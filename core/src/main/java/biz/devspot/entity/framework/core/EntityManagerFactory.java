package biz.devspot.entity.framework.core;

public class EntityManagerFactory {

    private static EntityManager manager;

    public static EntityManager getManager() {
        return manager;
    }

    public static void setManager(EntityManager manager) {
        EntityManagerFactory.manager = manager;
    }
    
}
