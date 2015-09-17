package vn.hiworld.chloe.neo.models;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;


@NodeEntity(label="CITY")
public class City extends Entity{
	
    @Relationship(type = "DISTRICT", direction = "BELONGS_TO")
    private Set<District> districts = new HashSet<District>();
    
    public City() {
    }

    public City(String name) {
        this.name = name;
    }
	
   
}
