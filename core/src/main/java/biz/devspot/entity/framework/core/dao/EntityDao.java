package biz.devspot.entity.framework.core.dao;

import biz.devspot.entity.framework.core.model.DataBackedObject;
import biz.devspot.entity.framework.core.query.Query;
import java.util.List;

public interface EntityDao {
    
    public void assignId(DataBackedObject data);
    
    public void save(DataBackedObject entity) throws DaoException;
    
    public void delete(DataBackedObject entity) throws DaoException;
    
    public DataBackedObject findById(String id) throws DaoException;
    
    public <E extends DataBackedObject> E findOne(Class<E> type, Query query) throws DaoException;
    
    public <E extends DataBackedObject> List<E> find(Class<E> type, Query query) throws DaoException;
    
}
