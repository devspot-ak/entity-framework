package biz.devspot.entity.framework.test.model;

import biz.devspot.entity.framework.core.annotation.ManagedEntity;

public class World {

    @ManagedEntity
    private Continent continent;

    public Continent getContinent() {
        return continent;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }

}
