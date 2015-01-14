package biz.devspot.entity.framework.core.query;

public class EqualityFilter extends QueryFilter{

    private Object value;

    public EqualityFilter(String field, Object value) {
        super(field);
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
}
