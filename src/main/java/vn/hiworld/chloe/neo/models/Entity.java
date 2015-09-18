package vn.hiworld.chloe.neo.models;

import vn.hiworld.chloe.neo.annotation.GraphId;
import vn.hiworld.chloe.neo.annotation.NodeEntity;
import vn.hiworld.chloe.neo.annotation.Relationship;

@NodeEntity(label="")
public abstract class Entity {
	@GraphId
    protected Long id;
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
	
	public Entity(String ... params){
		
		
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
    
    public String getLabel(){
    	return this.getClass().getAnnotation(NodeEntity.class).label();
    }
    
    public String getParentLabel(){
    	return this.getClass().getAnnotation(NodeEntity.class).parent();
    }
    
    public String getChildLabel(){
    	return this.getClass().getAnnotation(NodeEntity.class).child();
    }
    

	
}
