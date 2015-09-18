package vn.hiworld.chloe.service;

import vn.hiworld.chloe.neo.models.City;
import vn.hiworld.chloe.neo.models.Country;
import vn.hiworld.chloe.neo.models.District;

public class CityService extends GenericService<City,Country,District>{
//	private static City city = new City();
	
	@Override
	public Class<City> getEntityType() {
		// TODO Auto-generated method stub
		return City.class;
	}

	@Override
	public Class<Country> getEntityParent() {
		// TODO Auto-generated method stub
		return Country.class;
	}

	@Override
	public Class<District> getEntityChild() {
		return District.class;
	}
	
}
