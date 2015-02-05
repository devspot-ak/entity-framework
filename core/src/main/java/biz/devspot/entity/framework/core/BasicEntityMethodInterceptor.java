package biz.devspot.entity.framework.core;

import biz.devspot.entity.framework.core.model.DataBackedObject;
import biz.devspot.entity.framework.core.model.DataObject;
import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class BasicEntityMethodInterceptor implements MethodInterceptor {

    private DataObject data;
    private DataBackedObject entity;

    public BasicEntityMethodInterceptor(DataBackedObject entity) {
        this.entity = entity;
        this.data = DataBackedObjectHandlerFactory.getHandler().getDataObject(entity);
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy mp) throws Throwable {
        Object result = null;
        result = method.invoke(data, args);
        if(method.getReturnType().equals(Void.TYPE)){
            EntityManagerFactory.getManager().getTransaction().addUpdatedEntity(entity);
        }
        return result;
    }

}
