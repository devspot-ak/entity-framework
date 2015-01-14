package biz.devspot.entity.framework.core;

import biz.devspot.entity.framework.core.model.ManagedEntity;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class BasicEntityMethodInterceptor implements MethodInterceptor {

    private ManagedEntity entity;

    public BasicEntityMethodInterceptor(ManagedEntity entity) {
        this.entity = entity;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy mp) throws Throwable {
        Object result = null;
        result = method.invoke(entity, args);
        if(method.getReturnType().equals(Void.TYPE)){
            EntityManagerFactory.getManager().getTransaction().addUpdatedEntity(entity);
        }
        return result;
    }

}
