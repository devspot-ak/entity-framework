package biz.devspot.entity.framework.core;

public class DataBackedObjectHandlerFactory {

    private static DataBackedObjectHandler handler = new DataBackedObjectHandlerImpl();

    public static DataBackedObjectHandler getHandler() {
        return handler;
    }

    public static void setHandler(DataBackedObjectHandler handler) {
        DataBackedObjectHandlerFactory.handler = handler;
    }
    
}
