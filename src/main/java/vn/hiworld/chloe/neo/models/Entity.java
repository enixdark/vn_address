package vn.hiworld.chloe.neo.models;

import org.neo4j.ogm.annotation.GraphId;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Entity {

	@GraphId
    //@JsonProperty("id")
    protected Long id;
    
    
    
    @JsonProperty("name")
    protected String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || id == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (!id.equals(entity.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (id == null) ? -1 : id.hashCode();
    }

	
}
