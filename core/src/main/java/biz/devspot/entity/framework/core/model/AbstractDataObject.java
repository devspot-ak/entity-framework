package biz.devspot.entity.framework.core.model;

public abstract class AbstractDataObject implements DataObject{

    private String id;

    public AbstractDataObject() {
        
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
