package biz.devspot.entity.framework.core.mapping.json;

import biz.devspot.entity.framework.core.EntityManagerFactory;
import biz.devspot.entity.framework.core.model.ManagedEntity;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;

public class EntityLinkDeserialiser extends JsonDeserializer<ManagedEntity>{

    @Override
    public ManagedEntity deserialize(JsonParser jp, DeserializationContext dc) throws IOException, JsonProcessingException {
        String id = jp.readValueAs(String.class);
        return EntityManagerFactory.getManager().findById(id);
    }

}
