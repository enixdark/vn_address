package vn.hiworld.chloe.neo.models;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vn.hiworld.chloe.neo.annotation.NodeEntity;


@NodeEntity(label="COMPANY")
public class Company extends Entity{
	private float lat;
	private float lng;
	
    
    public Company() {
    }

    public Company(String name) {
        this.name = name;
    }
    
}
