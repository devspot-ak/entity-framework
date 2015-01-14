
package biz.devspot.entity.framework.core.model;

import biz.devspot.entity.framework.core.EntityManager;
import biz.devspot.entity.framework.core.EntityManagerFactory;
import biz.devspot.entity.framework.core.EntityManagerImpl;
import biz.devspot.entity.framework.core.dao.mongo.MongoDao;
import biz.devspot.entity.framework.core.mapping.json.EntityObjectMapper;
import biz.devspot.entity.framework.test.model.City;
import biz.devspot.entity.framework.test.model.Continent;
import biz.devspot.entity.framework.test.model.Country;
import biz.devspot.entity.framework.test.model.CountryExtended;
import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ManagedEntityTest {

    public ManagedEntityTest() {
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
        EntityManager manager = new EntityManagerImpl(new MongoDao(mongo.getDB("test"), new EntityObjectMapper()));
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
        manager.manage(continent);
        Country country = new Country("Test Country", continent);
        manager.manage(country);
        City city = new City("Test City", country);
        List<City> cities = new ArrayList<City>();
        cities.add(city);
        country.setCities(cities);
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
        manager.manage(continent);
        Country country = new CountryExtended("Test Country", continent);
        manager.manage(country);
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
        manager.manage(continent);
        Country country = new Country("Test Country", continent);
        manager.manage(country);
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
    
    @Test
    public void testUpdateExtended() {
        EntityManager manager = EntityManagerFactory.getManager();
        manager.openTransaction();
        Continent<Country> continent = new Continent("Test Continent");
        manager.manage(continent);
        Country country = new CountryExtended("Test Country", continent);
        manager.manage(country);
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