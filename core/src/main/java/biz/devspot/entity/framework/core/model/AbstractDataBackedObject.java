package biz.devspot.entity.framework.core.model;

import biz.devspot.entity.framework.core.EntityManagerFactory;

public abstract class AbstractDataBackedObject<DO extends DataObject> implements DataBackedObject{

    protected DO data;

    public AbstractDataBackedObject() {
        this.data = createDataObject();
        EntityManagerFactory.getManager().manage(this);
    }

    protected abstract DO createDataObject();

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.data.getId() != null ? this.data.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof DataBackedObject)) {
            return false;
        }
        final DataBackedObject other = (DataBackedObject) obj;
        return this.hashCode() == other.hashCode();
    }
}
