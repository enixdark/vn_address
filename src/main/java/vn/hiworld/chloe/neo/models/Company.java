package vn.hiworld.chloe.neo.models;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;


@NodeEntity(label="COMPANY")
public class Company extends Entity{
	@GraphId
	private float lat;
	private float lng;
	
    
    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }
    
}
