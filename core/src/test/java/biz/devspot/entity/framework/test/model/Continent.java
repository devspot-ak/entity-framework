package biz.devspot.entity.framework.test.model;

import biz.devspot.entity.framework.core.EntityManagerFactory;
import biz.devspot.entity.framework.core.annotation.LinkedEntities;
import biz.devspot.entity.framework.core.model.AbstractManagedEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;

public class Continent<C extends Country> extends AbstractManagedEntity{

    private String name;

    public Continent() {
    }

    public Continent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @LinkedEntities
    public List<C> getCountries(){
        System.out.println("getCountries");
        return (List<C>) EntityManagerFactory.getManager().getLinkedEntities(this, Country.class, "continent");
    }
    
}
