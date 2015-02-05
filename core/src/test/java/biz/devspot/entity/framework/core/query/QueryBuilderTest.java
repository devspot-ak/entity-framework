
package biz.devspot.entity.framework.core.query;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static biz.devspot.entity.framework.core.query.QueryFilters.*;

public class QueryBuilderTest {

    public QueryBuilderTest() {
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
    public void test() {
        new QueryBuilder().where("firstName").isEqualTo("test").and("surname").isEqualTo("test").or("surname").isEqualTo("other").limit(1);
    }

}