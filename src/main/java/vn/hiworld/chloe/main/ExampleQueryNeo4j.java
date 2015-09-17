package vn.hiworld.chloe.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.spark.SparkConf;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.api.properties.Property;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.protobuf.DescriptorProtos.SourceCodeInfo.Location;

import scala.Function1;
import scala.Tuple2;
import scala.Tuple3;
import scala.Tuple4;
import scala.Tuple5;
import scala.collection.mutable.ListBuffer;
import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;
import vn.hiworld.chloe.neo.relationship.NeoRel;
import vn.hiworld.chloe.service.CountryService;
import vn.hiworld.chloe.service.CountryServiceImpl;
import vn.hiworld.chloe.util.CleanWord;
import vn.hiworld.chloe.util.GeoCoding;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import org.neo4j.graphdb.RelationshipType;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.neo4j.graphdb.Label;
import com.google.gson.JsonParser;


public class ExampleQueryNeo4j {
	public final static String dbpath = "/usr/local/neo4j/data/graph.db";
	
	
	

	
	private static void registerShutdownHook( final GraphDatabaseService graphDb ) throws URISyntaxException
	{
	    // Registers a shutdown hook for the Neo4j instance so that it
	    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
	    // running application).
		SessionFactory sessionFactory = new SessionFactory("com.mycompany.myapp.domain");
		Session session = sessionFactory.openSession("http://@localhost:7474","neo4j","123456");
//		session.beginTransaction();
		session.query("match (n) return n LIMIT 1", new HashMap<String,Object>());
		
//		session.query("match (n) return count(n)",new HashMap());
//		URI uri = new URI("http://neo4j:123456@localhost:7474");
//		 String auth = uri.getUserInfo();
//		System.out.println(uri.getScheme() + "://" + uri.toString().substring(uri.toString().indexOf(auth) + auth.length()+1));
//		CountryServiceImpl e = new CountryServiceImpl();
//		e.find(1L);
		
//	    Runtime.getRuntime().addShutdownHook( new Thread()
//	    {
//	        @Override
//	        public void run()
//	        {
//	            graphDb.shutdown();
//	        }
//	    } );
	}
	
	
	
	
	
	
	
	
	
	
	/*
	 * Example for using neo4j driver to make node from file csv that data is cleaned without geocoding
	 */
	public static void main(String[] args) throws FileNotFoundException, URISyntaxException{
		
	
		final GraphDatabaseService services = new GraphDatabaseFactory()
				.newEmbeddedDatabase(ExampleQueryNeo4j.dbpath);
		NeoRel type = NeoRel.BELONGS_TO;
		registerShutdownHook(services);		
		
		
		
		try ( Transaction tx = services.beginTx() )
		{			
			
			
			
//			throw new Exception("");
		    tx.success();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally {
//			services.shutdown();
		}
		
		

		
	}
}

