package biz.devspot.entity.framework.core;

import biz.devspot.entity.framework.core.model.DataBackedObject;
import biz.devspot.entity.framework.core.model.DataObject;

public interface DataBackedObjectHandler {

    public DataObject getDataObject(Object object);

    public Class<? extends DataObject> getDataObjectType(Class<? extends DataBackedObject> object);

    public void setDataObject(DataBackedObject object, DataObject data);
}
