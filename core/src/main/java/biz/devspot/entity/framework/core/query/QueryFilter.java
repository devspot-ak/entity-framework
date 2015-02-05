package biz.devspot.entity.framework.core.query;

public abstract class QueryFilter {

    private String field;
    private QueryFilter orFilter;
    private QueryFilter andFilter;

    public QueryFilter(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public void setOrFilter(QueryFilter orFilter) {
        this.orFilter = orFilter;
    }

    public void setAndFilter(QueryFilter andFilter) {
        this.andFilter = andFilter;
    }

    public QueryFilter getOrFilter() {
        return orFilter;
    }

    public QueryFilter getAndFilter() {
        return andFilter;
    }

    public boolean hasOrFilter() {
        return orFilter != null;
    }

    public boolean hasAndFilter() {
        return andFilter != null;
    }

}
