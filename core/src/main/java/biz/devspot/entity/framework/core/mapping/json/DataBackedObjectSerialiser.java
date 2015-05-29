package biz.devspot.entity.framework.core.mapping.json;

import biz.devspot.entity.framework.core.DataBackedObjectHandlerFactory;
import biz.devspot.entity.framework.core.model.DataBackedObject;
import biz.devspot.entity.framework.core.model.DataObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class DataBackedObjectSerialiser extends JsonSerializer<DataBackedObject> {

    @Override
    public void serialize(DataBackedObject t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        DataObject dataObject = DataBackedObjectHandlerFactory.getHandler().getDataObject(t);
        ObjectMapper mapper = (ObjectMapper) jg.getCodec();
        ObjectWriter writer = mapper.writerWithType(dataObject.getClass());
        boolean internal = false;
        if (sp.getAttribute("internal") != null) {
            internal = (Boolean) sp.getAttribute("internal");
        }
        if(internal){
            jg.writeString(dataObject.getId());
        }else{
            writer.withAttribute("internal", true).writeValue(jg, dataObject);
        }
    }

}
