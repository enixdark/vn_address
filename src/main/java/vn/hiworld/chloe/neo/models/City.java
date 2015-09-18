package vn.hiworld.chloe.neo.models;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import vn.hiworld.chloe.neo.annotation.NodeEntity;
import vn.hiworld.chloe.neo.annotation.Relationship;



@NodeEntity(label="CITY")
public class City extends Entity{
	
	
	
    @Relationship(type = "DISTRICT", direction = "BELONGS_TO")
    private Set<District> districts;
    
    private Country country;
    
    public Set<District> getDistricts() {
		return districts;
	}

    public void setDistricts(Set<District> districts) {
		this.districts = districts;
	}

	public City() {
    }

    public City(String name) {
        this.name = name;
    }

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
   
}
