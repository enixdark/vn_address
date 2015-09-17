package vn.hiworld.chloe.neo.models;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;


@NodeEntity(label="DISTRICT")
public class District extends Entity{
	
	
    @Relationship(type = "WARD", direction = "BELONGS_TO")
    private Set<Ward> wards = new HashSet<Ward>();
    
    public District() {
    }

    public District(String name) {
        this.name = name;
    }
	
   
}
