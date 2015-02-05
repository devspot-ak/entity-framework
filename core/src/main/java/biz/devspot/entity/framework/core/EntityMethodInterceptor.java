package biz.devspot.entity.framework.core;

import biz.devspot.entity.framework.core.annotation.AssociatedEntity;
import biz.devspot.entity.framework.core.model.DataBackedObject;
import biz.devspot.entity.framework.core.model.DataObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.json.JSONObject;

public class EntityMethodInterceptor implements MethodInterceptor {

    private DataBackedObject object;
    private DataObject data;
    private JSONObject metadata;

    public EntityMethodInterceptor(DataBackedObject object, DataObject data, JSONObject metadata) {
        this.object = object;
        this.data = data;
        this.metadata = metadata;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy mp) throws Throwable {
        Object result = null;
        result = method.invoke(data, args);
        if (method.getReturnType().equals(Void.TYPE)) {
            EntityManagerFactory.getManager().getTransaction().addUpdatedEntity(object);
            if (method.getName().startsWith("set")) {
                if (hasAssociatedEntityAnnotation(method)) {
                    String fieldName = method.getName().substring(3);
                    fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
                    DataBackedObject assocEntity = (DataBackedObject) args[0];
                    String assocEntityId = null;
                    if (assocEntity != null) {
                        DataObject assocEntityData = DataBackedObjectHandlerFactory.getHandler().getDataObject(assocEntity);
                        assocEntityId = assocEntityData.getId();
                    }
                    metadata.put(fieldName, assocEntityId);
                }
            }
        } else {
            if (result == null && method.getName().startsWith("get")) {
                if (hasAssociatedEntityAnnotation(method)) {
                    String fieldName = method.getName().substring(3);
                    fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
                    String assocEntityId = metadata.optString(fieldName, null);
                    if (assocEntityId == null) {
                        result = null;
                    } else {
                        result = EntityManagerFactory.getManager().findById(assocEntityId);
                    }
                    MethodUtils.invokeMethod(data, "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), result);
                }
            }
        }
        return result;
    }

    private boolean hasAssociatedEntityAnnotation(Method method) {
        String fieldName = method.getName().substring(3);
        AssociatedEntity annotation = method.getAnnotation(AssociatedEntity.class);
        if (annotation == null) {
            fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
            Field field = FieldUtils.getField(data.getClass(), fieldName, true);
            if (field != null) {
                annotation = field.getAnnotation(AssociatedEntity.class);
            }
        }
        return annotation != null;
    }

}
