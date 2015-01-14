package biz.devspot.entity.framework.core.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

@Target({FIELD, METHOD})
@Retention(RUNTIME)
public @interface AssociatedEntities {
    
}
