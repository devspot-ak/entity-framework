package biz.devspot.entity.framework.test.model;

import biz.devspot.entity.framework.core.annotation.AssociatedEntity;
import biz.devspot.entity.framework.core.model.AbstractManagedEntity;

public class City extends AbstractManagedEntity{

    private String name;
    @AssociatedEntity
    private Country country;

    public City() {
    }

    public City(String name, Country country) {
        this.name = name;
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
    
}
