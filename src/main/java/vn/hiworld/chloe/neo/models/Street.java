package vn.hiworld.chloe.neo.models;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;


@NodeEntity(label="STREET")
public class Street extends Entity {
	
    @Relationship(type = "COMPANY", direction = "BELONGS_TO")
    private Set<Company> companies = new HashSet<Company>();
    
    public Street() {
    }

    public Street(String name) {
        this.name = name;
    }

	
	
	
}
