package biz.devspot.entity.framework.core.query;

public abstract class QueryFilter {

    private String field;

    public QueryFilter(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

}
