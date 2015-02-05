package biz.devspot.entity.framework.test.model;

import biz.devspot.entity.framework.core.annotation.AssociatedEntity;
import biz.devspot.entity.framework.core.model.AbstractDataObject;
import java.util.ArrayList;
import java.util.List;

public class CountryDO extends AbstractDataObject{

    private String name;
    @AssociatedEntity
    private Continent continent;
    private List<City> cities = new ArrayList<City>();

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

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
