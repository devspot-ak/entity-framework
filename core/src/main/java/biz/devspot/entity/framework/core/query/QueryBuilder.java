package biz.devspot.entity.framework.core.query;

public class QueryBuilder {

    private Query query = new Query();
    private String filterOperand = "start";
    private String filterField;
    private QueryFilter filter;
    private QuerySortField sortField;

    public QueryBuilder() {
    }

    public QueryBuilder(Query query) {
        this.query = query;
    }

    public QueryBuilder where(String field) {
        this.filterField = field;
        return this;
    }

    public QueryBuilder isEqualTo(Object value) {
        setFilter(new EqualityFilter(filterField, value));
        return this;
    }

    public QueryBuilder isGreaterThan(Object value) {
        setFilter(new GreaterThanFilter(filterField, value));
        return this;
    }
    
    public QueryBuilder isLessThan(Object value) {
        setFilter(new LessThanFilter(filterField, value));
        return this;
    }

    private void setFilter(QueryFilter newFilter) {
        if (filterField == null) {
            throw new IllegalStateException("Conditional field not set");
        }
        if (filterOperand.equals("start")) {
            filter = newFilter;
            query.setFilter(filter);
        } else if (filterOperand.equals("and")) {
            filter.setAndFilter(newFilter);
            filter = newFilter;
        } else if (filterOperand.equals("or")) {
            filter.setOrFilter(newFilter);
            filter = newFilter;
        } else {
            throw new IllegalStateException("Unknown filter operand " + filterOperand);
        }
    }

    public QueryBuilder and(String field) {
        applyOperand("and", field);
        return this;
    }

    public QueryBuilder or(String field) {
        applyOperand("or", field);
        return this;
    }

    public QueryBuilder limit(int limit) {
        query.setLimit(limit);
        return this;
    }

    public QueryBuilder sortBy(String field) {
        sortField = new QuerySortField(field);
        query.getSortFields().add(sortField);
        return this;
    }

    public QueryBuilder ascending() {
        if (sortField == null) {
            throw new IllegalStateException("Sort field not set");
        }
        sortField.setDirection(QuerySortDirection.ASCENDING);
        return this;
    }

    public QueryBuilder descending() {
        if (sortField == null) {
            throw new IllegalStateException("Sort field not set");
        }
        sortField.setDirection(QuerySortDirection.DESCENDING);
        return this;
    }

    private void applyOperand(String operand, String field) {
        if (filter == null) {
            throw new IllegalStateException("Existing condition not found");
        }
        this.filterOperand = operand;
        this.filterField = field;
    }

    public Query build() {
        return query;
    }

}
