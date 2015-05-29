package biz.devspot.entity.framework.core.query;

public class GreaterThanFilter extends QueryFilter{

    private Object value;

    public GreaterThanFilter(String field, Object value) {
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
