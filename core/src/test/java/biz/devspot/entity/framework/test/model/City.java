package biz.devspot.entity.framework.test.model;

import biz.devspot.entity.framework.core.model.AbstractIdentifiedDataBackedObject;

public class City extends AbstractIdentifiedDataBackedObject<CityDO>{
    
    public City(String name, Country country) {
        data.setName(name);
        data.setCountry(country);
    }

    public String getName() {
        return data.getName();
    }

    public Country getCountry() {
        return data.getCountry();
    }

    @Override
    protected CityDO createDataObject() {
        return new CityDO();
    }
    
}
