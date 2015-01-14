package biz.devspot.entity.framework.core;

import biz.devspot.entity.framework.core.model.ManagedEntity;
import java.util.List;

public interface EntityManager {
    
    public ManagedEntity manage(ManagedEntity entity);
    
    public ManagedEntity findById(String id);
    
    public void openTransaction();
    
    public EntityTransaction getTransaction();
    
    public void commitTransaction();
    
    public <E extends ManagedEntity> E getLinkedEntity(ManagedEntity target, Class<? extends E> type, String... links);
    
    public <E extends ManagedEntity> List<E> getLinkedEntities(ManagedEntity target, Class<? extends E> type, String... links);
}
