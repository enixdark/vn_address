package vn.hiworld.chloe.neo.models;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;


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
