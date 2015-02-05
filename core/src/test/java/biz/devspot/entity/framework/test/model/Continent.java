package biz.devspot.entity.framework.test.model;

import biz.devspot.entity.framework.core.EntityManagerFactory;
import biz.devspot.entity.framework.core.model.AbstractIdentifiedDataBackedObject;
import biz.devspot.entity.framework.core.query.QueryBuilder;
import java.util.List;

public class Continent<C extends Country> extends AbstractIdentifiedDataBackedObject<ContinentDO>{

    public Continent(String name) {
        data.setName(name);
    }

    public String getName() {
        return data.getName();
    }

    public void setName(String name) {
        data.setName(name);
    }
    
    public List<C> getCountries(){
        return (List<C>) EntityManagerFactory.getManager().find(Country.class, new QueryBuilder().where("continent").isEqualTo(getId()).build());
    }

    @Override
    protected ContinentDO createDataObject() {
        return new ContinentDO();
    }
    
}
