package biz.devspot.entity.framework.core.dao;

import biz.devspot.entity.framework.core.DataBackedObjectHandlerFactory;
import biz.devspot.entity.framework.core.annotation.TypeAlias;
import biz.devspot.entity.framework.core.model.DataBackedObject;
import biz.devspot.entity.framework.core.model.DataObject;
import biz.devspot.entity.framework.core.query.Query;
import biz.devspot.entity.framework.core.query.QueryBuilder;
import java.util.List;
import java.util.UUID;

public abstract class AbstractDao implements EntityDao{

    @Override
    public void assignId(DataBackedObject entity) {
        String id = entity.getClass().getName() + "_" + UUID.randomUUID().toString();
        DataObject data = DataBackedObjectHandlerFactory.getHandler().getDataObject(entity);
        data.setId(id);
    }

    @Override
    public void save(DataBackedObject entity) throws DaoException {
        String table = getTable(entity);
        doSave(table, entity);
    }
    
    public abstract void doSave(String table, DataBackedObject entity) throws DaoException;

    @Override
    public void delete(DataBackedObject entity) throws DaoException {
        doDelete(getTable(entity), entity);
    }
    
    public abstract void doDelete(String table, DataBackedObject entity) throws DaoException;

    @Override
    public DataBackedObject findById(String id) throws DaoException {
        try {
            Class type = getType(id);
            return findOne(type, new QueryBuilder().where("id").isEqualTo(id).build());
        } catch (ClassNotFoundException ex) {
            return null;
        }
    }

    @Override
    public <E extends DataBackedObject> E findOne(Class<E> type, Query query) throws DaoException {
        query.setLimit(1);
        List<E> results = find(type, query);
        if(results.isEmpty()){
           return null; 
        }else{
            return results.get(0);
        }
    }

    @Override
    public <E extends DataBackedObject> List<E> find(Class<E> type, Query query) throws DaoException {
        return doFind(getTable(type), query);
    }
    
    public abstract <E extends DataBackedObject> List<E> doFind(String table, Query query) throws DaoException;
    
    private String getTable(DataBackedObject entity) {
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

    protected Class<? extends DataBackedObject> getType(String id) throws ClassNotFoundException {
        String type = id.substring(0, id.indexOf("_"));
        return (Class<? extends DataBackedObject>) Class.forName(type);
    }

}
