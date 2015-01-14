package biz.devspot.entity.framework.core.model;

public class AbstractManagedEntity implements ManagedEntity{

    private String id;

    public AbstractManagedEntity() {
        
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof ManagedEntity)) {
            return false;
        }
        final ManagedEntity other = (ManagedEntity) obj;
        if ((this.id == null) ? (other.getId() != null) : !this.id.equals(other.getId())) {
            return false;
        }
        return true;
    }
    
}
