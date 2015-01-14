package biz.devspot.entity.framework.core.mapping.json;

import biz.devspot.entity.framework.core.model.ManagedEntity;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class AssociatedEntitySerialiser extends JsonSerializer<ManagedEntity>{

    @Override
    public void serialize(ManagedEntity t, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException {
        jg.writeString(t.getId());
    }

}
