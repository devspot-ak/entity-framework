package biz.devspot.entity.framework.core.dao.mongo;

import biz.devspot.entity.framework.core.dao.AbstractDao;
import biz.devspot.entity.framework.core.dao.DaoException;
import biz.devspot.entity.framework.core.mapping.json.EntityObjectMapper;
import biz.devspot.entity.framework.core.model.ManagedEntity;
import biz.devspot.entity.framework.core.query.EqualityFilter;
import biz.devspot.entity.framework.core.query.Query;
import biz.devspot.entity.framework.core.query.QueryFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MongoDao extends AbstractDao {

    private final DB database;
    private final EntityObjectMapper objectMapper;

    public MongoDao(DB database, EntityObjectMapper objectMapper) {
        this.database = database;
        this.objectMapper = objectMapper;
    }

    @Override
    public void doSave(String table, ManagedEntity entity) throws DaoException {
        try {
            System.out.println("Saving to " + table + ": " + objectMapper.writeValueAsString(entity));
            database.getCollection(table).save((DBObject) JSON.parse(objectMapper.writeValueAsString(entity).replace("\"id\"", "\"_id\"")));
        } catch (JsonProcessingException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public void doDelete(String table, ManagedEntity entity) throws DaoException {
        database.getCollection(table).remove(new BasicDBObject("_id", entity.getId()));
    }

    @Override
    public <E extends ManagedEntity> List<E> doFind(String table, Query query) throws DaoException {
        DBObject dbObject = new BasicDBObject();
        for (QueryFilter filter : query.getFilters()) {
            String field = filter.getField();
            if (field.equals("id")) {
                field = "_id";
            }
            if (filter instanceof EqualityFilter) {
                EqualityFilter eqFilter = (EqualityFilter) filter;
                dbObject.put(field, eqFilter.getValue());
            }
        }
        System.out.println("Query: " + dbObject.toString());
        DBCursor cursor = database.getCollection(table).find(dbObject);
        if (query.getLimit() > 0) {
            cursor.limit(query.getLimit());
        }
        List<E> results = new LinkedList<E>();
        while (cursor.hasNext()) {
            try {
                DBObject dbo = cursor.next();
                String id = (String) dbo.get("_id");
                Class<E> type = (Class<E>) getType(id);
                E result = objectMapper.readValue(dbo.toString().replace("\"_id\"", "\"id\""), type);
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
