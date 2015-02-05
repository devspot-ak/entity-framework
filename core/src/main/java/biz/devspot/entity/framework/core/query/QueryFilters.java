package biz.devspot.entity.framework.core.query;

public class QueryFilters {

    public static EqualityFilter equal(String field, Object value){
        return new EqualityFilter(field, value);
    }
    
}
