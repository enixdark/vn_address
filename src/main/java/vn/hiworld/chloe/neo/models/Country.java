package vn.hiworld.chloe.neo.models;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vn.hiworld.chloe.neo.annotation.NodeEntity;
import vn.hiworld.chloe.neo.annotation.Relationship;

@NodeEntity(label="COUNTRIES")
public class Country extends Entity{
	
    @Relationship(type = "CITY", direction = "BELONGS_TO")
    private Set<City> cities = new HashSet<City>();
    
    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }
}
