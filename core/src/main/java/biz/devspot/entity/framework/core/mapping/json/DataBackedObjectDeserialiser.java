package biz.devspot.entity.framework.core.mapping.json;

import biz.devspot.entity.framework.core.DataBackedObjectHandlerFactory;
import biz.devspot.entity.framework.core.EntityMethodInterceptor;
import biz.devspot.entity.framework.core.model.DataBackedObject;
import biz.devspot.entity.framework.core.model.DataObject;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.ResolvableDeserializer;
import java.io.IOException;
import net.sf.cglib.proxy.Enhancer;
import org.json.JSONObject;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

public class DataBackedObjectDeserialiser extends JsonDeserializer<DataBackedObject> implements ResolvableDeserializer {

    private Class<? extends DataBackedObject> type;
    private Class<? extends DataObject> dataType;
    private JsonDeserializer defaultDeserialiser;
    private Objenesis objenesis;
    private ObjectInstantiator objectInstantiator;

    public DataBackedObjectDeserialiser(Class<? extends DataBackedObject> type, JsonDeserializer defaultDeserialiser) {
        this.type = type;
        this.dataType = DataBackedObjectHandlerFactory.getHandler().getDataObjectType(type);
        this.defaultDeserialiser = defaultDeserialiser;
        this.objenesis = new ObjenesisStd();
        this.objectInstantiator = objenesis.getInstantiatorOf(type);
    }

    @Override
    public DataBackedObject deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        String json = jp.getCodec().readTree(jp).toString();
        if(json.indexOf("{")!=0){
            return null;
        }
        ObjectMapper objectMapper = (ObjectMapper) jp.getCodec();
        DataObject data = objectMapper.reader(dataType).readValue(json);
        DataBackedObject object = (DataBackedObject) objectInstantiator.newInstance();
        data = enhance(object, data, new JSONObject(json));
        DataBackedObjectHandlerFactory.getHandler().setDataObject(object, data);
        return object;
    }

    @Override
    public void resolve(DeserializationContext dc) throws JsonMappingException {
        //((ResolvableDeserializer) defaultDeserialiser).resolve(dc);
    }

    private DataObject enhance(DataBackedObject object, DataObject data, JSONObject metadata) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(data.getClass());
        EntityMethodInterceptor methodInterceptor = new EntityMethodInterceptor(object, data, metadata);
        enhancer.setCallback(methodInterceptor);
        DataObject proxy = (DataObject) enhancer.create();
        return proxy;
    }

}
