package vn.hiworld.chloe.models;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
public class Location {
	
	@SerializedName("address_components")
	private List<JsonObject> l_address_components;

	public List<JsonObject> getList() {
		return l_address_components;
	}

	public void setList(List<JsonObject> list) {
		this.l_address_components = l_address_components;
	}
	
}
