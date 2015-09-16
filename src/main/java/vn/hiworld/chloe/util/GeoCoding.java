package vn.hiworld.chloe.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.PendingResult;
import com.google.maps.model.AddressComponent;
import com.google.maps.model.GeocodingResult;

import se.walkercrou.places.GooglePlaces;
import se.walkercrou.places.Place;

public class GeoCoding {
	
	
	private static GeoCoding geoCodingContext = null;
	private static GeoApiContext geoContext = null;
	private static String APIKEY = null;
	private static GooglePlaces place = null;
	private final static List<String> ADDR_COMPONENTS = Arrays.asList(
		"locality","sublocality_level_1","country","route","administrative_area_level_1","administrative_area_level_2"
	);
	
	public static final String COUNTRY = "country";
	public static final String STREET = "route";
	public static final String WARD = "sublocality_level_1";
	public static final String DISTRICT = "administrative_area_level_2";
	public static final String CITY = "administrative_area_level_1";
	public static final String LOCALITY = "locality";

	
	
	
	private GeoCoding(String ApiKey){
		APIKEY = ApiKey;
		geoContext = new GeoApiContext().setApiKey(APIKEY);
		place = new GooglePlaces(APIKEY);
	}
	
	public static GeoCoding createGeoCoding(String ApiKey){
		if(geoCodingContext == null){
			geoCodingContext = new GeoCoding(ApiKey);
		}
		return geoCodingContext;
	}
	
	public GeoApiContext getGeoContext(){
		return geoContext;
	}
	
	public GooglePlaces getPlaceContext(){
		return place;
	}
	
	public String getApiKey(){
		return APIKEY;
	}
	
	public void setApiKey(final String ApiKey){
		APIKEY = ApiKey;
		geoContext.setApiKey(APIKEY);
		place.setApiKey(APIKEY);
	}
	
	public Map<String,String> getGeoInformation(final String address, final String company_name){
		final Map<String,String> data = new HashMap<String, String>();
		GeocodingApiRequest req = GeocodingApi.newRequest(geoContext).address(address);
		try {
			GeocodingResult[] results = req.await();
		    if(results.length == 0){
		    	List<Place> places = place.getPlacesByQuery(company_name, 
						GooglePlaces.DEFAULT_RESULTS);
				
				if(!places.isEmpty()){
				  for(se.walkercrou.places.AddressComponent a: places.get(0).getAddressComponents()){
			    		String type = a.getTypes().get(0);
				    	if(ADDR_COMPONENTS.contains(type)){
				    		data.put(type,a.getLongName());
				    	}
			      }
				  data.put("location",String.format("%f, %f",places.get(0).getLatitude(),places.get(0).getLongitude()));
				}
		    }
		    else{
		    	AddressComponent[] add = results[0].addressComponents;
		    	
		    	for(AddressComponent a: add){
		    		String type = a.types[0].toString().toLowerCase();
			    	if(ADDR_COMPONENTS.contains(type)){
			    		data.put(type,a.longName);
			    	}
		    	}
				data.put("location",String.format("%f, %f",results[0].geometry.location.lat,
						results[0].geometry.location.lng));
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}

//		req.setCallback(new PendingResult.Callback<GeocodingResult[]>() {
//			  @Override
//			  public void onResult(GeocodingResult[] result) {
//				 
//			  }
//
//			  @Override
//			  public void onFailure(Throwable e) {
//				  List<Place> places = place.getPlacesByQuery(company_name, 
//							GooglePlaces.DEFAULT_RESULTS);
//				  if(!places.isEmpty()){
//					  data.put("address",places.get(0).getAddress());
//					  data.put("location",String.format("%f, %f",places.get(0).getLatitude(),places.get(0).getLongitude()));
//				  }
//			  }
//		});
		return data;
		
			


	}
}
