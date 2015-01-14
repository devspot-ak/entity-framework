package biz.devspot.entity.framework.core.mapping.json;

import biz.devspot.entity.framework.core.model.ManagedEntity;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.util.HashMap;
import java.util.Map;

public class EntityObjectMapper extends ObjectMapper {

    public EntityObjectMapper() {
        SimpleModule module = new SimpleModule();
        //module.setDeserializers(new EntityDeserialisers());
        module.setDeserializerModifier(new BeanDeserializerModifier() {
            private final Map<Class<? extends ManagedEntity>, EntityDeserialiser> deserialisers = new HashMap<Class<? extends ManagedEntity>, EntityDeserialiser>();
            
            @Override
            public JsonDeserializer<?> modifyDeserializer(DeserializationConfig dc, BeanDescription bd, JsonDeserializer<?> jd) {
                Class type = bd.getBeanClass();
                if (ManagedEntity.class.isAssignableFrom(type)) {
                    EntityDeserialiser deserialiser = deserialisers.get(type);
                    if (deserialiser == null) {
                        deserialiser = new EntityDeserialiser(type, jd);
                        deserialisers.put(type, deserialiser);
                    }
                    return deserialiser;
                }
                return super.modifyDeserializer(dc, bd, jd);
            }

        });
        registerModule(module);
        setAnnotationIntrospectors(com.fasterxml.jackson.databind.AnnotationIntrospector.pair(new SerialiserAnnotationIntrospector(), DEFAULT_ANNOTATION_INTROSPECTOR), com.fasterxml.jackson.databind.AnnotationIntrospector.pair(new DeserialiserAnnotationIntrospector(), DEFAULT_ANNOTATION_INTROSPECTOR));
    }

}
