package biz.devspot.entity.framework.core.mapping.json;

import biz.devspot.entity.framework.core.DataBackedObjectHandlerFactory;
import biz.devspot.entity.framework.core.EntityManager;
import biz.devspot.entity.framework.core.EntityManagerFactory;
import biz.devspot.entity.framework.core.EntityManagerImpl;
import biz.devspot.entity.framework.core.dao.EntityDao;
import biz.devspot.entity.framework.core.dao.mongo.MongoDao;
import biz.devspot.entity.framework.test.model.Continent;
import biz.devspot.entity.framework.test.model.Country;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import java.io.IOException;
import org.easymock.EasyMock;
import static org.easymock.EasyMock.expect;
import org.easymock.IMocksControl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataBackedObjectMapperTest {

    private EntityManager manager;
    
    public DataBackedObjectMapperTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        MongoClient mongo = new Fongo("Test Server").getMongo();
        EntityDao dao = new MongoDao(mongo.getDB("test"), new DataBackedObjectMapper());
        manager = new EntityManagerImpl(dao);
        EntityManagerFactory.setManager(manager);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSerialise() throws JsonProcessingException {
        manager.openTransaction();
        DataBackedObjectMapper objectMapper = new DataBackedObjectMapper();
        Continent continent = new Continent("Test Continent");
        DataBackedObjectHandlerFactory.getHandler().getDataObject(continent).setId("123");
        Country country = new Country("Test Country", continent);
        DataBackedObjectHandlerFactory.getHandler().getDataObject(country).setId("456");
        String result = objectMapper.writeValueAsString(country);
        System.out.println("result = " + result);
        JSONObject json = new JSONObject(result);
        assertEquals("456", json.getString("id"));
        assertEquals("123", json.getString("continent"));        
    }

    @Test
    public void testDeserialise() throws JsonProcessingException, IOException {
        manager.openTransaction();
        DataBackedObjectMapper objectMapper = new DataBackedObjectMapper();
        JSONObject json = new JSONObject();
        json.put("id", "123");
        json.put("name", "Test Country");
        json.put("continent", "456");
        Continent continent = new Continent("Test Continent");
        Country country = objectMapper.readValue(json.toString(), Country.class);
        assertEquals("123", country.getId());
        assertEquals("Test Country", country.getName());
        IMocksControl mocksControl = EasyMock.createControl();
        EntityManager mockEntityService = mocksControl.createMock(EntityManager.class);
        EntityManagerFactory.setManager(mockEntityService);
        expect(mockEntityService.findById("456")).andReturn(continent);
        mocksControl.replay();
        assertNotNull(country.getContinent());
        mocksControl.verify();
    }

}
