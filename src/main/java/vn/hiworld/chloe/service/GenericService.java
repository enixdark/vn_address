package vn.hiworld.chloe.service;

import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.kernel.impl.core.NodeProxy;

import com.google.common.collect.Iterators;

import vn.hiworld.chloe.neo.annotation.NodeEntity;
import vn.hiworld.chloe.neo.models.Entity;
import vn.hiworld.chloe.neo.models.EntityIterator;
import vn.hiworld.chloe.neo.session.CholeServiceFactory;

public abstract class GenericService<T,P,C> implements Service<T> {

	private enum TYPE{
		DEPTH_LIST,
		DEPTH_ENTITY,
		DEPTH_LEVEL_1,
		DEPTH_LEVEL_2,
		DEPTH_ALL
	}
//	private static final int DEPTH_ENTITY = 1;
	
	protected GraphDatabaseService session =  CholeServiceFactory.getInstance().getGraphdb();

	@Override
	public Iterable<T> findAll(){
		String query = String.format("match (n:%s) return n", getEntityType().getAnnotation(NodeEntity.class).label()); 
		return new EntityIterator<T>(session.execute(query),getEntityType());
	}
	
	@Override
	public Iterable<T> findLimit(int limit){
		String query = String.format("match (n:%s) return n LIMIT %d", getEntityType().getAnnotation(NodeEntity.class).label(),limit); 
		return new EntityIterator<T>(session.execute(query),getEntityType());
	}

	@Override
	public T find(Long id) {
		String query = String.format("match (n:%s) where n.id = %d return n", getEntityType().getAnnotation(NodeEntity.class).label(), id); 
		return (new EntityIterator<T>(session.execute(query),getEntityType())).iterator().next();
	}
	
	@Override
	public Iterable<T> findBy(Map<String,String> parameters) {
		String params = "where ";
		for(Entry<String,String> elem : parameters.entrySet()){
			params += String.format("n.%s = '%s'",elem.getKey(),elem.getValue());
		}
		String query = String.format("match (n:%s) %s return n", getEntityType().getAnnotation(NodeEntity.class).label(),params); 
		return new EntityIterator<T>(session.execute(query),getEntityType());
	}
	
	@Override
	public Result query(String query){
		return session.execute(query);
	}
	
	public Iterable<P> findParent(Map<String,String> parameters){
		String params = "where ";
		for(Entry<String,String> elem : parameters.entrySet()){
			params += String.format("n.%s = '%s'",elem.getKey(),elem.getValue());
		}
		String query = String.format("match (n:%s)<-[*1]-(p) %s return p", 
				getEntityType().getAnnotation(NodeEntity.class).label()
				,params);
		return new EntityIterator<P>(session.execute(query),getEntityParent());
	}
	
//	public Iterable<C> findChilds(Map<String,String> parameters){
//		String params = "where ";
//		for(Entry<String,String> elem : parameters.entrySet()){
//			params += String.format("n.%s = '%s'",elem.getKey(),elem.getValue());
//		}
//		String query = String.format("match (n:%s)-[r]->(p) %s return p", 
//				getEntityType().getAnnotation(NodeEntity.class).label()
//				,params);
//		return new EntityIterator<C>(session.execute(query),getEntityChild());
//	}
	
	public Result queryResult(String query , String type, Map<String,String> parameters){
		String params = "where ";
		for(Entry<String,String> elem : parameters.entrySet()){
			params += String.format("n.%s = '%s'",elem.getKey(),elem.getValue());
		}
		String q = String.format(query, type , params);

		return session.execute(q);
	}
	
	public Result findChilds(String type,Map<String,String> parameters){
		return queryResult("match (n:%s)-[r]->(p) %s return p",type,parameters);
	}
	
	public Result findSiblings(String type,Map<String,String> parameters){
		String q = String.format("match (n:%s)<-[*1]-(p)-[*1]->(n2:%s) %s return n2", 
				type);
		return queryResult(q, type, parameters);
	}
	
	public Result findCompanyOf(String type,Map<String,String> parameters){
		return queryResult("match (n:%s)-[*1..]-(c:COMPANY) %s return c", type, parameters);
	}
	
	
	public Result getGeoLocation(String type,Map<String,String> parameters){
		return queryResult("match (n:%s) %s return n.lat, n.lng", type, parameters);
	}
	
	
	
	
	
	
	public void pprintNode(Result results){
		System.out.println(results.resultAsString());
	}
	
	
	
	
	
	
//
//	@Override
//	public void delete(Long id) {
//		session.delete(session.load(getEntityType(), id));
//	}
//
//	@Override
//	public T createOrUpdate(T entity) {
//		session.save(entity, DEPTH_ENTITY);
//		return find(((Entity)entity).getId());
//	}
//
	public abstract Class<T> getEntityType();
	public abstract Class<P> getEntityParent();
	public abstract Class<C> getEntityChild();

}
