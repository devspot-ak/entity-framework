package biz.devspot.entity.framework.core.mapping.json;

import biz.devspot.entity.framework.core.model.DataBackedObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import java.util.HashMap;
import java.util.Map;

public class DataBackedObjectMapper extends ObjectMapper {

    public DataBackedObjectMapper() {
        SimpleModule module = new SimpleModule();
        //module.setDeserializers(new EntityDeserialisers());
        module.setSerializerModifier(new BeanSerializerModifier() {
            
            private DataBackedObjectSerialiser dataBackedObjectSerialiser = new DataBackedObjectSerialiser();
            
            @Override
            public JsonSerializer<?> modifySerializer(SerializationConfig sc, BeanDescription bd, JsonSerializer<?> js) {
                Class type = bd.getBeanClass();
                if (DataBackedObject.class.isAssignableFrom(type)) {
                    return dataBackedObjectSerialiser;
                }
                return super.modifySerializer(sc, bd, js);
            }

        });
        module.setDeserializerModifier(new BeanDeserializerModifier() {
            private final Map<Class<? extends DataBackedObject>, DataBackedObjectDeserialiser> deserialisers = new HashMap<Class<? extends DataBackedObject>, DataBackedObjectDeserialiser>();

            @Override
            public JsonDeserializer<?> modifyDeserializer(DeserializationConfig dc, BeanDescription bd, JsonDeserializer<?> jd) {
                Class type = bd.getBeanClass();
                if (DataBackedObject.class.isAssignableFrom(type)) {
                    DataBackedObjectDeserialiser deserialiser = deserialisers.get(type);
                    if (deserialiser == null) {
                        deserialiser = new DataBackedObjectDeserialiser(type, jd);
                        deserialisers.put(type, deserialiser);
                    }
                    return deserialiser;
                }
                return super.modifyDeserializer(dc, bd, jd);
            }

        });
        registerModule(module);
    }

}
