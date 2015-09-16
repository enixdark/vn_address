package vn.hiworld.chloe.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
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

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;

import scala.Function1;
import scala.Tuple2;
import scala.Tuple3;
import scala.Tuple4;
import scala.Tuple5;
import scala.collection.mutable.ListBuffer;
import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;
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

public class ExampleNeo4jCSV {
	public final static String dbpath = "/usr/local/neo4j/data/graph.db";
	
	
	public static enum NeoLabel implements Label {
		COMPANY,
		DISTRICT,
		WARD,
		STREET,
		CITY,
		COUNTRIES
	}

	public static enum NeoRelTypes implements RelationshipType{
		NULL,
		BELONGS_TO
//		DIRECT_TO_DISTRICT,
//		DIRECT_TO_STREET,
//		DIRECT_TO_COMPANY,
//		DIRECT_TO_WARD
		
	}
	private static void registerShutdownHook( final GraphDatabaseService graphDb )
	{
	    // Registers a shutdown hook for the Neo4j instance so that it
	    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
	    // running application).
	    Runtime.getRuntime().addShutdownHook( new Thread()
	    {
	        @Override
	        public void run()
	        {
	            graphDb.shutdown();
	        }
	    } );
	}
	
	
	public static Tuple2<HashMap<String, Node>,Node> process(GraphDatabaseService services,
			Map<String, Node> map,
			String key,
			NeoLabel label
	){
		return process(services,map,key,"1",null,label,NeoRelTypes.NULL);
	}
	
	
	public static Tuple2<HashMap<String, Node>,Node> process(GraphDatabaseService services,
			Map<String, Node> map,
			String key,
			String rel_name,
			Node rel_node,
			NeoLabel label,
			NeoRelTypes type
	)
	{
		Node node;
		if(!map.containsKey(rel_name+":"+key)){
			node = services.createNode(label);
			node.setProperty("name", key);
			map.put(rel_name+":"+key,node);
			if(rel_node != null){
				rel_node.createRelationshipTo(node, type);
			}
		}
		else{
			node = map.get(rel_name+":"+key);
		}
		return new Tuple2(map,node);
	}
	
	
	
	
	
	
	
	/*
	 * Example for using neo4j driver to make node from file csv that data is cleaned without geocoding
	 */
	public static void main(String[] args) throws FileNotFoundException{
		
		
		GeoCoding context = GeoCoding.createGeoCoding("AIzaSyDTGMoAL8ve-u4zCe3M8V1nEQGQCVV3YzU");
		SparkConf conf = new SparkConf()
				.setAppName("default")
				.set("spark.master", "local[2]");

		SparkContext sc = new SparkContext(conf);
		SQLContext sqlContext = new SQLContext(sc);
		String path = "/home/thuy/data/super_mini_city.csv";
		DataFrame df = sqlContext.read().format("com.databricks.spark.csv").option("header", "true").load(path);
		List<Row> cities = df.select("3","9").javaRDD().collect();
		
		
		final GraphDatabaseService services = new GraphDatabaseFactory()
				.newEmbeddedDatabase(ExampleNeo4jCSV.dbpath);
		NeoRelTypes type = NeoRelTypes.BELONGS_TO;
		registerShutdownHook(services);		
		
		
		
		try ( Transaction tx = services.beginTx() )
		{			
			
			
			Map<String,Node> map_coutries = new HashMap<String, Node>();

			for(Row city : cities){
				
				Node country_node = null ,company_node;
				String country_name, cities_name, district_name, ward_name, street_name;
				Map<String,Tuple3<String,NeoLabel,NeoRelTypes>> map = 
						new HashMap<String,Tuple3<String,NeoLabel,NeoRelTypes>>();
				
				String company_name = city.get(0).toString();

				
				Map<String,String> address = context.getGeoInformation(city.get(1).toString(), city.get(0).toString());
				country_name = address.get(GeoCoding.COUNTRY) == null ? "" : address.get(GeoCoding.COUNTRY).toLowerCase();
				cities_name = CleanWord.extractCity(address.get(GeoCoding.CITY)  == null ? (
						address.get(GeoCoding.LOCALITY) == null ? "" : address.get(GeoCoding.LOCALITY))
						: address.get(GeoCoding.CITY).toLowerCase());
				district_name = CleanWord.extractDistrict((address.get(GeoCoding.DISTRICT)  == null ? (
						address.get(GeoCoding.LOCALITY) == null ? "" : address.get(GeoCoding.LOCALITY))
						: address.get(GeoCoding.DISTRICT).toLowerCase()));
				ward_name = address.get(GeoCoding.WARD)  == null ? "" : address.get(GeoCoding.WARD).toLowerCase();
				street_name = address.get(GeoCoding.STREET) == null ? "" : address.get(GeoCoding.STREET).toLowerCase();
				
				map.put("country_name",new Tuple3<String,NeoLabel,NeoRelTypes>(
						country_name,
						NeoLabel.COUNTRIES,
						NeoRelTypes.NULL
						));
				
				map.put("cities_name",new Tuple3<String,NeoLabel,NeoRelTypes>(
						cities_name,NeoLabel.CITY,NeoRelTypes.BELONGS_TO
						));
				
				
				map.put("district_name",new Tuple3<String,NeoLabel,NeoRelTypes>(
						district_name,NeoLabel.DISTRICT,NeoRelTypes.BELONGS_TO
						));
				map.put("ward_name",new Tuple3<String,NeoLabel,NeoRelTypes>(
						ward_name,NeoLabel.WARD,NeoRelTypes.BELONGS_TO
						));
				
				map.put("street_name",new Tuple3<String,NeoLabel,NeoRelTypes>(
						street_name,NeoLabel.STREET,NeoRelTypes.BELONGS_TO
						));

			

				Tuple2<HashMap<String, Node>,Node> tcountries = process(services, map_coutries, country_name, NeoLabel.COUNTRIES);
				map_coutries = tcountries._1();
				country_node = tcountries._2();
				Node node_flag = country_node;
				String name_flag = country_name;
				for(String name : Arrays.asList("cities_name", "district_name", "ward_name", "street_name")){
					Tuple3<String,NeoLabel,NeoRelTypes> item = map.get(name);
					if(!item._1().isEmpty()){
						Tuple2<HashMap<String, Node>,Node> node = 
						process(
								services, 
								map_coutries, 
								item._1(), 
								name_flag, 
								node_flag,
								item._2(),
								item._3());
						map_coutries = node._1();
						node_flag = node._2();
						name_flag = item._1();
					}
				}
				
				
				
				
				
				company_node = services.createNode(NeoLabel.COMPANY);
				company_node.setProperty("name", company_name);
				company_node.setProperty("address", city.get(1).toString());
				company_node.setProperty("location", address.get("location") == null ? "" : address.get("location"));
				node_flag.createRelationshipTo(company_node, NeoRelTypes.BELONGS_TO );
				Thread.sleep(1000);
			}
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
//		
		
//		Pattern regexPattern = Pattern.compile("(tp[1-9.\\s]|thanh pho\\s|thành phố|thanh pho)", Pattern.UNICODE_CASE);
//		System.out.println(CleanWord.extractCity("tphố hcm"));

		
	}
}

