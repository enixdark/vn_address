package vn.hiworld.chloe.service;

import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

public class Neo4jSessionFactory {
	private final static SessionFactory sessionFactory = new SessionFactory("graph");
    private static Neo4jSessionFactory factory = new Neo4jSessionFactory();

    public static Neo4jSessionFactory getInstance() {
            return factory;
    }

    private Neo4jSessionFactory() {
    }

    public Session getNeo4jSession() {
            return sessionFactory.openSession("http://neo4j:123456@localhost:7474");
    }
}
