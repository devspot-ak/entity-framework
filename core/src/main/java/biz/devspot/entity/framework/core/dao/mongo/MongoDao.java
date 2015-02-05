package biz.devspot.entity.framework.core.dao.mongo;

import biz.devspot.entity.framework.core.DataBackedObjectHandlerFactory;
import biz.devspot.entity.framework.core.dao.AbstractDao;
import biz.devspot.entity.framework.core.dao.DaoException;
import biz.devspot.entity.framework.core.mapping.json.DataBackedObjectMapper;
import biz.devspot.entity.framework.core.model.DataBackedObject;
import biz.devspot.entity.framework.core.query.Query;
import biz.devspot.entity.framework.core.query.QuerySortDirection;
import biz.devspot.entity.framework.core.query.QuerySortField;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MongoDao extends AbstractDao {

    private final DB database;
    private final DataBackedObjectMapper objectMapper;
    private final MongoQueryParser queryParser = new MongoQueryParser();
    private final HashMap<String, DataBackedObject> objectCache = new HashMap<String, DataBackedObject>();

    public MongoDao(DB database, DataBackedObjectMapper objectMapper) {
        this.database = database;
        this.objectMapper = objectMapper;
    }
    
    public void clearObjectCache(){
        objectCache.clear();
    }

    @Override
    public void doSave(String table, DataBackedObject entity) throws DaoException {
        try {
            System.out.println("Saving to " + table + ": " + objectMapper.writeValueAsString(entity));
            DBObject dbObject = (DBObject) JSON.parse(objectMapper.writeValueAsString(entity).replace("\"id\"", "\"_id\""));
            objectCache.put((String) dbObject.get("id"), entity);
            database.getCollection(table).save(dbObject);
        } catch (JsonProcessingException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public void doDelete(String table, DataBackedObject entity) throws DaoException {
        database.getCollection(table).remove(new BasicDBObject("_id", DataBackedObjectHandlerFactory.getHandler().getDataObject(entity).getId()));
    }

    @Override
    public <E extends DataBackedObject> List<E> doFind(String table, Query query) throws DaoException {
        DBObject dbObject = queryParser.parse(query);
        System.out.println("Query on " + table + ": " + dbObject.toString());
        DBCursor cursor = database.getCollection(table).find(dbObject);
        if (query.getLimit() > 0) {
            cursor.limit(query.getLimit());
        }
        if (!query.getSortFields().isEmpty()) {
            DBObject orderBy = new BasicDBObject();
            for (QuerySortField sortField : query.getSortFields()) {
                int direction = sortField.getDirection().equals(QuerySortDirection.ASCENDING) ? 1 : -1;
                orderBy.put(sortField.getField(), direction);
            }
            System.out.println("Sorted by " + orderBy.toString());
            cursor.sort(orderBy);
        }
        List<E> results = new LinkedList<E>();
        while (cursor.hasNext()) {
            try {
                DBObject dbo = cursor.next();
                String id = (String) dbo.get("_id");
                E result = (E) objectCache.get(id);
                if (result == null) {
                    Class<E> type = (Class<E>) getType(id);
                    result = objectMapper.readValue(dbo.toString().replace("\"_id\"", "\"id\""), type);
                    objectCache.put(id, result);
                }
                results.add(result);
            } catch (IOException ex) {
                throw new DaoException(ex);
            } catch (ClassNotFoundException ex) {
                throw new DaoException(ex);
            }
        }
        return results;
    }

}
