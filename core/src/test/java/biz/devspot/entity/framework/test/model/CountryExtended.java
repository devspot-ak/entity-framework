package biz.devspot.entity.framework.test.model;

import biz.devspot.entity.framework.core.annotation.TypeAlias;

@TypeAlias(Country.class)
public class CountryExtended extends Country{

    public CountryExtended() {
    }

    public CountryExtended(String name, Continent continent) {
        super(name, continent);
    }

}
