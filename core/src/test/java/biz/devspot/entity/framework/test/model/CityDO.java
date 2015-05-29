package biz.devspot.entity.framework.test.model;

import biz.devspot.entity.framework.core.model.AbstractDataObject;

public class CityDO extends AbstractDataObject{

    private String name;
    private Country country;

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
