package biz.devspot.entity.framework.core.query;

public class QueryBuilder {

    private Query query = new Query();

    public QueryBuilder() {
    }

    public QueryBuilder(Query query) {
        this.query = query;
    }
    
    public QueryFilterBuilder filter(String field){
        return new QueryFilterBuilder(query, field);
    }
    
    public Query build(){
        return query;
    }
    
}
