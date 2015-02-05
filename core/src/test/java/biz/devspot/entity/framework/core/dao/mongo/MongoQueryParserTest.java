
package biz.devspot.entity.framework.core.dao.mongo;

import biz.devspot.entity.framework.core.query.Query;
import biz.devspot.entity.framework.core.query.QueryBuilder;
import com.mongodb.DBObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class MongoQueryParserTest {

    public MongoQueryParserTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testParse() {
        Query query = new QueryBuilder().where("p1").isEqualTo("v1").build();
        MongoQueryParser queryParser = new MongoQueryParser();
        DBObject result = queryParser.parse(query);
        System.out.println(result.toString());
        query = new QueryBuilder().where("p1").isEqualTo("v1").and("p2").isEqualTo("v2").build();
        result = queryParser.parse(query);
        System.out.println(result.toString());
        query = new QueryBuilder().where("p1").isEqualTo("v1").and("p2").isEqualTo("v2").and("p3").isEqualTo("v3").build();
        result = queryParser.parse(query);
        System.out.println(result.toString());
        query = new QueryBuilder().where("p1").isEqualTo("v1").or("p1").isEqualTo("v2").build();
        result = queryParser.parse(query);
        System.out.println(result.toString());
    }

}