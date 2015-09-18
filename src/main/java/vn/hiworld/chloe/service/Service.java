package vn.hiworld.chloe.service;

import java.util.Map;

import org.neo4j.graphdb.Result;

import vn.hiworld.chloe.neo.models.Entity;

public interface Service<T> {

    Iterable<T> findAll();
    
    Iterable<T> findLimit(int limit);
    
    Iterable<T> findBy(Map<String,String> parameters);

    Result query(String query);
    
    T find(Long id);

//    void delete(Long id);
//
//    T createOrUpdate(T object);

}
