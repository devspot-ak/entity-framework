package biz.devspot.entity.framework.core.mapping.json;

import biz.devspot.entity.framework.core.annotation.AssociatedEntity;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.introspect.Annotated;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.NopAnnotationIntrospector;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DeserialiserAnnotationIntrospector extends NopAnnotationIntrospector {

    private Map<Class, JsonDeserializer> deserialisers = new HashMap<Class, JsonDeserializer>();

    public DeserialiserAnnotationIntrospector() {
        deserialisers.put(AssociatedEntity.class, null);
    }

    @Override
    public Object findDeserializer(Annotated antd) {
        for (Entry<Class, JsonDeserializer> entry : deserialisers.entrySet()) {
            Class annotationClass = entry.getKey();
            Annotation annotation = antd.getAnnotation(annotationClass);
            if (annotation != null) {
                return entry.getValue();
            }
        }
        return super.findDeserializer(antd);
    }

    @Override
    public boolean hasIgnoreMarker(AnnotatedMember am) {
        for (Entry<Class, JsonDeserializer> entry : deserialisers.entrySet()) {
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
