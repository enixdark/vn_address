package vn.hiworld.chloe.neo.session;

import java.net.URISyntaxException;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import scala.util.control.Exception;
import vn.hiworld.chloe.main.ExampleQueryNeo4j;
public class CholeServiceFactory {
			
	private static String dbpath = "";
	private static CholeServiceFactory service = null;
	private GraphDatabaseService graphDb = null;
	
	
	private CholeServiceFactory(String dbpath) {
    	this.dbpath = dbpath;
    	graphDb = new GraphDatabaseFactory()
				.newEmbeddedDatabase(dbpath);
		try {
			registerShutdownHook(graphDb);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			System.out.println("hello");
			e.printStackTrace();
		}	
    }
	
	private static void registerShutdownHook(final GraphDatabaseService graphDb) throws URISyntaxException
	{
	    Runtime.getRuntime().addShutdownHook( new Thread()
	    {
	        @Override
	        public void run()
	        {
	            graphDb.shutdown();
	        }
	    } );
	}
	
	public static CholeServiceFactory createService(String path){
		if(service == null || dbpath != path){
			service = new CholeServiceFactory(path);
		}
		return service;
	}
	
	public GraphDatabaseService getGraphdb(){
		return graphDb;
	}
	
	public static CholeServiceFactory getInstance(){
		return service;
	}
	
	public static GraphDatabaseService getCholeSession(){
		return service.getGraphdb();
	}
	
	
	
	
   
}