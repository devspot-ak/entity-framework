package biz.devspot.entity.framework.core;

import biz.devspot.entity.framework.core.model.DataBackedObject;
import biz.devspot.entity.framework.core.query.Query;
import java.util.List;

public interface EntityManager {
    
    public DataBackedObject manage(DataBackedObject entity);
    
    public DataBackedObject findById(String id);
    
    public <E extends DataBackedObject> E findOne(Class<? extends E> type, Query query);
    
    public <E extends DataBackedObject> List<E> find(Class<? extends E> type, Query query);
    
    public void openTransaction();
    
    public EntityTransaction getTransaction();
    
    public void commitTransaction();
    
}
