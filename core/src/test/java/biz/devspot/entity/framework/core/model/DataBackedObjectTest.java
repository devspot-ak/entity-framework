package biz.devspot.entity.framework.core.model;

import biz.devspot.entity.framework.core.EntityManager;
import biz.devspot.entity.framework.core.EntityManagerFactory;
import biz.devspot.entity.framework.core.EntityManagerImpl;
import biz.devspot.entity.framework.core.dao.mongo.MongoDao;
import biz.devspot.entity.framework.core.mapping.json.DataBackedObjectMapper;
import biz.devspot.entity.framework.test.model.Continent;
import biz.devspot.entity.framework.test.model.Country;
import biz.devspot.entity.framework.test.model.CountryExtended;
import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class DataBackedObjectTest {

    public DataBackedObjectTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    private MongoDao dao;
    
    @Before
    public void setUp() {
        MongoClient mongo = new Fongo("Test Server").getMongo();
        dao = new MongoDao(mongo.getDB("test"), new DataBackedObjectMapper());
        EntityManager manager = new EntityManagerImpl(dao);
        EntityManagerFactory.setManager(manager);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test() {
        EntityManager manager = EntityManagerFactory.getManager();
        manager.openTransaction();
        Continent<Country> continent = new Continent("Test Continent");
        Country country = new Country("Test Country", continent);
        manager.commitTransaction();
        assertTrue(continent.getId().startsWith(Continent.class.getName() + "_"));
        Continent result = (Continent) manager.findById(continent.getId());
        assertEquals(continent.getId(), result.getId());
        assertEquals(1, continent.getCountries().size());
        assertEquals(country.getId(), continent.getCountries().get(0).getId());
    }

    @Test
    public void testExtended() {
        EntityManager manager = EntityManagerFactory.getManager();
        manager.openTransaction();
        Continent<CountryExtended> continent = new Continent("Test Continent");
        Country country = new CountryExtended("Test Country", continent);
        manager.commitTransaction();
        assertTrue(continent.getId().startsWith(Continent.class.getName() + "_"));
        assertTrue(country.getId().startsWith(CountryExtended.class.getName() + "_"));
        assertEquals(1, continent.getCountries().size());
        assertEquals(country.getId(), continent.getCountries().get(0).getId());
    }

    @Test
    public void testUpdate() {
        EntityManager manager = EntityManagerFactory.getManager();
        manager.openTransaction();
        Continent<Country> continent = new Continent("Test Continent");
        Country country = new Country("Test Country", continent);
        manager.commitTransaction();
        Continent<Country> result = (Continent) manager.findById(continent.getId());
        assertEquals("Test Continent", result.getName());
        manager.openTransaction();
        result.setName("Other Continent");
        manager.commitTransaction();
        result = (Continent) manager.findById(continent.getId());
        assertEquals("Other Continent", result.getName());
        manager.openTransaction();
        result.getCountries().get(0).setName("Other Country");
        manager.commitTransaction();
        result = (Continent) manager.findById(continent.getId());
        assertEquals("Other Country", result.getCountries().get(0).getName());
        dao.clearCache();
        country = (Country) manager.findById(country.getId());
        manager.openTransaction();
        country.setContinent(null);
        manager.commitTransaction();
        assertEquals(null, country.getContinent());
    }
    
    @Test
    public void testUpdate2() {
        EntityManager manager = EntityManagerFactory.getManager();
        manager.openTransaction();
        Continent<Country> continent = new Continent("Test Continent");
        Country country = new Country("Test Country", null);
        manager.commitTransaction();
        assertEquals(null, country.getContinent());
        dao.clearCache();
        country = (Country) manager.findById(country.getId());
        assertEquals(null, country.getContinent());
        manager.openTransaction();
        country.setContinent(continent);
        manager.commitTransaction();
        assertEquals(continent, country.getContinent());
    }

    @Test
    public void testUpdateExtended() {
        EntityManager manager = EntityManagerFactory.getManager();
        manager.openTransaction();
        Continent<Country> continent = new Continent("Test Continent");
        Country country = new CountryExtended("Test Country", continent);
        manager.commitTransaction();
        Continent<Country> result = (Continent) manager.findById(continent.getId());
        assertEquals("Test Continent", result.getName());
        manager.openTransaction();
        result.setName("Other Continent");
        manager.commitTransaction();
        result = (Continent) manager.findById(continent.getId());
        assertEquals("Other Continent", result.getName());
        manager.openTransaction();
        result.getCountries().get(0).setName("Other Country");
        manager.commitTransaction();
        result = (Continent) manager.findById(continent.getId());
        assertEquals("Other Country", result.getCountries().get(0).getName());
    }

}
