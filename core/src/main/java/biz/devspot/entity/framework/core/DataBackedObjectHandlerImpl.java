package biz.devspot.entity.framework.core;

import biz.devspot.entity.framework.core.model.DataBackedObject;
import biz.devspot.entity.framework.core.model.DataObject;
import biz.devspot.entity.framework.core.util.ReflectionUtils;
import java.lang.reflect.Field;
import org.apache.commons.lang3.reflect.FieldUtils;

public class DataBackedObjectHandlerImpl implements DataBackedObjectHandler {

    @Override
    public DataObject getDataObject(Object object) {
        try {
            Field field = FieldUtils.getField(object.getClass(), "data", true);
            if (field == null) {
                throw new IllegalStateException("Couldn't find data object for entity");
            }
            DataObject data = (DataObject) field.get(object);
            return data;
        } catch (SecurityException ex) {
            throw new IllegalStateException("Couldn't access data object for entity", ex);
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException("Couldn't access data object for entity", ex);
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException("Couldn't access data object for entity", ex);
        }
    }

    @Override
    public Class<? extends DataObject> getDataObjectType(Class<? extends DataBackedObject> objectClass) {
        Field field = FieldUtils.getField(objectClass, "data", true);
        if (field == null) {
            throw new IllegalStateException("Couldn't find data object for entity");
        }
        Class<? extends DataObject> type = (Class<? extends DataObject>) ReflectionUtils.getFieldType(objectClass, field);
        return type;
    }

    

    @Override
    public void setDataObject(DataBackedObject object, DataObject data) {
        try {
            Field field = FieldUtils.getField(object.getClass(), "data", true);
            if (field == null) {
                throw new IllegalStateException("Couldn't find data object for entity");
            }
            field.set(object, data);
        } catch (IllegalArgumentException ex) {
            throw new IllegalStateException("Couldn't access data object for entity");
        } catch (IllegalAccessException ex) {
            throw new IllegalStateException("Couldn't access data object for entity");
        }
    }
}
