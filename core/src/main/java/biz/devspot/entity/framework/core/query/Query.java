package biz.devspot.entity.framework.core.query;

import java.util.HashSet;
import java.util.Set;

public class Query {

    private QueryFilter filter;
    private int limit = -1;
    private Set<QuerySortField> sortFields = new HashSet<QuerySortField>();

    public QueryFilter getFilter() {
        return filter;
    }

    public void setFilter(QueryFilter filter) {
        this.filter = filter;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public Set<QuerySortField> getSortFields() {
        return sortFields;
    }

    public void setSortFields(Set<QuerySortField> sortFields) {
        this.sortFields = sortFields;
    }

}
