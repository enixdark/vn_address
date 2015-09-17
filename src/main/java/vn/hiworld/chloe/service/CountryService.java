package vn.hiworld.chloe.service;


import org.neo4j.ogm.session.Session;

import vn.hiworld.chloe.neo.models.*;


public class CountryService extends GenericService<Country> {

	private static final int DEPTH_LIST = 0;
	private static final int DEPTH_ENTITY = 1;
	private Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();
	
	@Override
	public Iterable<Country> findAll() {
		// TODO Auto-generated method stub
		return session.loadAll(Country.class, DEPTH_LIST);
	}

	@Override
	public Country find(Long id) {
		// TODO Auto-generated method stub
		return session.load(Country.class, id, DEPTH_ENTITY);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		session.delete(session.load(Country.class, id));
	}

	@Override
	public Country createOrUpdate(Country entity) {
		// TODO Auto-generated method stub
		session.save(entity, DEPTH_ENTITY);
	    return find(entity.getId());
	}

	@Override
	public Class<Country> getEntityType() {
		// TODO Auto-generated method stub
		return null;
	}
	

}
