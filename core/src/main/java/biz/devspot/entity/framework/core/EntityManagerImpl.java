package biz.devspot.entity.framework.core;

import biz.devspot.entity.framework.core.dao.EntityDao;
import biz.devspot.entity.framework.core.model.DataBackedObject;
import biz.devspot.entity.framework.core.model.DataObject;
import biz.devspot.entity.framework.core.query.Query;
import java.util.List;
import net.sf.cglib.proxy.Enhancer;

public class EntityManagerImpl implements EntityManager {

    private ThreadLocal<EntityTransaction> context = new ThreadLocal<EntityTransaction>();

    private final EntityDao dao;

    public EntityManagerImpl(EntityDao dao) {
        this.dao = dao;
    }

    @Override
    public void clearCache() {
        dao.clearCache();
    }

    @Override
    public void manage(DataBackedObject entity) {
        dao.assignId(entity);
        DataObject data = DataBackedObjectHandlerFactory.getHandler().getDataObject(entity);
        getTransaction().addUpdatedEntity(entity);
    }

    @Override
    public DataBackedObject findById(String id) {
        DataBackedObject result = dao.findById(id);
        return result;
    }

    @Override
    public <E extends DataBackedObject> E findOne(Class<? extends E> type, Query query) {
        return dao.findOne(type, query);
    }

    @Override
    public <E extends DataBackedObject> List<E> find(Class<? extends E> type, Query query) {
        return (List<E>) dao.find(type, query);
    }

    @Override
    public void openTransaction() {
        context.set(new EntityTransaction());
    }

    @Override
    public EntityTransaction getTransaction() {
        if (context.get() == null) {
            throw new TransactionNotFoundException();
        }
        return context.get();
    }

    @Override
    public void commitTransaction() {
        for (DataBackedObject entity : getTransaction().getUpdated()) {
            dao.save(entity);
        }
        for (DataBackedObject entity : getTransaction().getDeleted()) {
            dao.delete(entity);
        }
        context.remove();
    }

    private DataBackedObject enhance(DataBackedObject entity) {
        DataObject data = DataBackedObjectHandlerFactory.getHandler().getDataObject(entity);
        if (entity == null) {
            return null;
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(data.getClass());
        enhancer.setCallback(new BasicEntityMethodInterceptor(entity));
        data = (DataObject) enhancer.create();
        DataBackedObjectHandlerFactory.getHandler().setDataObject(entity, data);
        return entity;
    }

}
