package biz.devspot.entity.framework.core;

import biz.devspot.entity.framework.core.model.DataBackedObject;
import java.util.HashSet;
import java.util.Set;

public class EntityTransaction {

    private final Set<DataBackedObject> updated = new HashSet<DataBackedObject>();
    private final Set<DataBackedObject> deleted = new HashSet<DataBackedObject>();

    public Set<DataBackedObject> getUpdated() {
        return updated;
    }

    public Set<DataBackedObject> getDeleted() {
        return deleted;
    }
    
    public void addUpdatedEntity(DataBackedObject entity){
        updated.add(entity);
    }
    
    public void addDeletedEntity(DataBackedObject entity){
        deleted.add(entity);
    }
    
}
