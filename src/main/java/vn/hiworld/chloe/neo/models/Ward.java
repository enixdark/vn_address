package vn.hiworld.chloe.neo.models;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vn.hiworld.chloe.neo.annotation.NodeEntity;
import vn.hiworld.chloe.neo.annotation.Relationship;




@NodeEntity(label="WARD")
public class Ward extends Entity{
	
    @Relationship(type = "STREET", direction = "BELONGS_TO")
    private Set<Street> streets = new HashSet<Street>();
    
    public Ward() {
    }

    public Ward(String name) {
        this.name = name;
    }
	
}
