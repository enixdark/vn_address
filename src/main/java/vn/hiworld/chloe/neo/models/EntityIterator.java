package vn.hiworld.chloe.neo.models;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;

import com.google.common.collect.Iterators;

public class EntityIterator<T> implements Iterable<T> {

	private Result results = null;
	private Class<T> type = null;
	
	public EntityIterator(Result results, Class<T> type) {
		this.results = results;
		this.type = type;
	}
	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new Iterator<T>() {
            @Override
            public boolean hasNext() {
                return results.hasNext();
            }

            @Override
            public T next() {
            	if(this.hasNext()){
            		Map<String,Object> re = results.next();
            		List<Object> _values = new ArrayList<Object>();
            		Class[] _class = null;
            		int i = 0;
            		System.out.println(re.keySet().size());
            		try{
	            		for( Entry<String, Object> elem : re.entrySet()){
	            			Node e = (Node) elem.getValue();
	                		_class = new Class[Iterators.size(e.getPropertyKeys().iterator())];
	            			for(String name: e.getPropertyKeys()){
	            				_class[i] = e.getProperty(name).getClass();
	            				_values.add(e.getProperty(name).toString());
	            				i++;
	            			}
	            		}
            		}
	            	catch(Exception e){
	            		System.out.println(re.keySet());
	            	}
            		//                System.out.println(_values);
            		try {
            			return type.getConstructor(_class).newInstance(_values.toArray());
            		} catch (InstantiationException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		} catch (IllegalAccessException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		} catch (IllegalArgumentException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		} catch (InvocationTargetException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		} catch (NoSuchMethodException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		} catch (SecurityException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
            	}
				return null;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
	}
	

}
