package vn.hiworld.chloe.neo.models;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vn.hiworld.chloe.neo.annotation.NodeEntity;
import vn.hiworld.chloe.neo.annotation.Relationship;




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
