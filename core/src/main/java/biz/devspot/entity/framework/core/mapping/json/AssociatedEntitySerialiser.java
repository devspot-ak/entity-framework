package biz.devspot.entity.framework.core.mapping.json;

import biz.devspot.entity.framework.core.DataBackedObjectHandlerFactory;
import biz.devspot.entity.framework.core.EntityManagerFactory;
import biz.devspot.entity.framework.core.model.DataObject;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class AssociatedEntitySerialiser extends JsonSerializer<Object>{

    @Override
    public void serialize(Object t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        DataObject data = DataBackedObjectHandlerFactory.getHandler().getDataObject(t);
        jg.writeString(data.getId());
    }

}
