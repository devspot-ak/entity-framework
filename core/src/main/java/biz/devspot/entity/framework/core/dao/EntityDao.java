package biz.devspot.entity.framework.core.dao;

import biz.devspot.entity.framework.core.model.ManagedEntity;
import biz.devspot.entity.framework.core.query.Query;
import java.util.List;

public interface EntityDao {

    public void assignId(ManagedEntity entity) throws DaoException;
    
    public void save(ManagedEntity entity) throws DaoException;
    
    public void delete(ManagedEntity entity) throws DaoException;
    
    public ManagedEntity findById(String id) throws DaoException;
    
    public <E extends ManagedEntity> E findOne(Class<E> type, Query query) throws DaoException;
    
    public <E extends ManagedEntity> List<E> find(Class<E> type, Query query) throws DaoException;
    
}
