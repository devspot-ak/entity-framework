package biz.devspot.entity.framework.core.query;

public class QuerySortField {

    private String field;
    private QuerySortDirection direction = QuerySortDirection.ASCENDING;

    public QuerySortField(String field) {
        this.field = field;
    }

    public QuerySortField(String field, QuerySortDirection direction) {
        this.field = field;
        this.direction = direction;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public QuerySortDirection getDirection() {
        return direction;
    }

    public void setDirection(QuerySortDirection direction) {
        this.direction = direction;
    }
    
}
