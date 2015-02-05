package biz.devspot.entity.framework.core;

public class EntityManagerNotFoundException extends RuntimeException {

    public EntityManagerNotFoundException() {
        super("An entity manager could not be found");
    }

}
