package biz.devspot.entity.framework.core.query;

public class QueryFilterBuilder {

    private Query query;
    private String field;

    public QueryFilterBuilder(Query query, String field) {
        this.query = query;
        this.field = field;
    }
    
    public QueryBuilder eq(String value){
        query.getFilters().add(new EqualityFilter(field, value));
        return new QueryBuilder(query);
    }

}
