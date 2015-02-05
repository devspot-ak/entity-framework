package biz.devspot.entity.framework.core.model;

public abstract class AbstractIdentifiedDataBackedObject<DO extends DataObject> extends AbstractDataBackedObject<DO>{
    
    public String getId(){
        return data.getId();
    }

}
