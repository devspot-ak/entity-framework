package biz.devspot.entity.framework.core.mapping.json;

import biz.devspot.entity.framework.core.EntityMethodInterceptor;
import biz.devspot.entity.framework.core.model.ManagedEntity;
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

public class EntityDeserialiser extends JsonDeserializer<ManagedEntity> implements ResolvableDeserializer{

    private Class<? extends ManagedEntity> type;
    private JsonDeserializer defaultDeserialiser;

    public EntityDeserialiser(Class<? extends ManagedEntity> type, JsonDeserializer defaultDeserialiser) {
        this.type = type;
        this.defaultDeserialiser = defaultDeserialiser;
    }

    @Override
    public ManagedEntity deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        Boolean enhance = (Boolean) dc.getAttribute("enhance");
        if (enhance!=null && enhance == false) {
            dc.setAttribute("enhance", true);
            return (ManagedEntity) defaultDeserialiser.deserialize(jp, dc);
        } else {
            String json = jp.getCodec().readTree(jp).toString();
            ObjectMapper objectMapper = (ObjectMapper) jp.getCodec();
            ManagedEntity entity = (ManagedEntity) objectMapper.reader(type).withAttribute("enhance", false).readValue(json);
            return enhance(entity, new JSONObject(json));
        }
    }

    @Override
    public void resolve(DeserializationContext dc) throws JsonMappingException {
        ((ResolvableDeserializer) defaultDeserialiser).resolve(dc);
    }

    private ManagedEntity enhance(ManagedEntity entity, JSONObject metadata) {
        if (entity == null) {
            return null;
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(entity.getClass());
        EntityMethodInterceptor methodInterceptor = new EntityMethodInterceptor(entity, metadata);
        enhancer.setCallback(methodInterceptor);
        ManagedEntity proxy = (ManagedEntity) enhancer.create();
        methodInterceptor.setProxy(proxy);
        return proxy;
    }

}
