package biz.devspot.entity.framework.core;

public class EntityManagerFactory {

    private static EntityManager manager;

    public static EntityManager getManager() {
        if(manager == null){
            throw new EntityManagerNotFoundException();
        }
        return manager;
    }

    public static void setManager(EntityManager manager) {
        EntityManagerFactory.manager = manager;
    }
    
}
