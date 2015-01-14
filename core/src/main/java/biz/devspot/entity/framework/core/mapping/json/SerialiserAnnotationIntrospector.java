package biz.devspot.entity.framework.core.mapping.json;

import biz.devspot.entity.framework.core.annotation.AssociatedEntity;
import biz.devspot.entity.framework.core.annotation.Generated;
import biz.devspot.entity.framework.core.annotation.LinkedEntities;
import biz.devspot.entity.framework.core.annotation.LinkedEntity;
import biz.devspot.entity.framework.core.annotation.ManagedEntity;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SerialiserAnnotationIntrospector extends NopAnnotationIntrospector {

    private Map<Class, JsonSerializer> serialisers = new HashMap<Class, JsonSerializer>();

    public SerialiserAnnotationIntrospector() {
        serialisers.put(AssociatedEntity.class, new AssociatedEntitySerialiser());
        serialisers.put(ManagedEntity.class, new AssociatedEntitySerialiser());
        serialisers.put(LinkedEntity.class, null);
        serialisers.put(LinkedEntities.class, null);
        serialisers.put(Generated.class, null);
    }

    @Override
    public Object findSerializer(Annotated antd) {
        for (Entry<Class, JsonSerializer> entry : serialisers.entrySet()) {
            Class annotationClass = entry.getKey();
            Annotation annotation = antd.getAnnotation(annotationClass);
            if (annotation != null) {
                return entry.getValue();
            }
        }
        return super.findSerializer(antd);
    }

    @Override
    public boolean hasIgnoreMarker(AnnotatedMember am) {
        for (Entry<Class, JsonSerializer> entry : serialisers.entrySet()) {
            if (entry.getValue() == null) {
                Class annotationClass = entry.getKey();
                Annotation annotation = am.getAnnotation(annotationClass);
                if (annotation != null) {
                    return true;
                }
            }
        }
        return super.hasIgnoreMarker(am);
    }

}
