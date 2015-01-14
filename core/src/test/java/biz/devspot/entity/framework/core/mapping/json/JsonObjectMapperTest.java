package biz.devspot.entity.framework.core.mapping.json;

import biz.devspot.entity.framework.test.model.Continent;
import biz.devspot.entity.framework.test.model.Country;
import biz.devspot.entity.framework.core.EntityManager;
import biz.devspot.entity.framework.core.EntityManagerFactory;
import biz.devspot.entity.framework.test.model.World;
import com.fasterxml.jackson.core.JsonProcessingException;
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

public class JsonObjectMapperTest {

    public JsonObjectMapperTest() {
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
    public void testSerialise() throws JsonProcessingException {
        EntityObjectMapper objectMapper = new EntityObjectMapper();
        Continent continent = new Continent("Test Continent");
        continent.setId("123");
        Country country = new Country("Test Country", continent);
        String result = objectMapper.writeValueAsString(country);
        System.out.println("result = " + result);
        JSONObject json = new JSONObject(result);
        assertEquals("123", json.getString("continent"));
        result = objectMapper.writeValueAsString(continent);
        System.out.println("result = " + result);
        json = new JSONObject(result);
        assertEquals("123", json.getString("id"));
    }

    @Test
    public void testDeserialise() throws JsonProcessingException, IOException {
        EntityObjectMapper objectMapper = new EntityObjectMapper();
        JSONObject json = new JSONObject();
        json.put("id", "123");
        json.put("name", "Test Country");
        json.put("continent", "456");
        json.put("cities", new JSONArray("[{'name': 'Test City', 'country': '123'}]"));
        Continent continent = new Continent("Test Continent");
        continent.setId("456");
        Country country = objectMapper.readValue(json.toString(), Country.class);
        assertEquals("123", country.getId());
        assertEquals("Test Country", country.getName());
        IMocksControl mocksControl = EasyMock.createControl();
        EntityManager mockEntityService = mocksControl.createMock(EntityManager.class);
        EntityManagerFactory.setManager(mockEntityService);
        expect(mockEntityService.findById("456")).andReturn(continent);
        mocksControl.replay();
        assertEquals("456", country.getContinent().getId());
        mocksControl.verify();
        assertEquals("Test City", country.getCities().get(0).getName());
    }
    
    @Test
    public void testWrapperObject() throws Exception{
        EntityObjectMapper objectMapper = new EntityObjectMapper();
        Continent continent = new Continent("Test Continent");
        continent.setId("123");
        World world = new World();
        world.setContinent(continent);
        String result = objectMapper.writeValueAsString(world);
        System.out.println("result = " + result);
        JSONObject json = new JSONObject(result);
        assertEquals("123", json.getString("continent"));
        IMocksControl mocksControl = EasyMock.createControl();
        EntityManager mockEntityService = mocksControl.createMock(EntityManager.class);
        EntityManagerFactory.setManager(mockEntityService);
        expect(mockEntityService.findById("123")).andReturn(continent);
        mocksControl.replay();
        world = objectMapper.readValue(result, World.class);
        assertEquals(continent, world.getContinent());
        mocksControl.verify();
    }

}
