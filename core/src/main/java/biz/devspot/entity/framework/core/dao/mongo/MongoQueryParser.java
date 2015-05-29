package biz.devspot.entity.framework.core.dao.mongo;

import biz.devspot.entity.framework.core.query.EqualityFilter;
import biz.devspot.entity.framework.core.query.GreaterThanFilter;
import biz.devspot.entity.framework.core.query.LessThanFilter;
import biz.devspot.entity.framework.core.query.Query;
import biz.devspot.entity.framework.core.query.QueryFilter;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import java.util.ArrayList;
import java.util.List;

public class MongoQueryParser {

    public DBObject parse(Query query){
        DBObject dbObject = new BasicDBObject();
        QueryFilter filter = query.getFilter();
        dbObject = parseQueryFilter(dbObject, filter);
        return dbObject;
    }
    
    private DBObject parseQueryFilter(DBObject dbObject, QueryFilter filter) {
        if (filter != null) {
            String field = filter.getField();
            if (field.equals("id")) {
                field = "_id";
            }
            if (filter instanceof EqualityFilter) {
                EqualityFilter eqFilter = (EqualityFilter) filter;
                dbObject.put(field, eqFilter.getValue());
            }else if(filter instanceof LessThanFilter){
                LessThanFilter ltFilter = (LessThanFilter)filter;
                dbObject.put(field, new BasicDBObject("$lt", ltFilter.getValue()));
            }else if(filter instanceof GreaterThanFilter){
                GreaterThanFilter ltFilter = (GreaterThanFilter)filter;
                dbObject.put(field, new BasicDBObject("$gt", ltFilter.getValue()));
            }
            if(filter.hasAndFilter()){
                parseQueryFilter(dbObject, filter.getAndFilter());
            }else if(filter.hasOrFilter()){
                DBObject nestedObject = dbObject;
                dbObject = new BasicDBObject();
                List<DBObject> conditions = new ArrayList<DBObject>();
                conditions.add(nestedObject);
                conditions.add(parseQueryFilter(new BasicDBObject(), filter.getOrFilter()));
                dbObject.put("$or", conditions);
            }
        }
        return dbObject;
    }
    
}
