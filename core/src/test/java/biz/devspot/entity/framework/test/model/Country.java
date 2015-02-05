package biz.devspot.entity.framework.test.model;

import biz.devspot.entity.framework.core.model.AbstractIdentifiedDataBackedObject;
import java.util.List;

public class Country extends AbstractIdentifiedDataBackedObject<CountryDO> {

    public Country(String name, Continent continent) {
        data.setName(name);
        data.setContinent(continent);
    }

    public String getName() {
        return data.getName();
    }

    public void setName(String name) {
        data.setName(name);
    }

    public Continent getContinent() {
        return data.getContinent();
    }
    
    public void setContinent(Continent continent){
        data.setContinent(continent);
    }

    public List<City> getCities() {
        return data.getCities();
    }

    public void setCities(List<City> cities) {
        data.setCities(cities);
    }

    @Override
    protected CountryDO createDataObject() {
        return new CountryDO();
    }

}
