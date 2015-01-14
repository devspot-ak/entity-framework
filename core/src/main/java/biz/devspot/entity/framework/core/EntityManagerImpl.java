package biz.devspot.entity.framework.core;

import biz.devspot.entity.framework.core.dao.EntityDao;
import biz.devspot.entity.framework.core.model.ManagedEntity;
import biz.devspot.entity.framework.core.query.QueryBuilder;
import java.util.List;
import net.sf.cglib.proxy.Enhancer;

public class EntityManagerImpl implements EntityManager {

    private ThreadLocal<EntityTransaction> context = new ThreadLocal<EntityTransaction>();

    private final EntityDao dao;

    public EntityManagerImpl(EntityDao dao) {
        this.dao = dao;
    }

    @Override
    public ManagedEntity manage(ManagedEntity entity) {
        if (entity.getId() == null) {
            dao.assignId(entity);
        }
        getTransaction().addUpdatedEntity(entity);
        return enhance(entity);
    }

    @Override
    public ManagedEntity findById(String id) {
        ManagedEntity result = dao.findById(id);
        return result;
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
        for (ManagedEntity entity : getTransaction().getUpdated()) {
            dao.save(entity);
        }
        for (ManagedEntity entity : getTransaction().getDeleted()) {
            dao.delete(entity);
        }
        context.remove();
    }

    @Override
    public <E extends ManagedEntity> E getLinkedEntity(ManagedEntity target, Class<? extends E> type, String... links) {
        return (E) dao.findOne(type, new QueryBuilder().filter(links[0]).eq(target.getId()).build());
    }

    @Override
    public <E extends ManagedEntity> List<E> getLinkedEntities(ManagedEntity target, Class<? extends E> type, String... links) {
        return (List<E>) dao.find(type, new QueryBuilder().filter(links[0]).eq(target.getId()).build());
    }
    
    private ManagedEntity enhance(ManagedEntity entity){
        return getProxy(entity);
    }

    private ManagedEntity getProxy(ManagedEntity entity) {
        if(entity == null){
            return null;
        }
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(entity.getClass());
        enhancer.setCallback(new BasicEntityMethodInterceptor(entity));
        return (ManagedEntity) enhancer.create();
    }

}
