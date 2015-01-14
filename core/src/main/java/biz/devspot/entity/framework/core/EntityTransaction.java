package biz.devspot.entity.framework.core;

import biz.devspot.entity.framework.core.model.ManagedEntity;
import java.util.ArrayList;
import java.util.List;

public class EntityTransaction {

    private final List<ManagedEntity> updated = new ArrayList<ManagedEntity>();
    private final List<ManagedEntity> deleted = new ArrayList<ManagedEntity>();

    public List<ManagedEntity> getUpdated() {
        return updated;
    }

    public List<ManagedEntity> getDeleted() {
        return deleted;
    }
    
    public void addUpdatedEntity(ManagedEntity entity){
        updated.add(entity);
    }
    
    public void addDeletedEntity(ManagedEntity entity){
        deleted.add(entity);
    }
    
}
