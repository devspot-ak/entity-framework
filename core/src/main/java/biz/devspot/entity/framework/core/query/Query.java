package biz.devspot.entity.framework.core.query;

import java.util.ArrayList;
import java.util.List;

public class Query {

    private List<QueryFilter> filters = new ArrayList<QueryFilter>();
    private int limit = -1;

    public List<QueryFilter> getFilters() {
        return filters;
    }

    public void setFilters(List<QueryFilter> filters) {
        this.filters = filters;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
    
}
