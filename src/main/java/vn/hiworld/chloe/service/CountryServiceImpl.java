//package vn.hiworld.chloe.service;
//
//import vn.hiworld.chloe.neo.models.Country;
//import java.util.Map;
//import java.util.Collections;
//
////Service("CountryService")
//public class CountryServiceImpl extends GenericService<Country> {
//
//    public Iterable<Map<String,Object>> getCountryBy() {
//        String query =
//            "MATCH (s:COUNTRIES) return collection(s)";
//        return Neo4jSessionFactory.getInstance().getNeo4jSession()
//                .query(query, Collections.EMPTY_MAP);
//    }
//
//    @Override
//    public Class<Country> getEntityType() {
//        return Country.class;
//    }
//
//}
