package biz.devspot.entity.framework.core.mapping.json;

import biz.devspot.entity.framework.core.DataBackedObjectHandlerFactory;
import biz.devspot.entity.framework.core.model.DataBackedObject;
import biz.devspot.entity.framework.core.model.DataObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class DataBackedObjectSerialiser extends JsonSerializer<DataBackedObject>{

    @Override
    public void serialize(DataBackedObject t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        DataObject dataObject = DataBackedObjectHandlerFactory.getHandler().getDataObject(t);
        jg.getCodec().writeValue(jg, dataObject);
    }

}
