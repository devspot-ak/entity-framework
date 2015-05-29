package biz.devspot.entity.framework.test.model;

import biz.devspot.entity.framework.core.model.AbstractDataObject;

public class CountryDO extends AbstractDataObject{

    private String name;
    private Continent continent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }
}
