package biz.devspot.entity.framework.core;

import biz.devspot.entity.framework.core.annotation.AssociatedEntity;
import biz.devspot.entity.framework.core.model.ManagedEntity;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.json.JSONObject;

public class EntityMethodInterceptor implements MethodInterceptor {

    private ManagedEntity entity;
    private JSONObject metadata;
    private ManagedEntity proxy;

    public EntityMethodInterceptor(ManagedEntity entity, JSONObject metadata) {
        this.entity = entity;
        this.metadata = metadata;
    }

    public void setProxy(ManagedEntity proxy) {
        this.proxy = proxy;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy mp) throws Throwable {
        Object result = null;
        result = method.invoke(entity, args);
        if (method.getReturnType().equals(Void.TYPE)) {
            EntityManagerFactory.getManager().getTransaction().addUpdatedEntity(proxy);
        } else {
            if (result == null && method.getName().startsWith("get")) {
                String fieldName = method.getName().substring(3);
                AssociatedEntity annotation = method.getAnnotation(AssociatedEntity.class);
                if (annotation == null) {
                    fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
                    Field field = FieldUtils.getField(entity.getClass(), fieldName, true);
                    if (field != null) {
                        annotation = field.getAnnotation(AssociatedEntity.class);
                    }
                }
                if (annotation != null) {
                    String assocEntityId = metadata.getString(fieldName);
                    result = EntityManagerFactory.getManager().findById(assocEntityId);
                    MethodUtils.invokeMethod(entity, "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), result);
                }
            }
        }
        return result;
    }

}
