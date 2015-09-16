package vn.hiworld.chloe.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class CleanWord {
	
	public final static String REGEX = "[.]";
	private final static String DISTRICTS = "districts";
	private final static String STREETS = "streets";
	private final static String CITIES = "cities";
	private final static String COUNTRIES = "countries";
	private final static String CITY_NAME = "city_name";
	private final static String WARDS = "wards";


	
	static JsonObject jsonObject = null;
			
	public static JsonObject getLocation() throws JsonIOException, JsonSyntaxException, FileNotFoundException{
		if(jsonObject == null){
			jsonObject = new JsonParser().parse(
					new BufferedReader(
						new FileReader("/home/thuy/workspace1/Db/Neo.json")
						)
					)
					.getAsJsonObject();
		}
		return jsonObject;
	}
	
	public static String extract(String text, String type) throws JsonIOException, JsonSyntaxException, FileNotFoundException{
		String temp = text.toLowerCase();
		for(Entry<String, JsonElement> elem: getLocation().get(type).getAsJsonObject().entrySet()){
			Pattern p = Pattern.compile(elem.getKey(), Pattern.UNICODE_CASE );
			if(p.matcher(temp).lookingAt()){
				String s = elem.getValue().getAsString() + " ";
				return temp.replaceAll(p.pattern(),s);
			}
		}
		return temp;	
	}
	
	public static String extract_city_name(String text) throws JsonIOException, JsonSyntaxException, FileNotFoundException{
		for(Entry<String, JsonElement> elem: getLocation().get(CITY_NAME).getAsJsonObject().entrySet()){
			if(text.contains(elem.getKey())){
				return text.replaceAll(elem.getKey(),elem.getValue().getAsString());
			}
		}
		return text;
	}
	
	public static String extractWard(String text) throws JsonIOException, JsonSyntaxException, FileNotFoundException{
		return extract(text, WARDS);
	}
	
	public static String extractCountry(String text) throws JsonIOException, JsonSyntaxException, FileNotFoundException{
		return extract(text, COUNTRIES);
	}
	
	public static String extractStreet(String text) throws JsonIOException, JsonSyntaxException, FileNotFoundException{
		return extract(text, STREETS);
	}
	
	public static String extractDistrict(String text) throws JsonIOException, JsonSyntaxException, FileNotFoundException{
		return extract(text, DISTRICTS);
	}
	
	public static String extractCity(String text) throws JsonIOException, JsonSyntaxException, FileNotFoundException{
		return extract(extract_city_name(text.replaceAll("\\d+","").trim()), CITIES);
	}
}
