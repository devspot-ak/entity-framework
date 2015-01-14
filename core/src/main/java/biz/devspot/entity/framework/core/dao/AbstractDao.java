package biz.devspot.entity.framework.core.dao;

import biz.devspot.entity.framework.core.annotation.TypeAlias;
import biz.devspot.entity.framework.core.model.ManagedEntity;
import biz.devspot.entity.framework.core.query.Query;
import biz.devspot.entity.framework.core.query.QueryBuilder;
import java.util.List;
import java.util.UUID;

public abstract class AbstractDao implements EntityDao{

    @Override
    public void assignId(ManagedEntity entity) throws DaoException {
        String id = entity.getClass().getName() + "_" + UUID.randomUUID().toString();
        entity.setId(id);
    }

    @Override
    public void save(ManagedEntity entity) throws DaoException {
        String table = getTable(entity);
        doSave(table, entity);
    }
    
    public abstract void doSave(String table, ManagedEntity entity) throws DaoException;

    @Override
    public void delete(ManagedEntity entity) throws DaoException {
        doDelete(getTable(entity), entity);
    }
    
    public abstract void doDelete(String table, ManagedEntity entity) throws DaoException;

    @Override
    public ManagedEntity findById(String id) throws DaoException {
        try {
            Class type = getType(id);
            return findOne(type, new QueryBuilder().filter("id").eq(id).build());
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    @Override
    public <E extends ManagedEntity> E findOne(Class<E> type, Query query) throws DaoException {
        query.setLimit(1);
        List<E> results = find(type, query);
        if(results.isEmpty()){
           return null; 
        }else{
            return results.get(0);
        }
    }

    @Override
    public <E extends ManagedEntity> List<E> find(Class<E> type, Query query) throws DaoException {
        return doFind(getTable(type), query);
    }
    
    public abstract <E extends ManagedEntity> List<E> doFind(String table, Query query) throws DaoException;
    
    private String getTable(ManagedEntity entity) {
        return getTable(entity.getClass());
    }
    
    private String getTable(Class clazz){
        if(clazz.getName().contains("$$")){
            clazz = clazz.getSuperclass();
        }
        TypeAlias typeAlias = (TypeAlias) clazz.getAnnotation(TypeAlias.class);
        if(typeAlias!=null){
            clazz = typeAlias.value();
        }
        String table = clazz.getName();
        return table;
    }

    protected Class<? extends ManagedEntity> getType(String id) throws ClassNotFoundException {
        String type = id.substring(0, id.indexOf("_"));
        return (Class<? extends ManagedEntity>) Class.forName(type);
    }

}
