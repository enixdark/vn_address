package vn.hiworld.chloe.neo.models;

import java.util.HashSet;
import java.util.Set;

import vn.hiworld.chloe.neo.annotation.NodeEntity;
import vn.hiworld.chloe.neo.annotation.Relationship;

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
