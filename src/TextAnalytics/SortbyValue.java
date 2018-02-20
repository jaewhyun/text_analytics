package TextAnalytics;
import java.util.*;

// from : https://www.programcreek.com/2013/03/java-sort-map-by-value/

public class SortbyValue {
	
	class ValueComparatorintdouble implements Comparator<Integer>{
		 
		HashMap<Integer, Double> map = new HashMap<Integer, Double>();
	 
		public ValueComparatorintdouble(HashMap<Integer, Double> map){
			this.map.putAll(map);
		}
	 
		@Override
		public int compare(Integer s1, Integer s2) {
			int compare = map.get(s1).compareTo(map.get(s2));
			if (compare == 0) {
				compare = s1.compareTo(s2);
				if(compare > 0) {
					return -1;
				}
			}
			
			if(compare > 0) {
				return -1;
			} else if(compare < 0) {
				return 1;
			} else { // if map.get(s1).equals(map.get(s2))
				return 0;
			}
		}
	}

	public TreeMap<Integer, Double> sortMapByValueintdouble(HashMap<Integer, Double> map){
		Comparator<Integer> comparator = new ValueComparatorintdouble(map);
		//TreeMap is a map sorted by its keys. 
		//The comparator is used to sort the TreeMap by keys. 
		TreeMap<Integer, Double> result = new TreeMap<Integer, Double>(comparator);
		result.putAll(map);
		return result;
	}
	
	class ValueComparatorintintplus implements Comparator<Integer> {
		HashMap<Integer, Double> map = new HashMap<Integer, Double>();
		
		public ValueComparatorintintplus(HashMap<Integer, Double> map) {
			this.map.putAll(map);
		}
		
		@Override
		public int compare(Integer s1, Integer s2) {
			int compare = map.get(s1).compareTo(map.get(s2));
			if (compare == 0) {
				compare = s1.compareTo(s2);
				if(compare > 0) {
					return -1;
				}
			}
			
			if(compare > 0) {
				return 1;
			} else if(compare < 0) {
				return -1;
			} else { // if map.get(s1).equals(map.get(s2))
				return 0;
			}
		}
	}
	
	public TreeMap<Integer, Double> sortMapByValueintintplus(HashMap<Integer, Double> map){
		Comparator<Integer> comparator = new ValueComparatorintintplus(map);
		//TreeMap is a map sorted by its keys. 
		//The comparator is used to sort the TreeMap by keys. 
		TreeMap<Integer, Double> result = new TreeMap<Integer, Double>(comparator);
		result.putAll(map);
		return result;
	}

	class ValueComparatorintintminus implements Comparator<Integer>{
		 
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	 
		public ValueComparatorintintminus(HashMap<Integer, Integer> map){
			this.map.putAll(map);
		}
	 
		@Override
		public int compare(Integer s1, Integer s2) {
			int compare = map.get(s1).compareTo(map.get(s2));
			if (compare == 0) {
				compare = s1.compareTo(s2);
			}
			
			if(compare > 0) {
				return -1;
			} else if(compare < 0) {
				return 1;
			} else { // if map.get(s1).equals(map.get(s2))
				return 0;
			}
		}
	}
	
	public TreeMap<Integer, Integer> sortMapByValueintintminus(HashMap<Integer, Integer> map){
		Comparator<Integer> comparator = new ValueComparatorintintminus(map);
		//TreeMap is a map sorted by its keys. 
		//The comparator is used to sort the TreeMap by keys. 
		TreeMap<Integer, Integer> result = new TreeMap<Integer, Integer>(comparator);
		result.putAll(map);
		return result;
	}
	
	class ValueComparatorstringdouble implements Comparator<String>{
 
		HashMap<String, Double> map = new HashMap<String, Double>();
	 
		public ValueComparatorstringdouble(HashMap<String, Double> map){
			this.map.putAll(map);
		}
	 
		@Override
		public int compare(String s1, String s2) {
			int compare = map.get(s1).compareTo(map.get(s2));
			if (compare == 0) {
				compare = s1.compareTo(s2);
			}
			
			if(compare > 0) {
				return -1;
			} else if(compare < 0) {
				return 1;
			} else { // if map.get(s1).equals(map.get(s2))
				return 0;
			}
		}
	}

	public TreeMap<String, Double> sortMapByValuestringdouble(HashMap<String, Double> map){
		Comparator<String> comparator = new ValueComparatorstringdouble(map);
		//TreeMap is a map sorted by its keys. 
		//The comparator is used to sort the TreeMap by keys. 
		TreeMap<String, Double> result = new TreeMap<String, Double>(comparator);
		result.putAll(map);
		return result;
	}
	
	class ValueComparatorstringint implements Comparator<String>{
		 
		HashMap<String, Integer> map = new HashMap<String, Integer>();
	 
		public ValueComparatorstringint(HashMap<String, Integer> map){
			this.map.putAll(map);
		}
	 
		@Override
		public int compare(String s1, String s2) {
			int compare = map.get(s1).compareTo(map.get(s2));
			if (compare == 0) {
				compare = s1.compareTo(s2);
			}
			
			if(compare > 0) {
				return -1;
			} else if(compare < 0) {
				return 1;
			} else { // if map.get(s1).equals(map.get(s2))
				return 0;
			}
		}
	}

	public TreeMap<String, Integer> sortMapByValuestringint(HashMap<String, Integer> map){
		Comparator<String> comparator = new ValueComparatorstringint(map);
		//TreeMap is a map sorted by its keys. 
		//The comparator is used to sort the TreeMap by keys. 
		TreeMap<String, Integer> result = new TreeMap<String, Integer>(comparator);
		result.putAll(map);
		return result;
	}
	
	class ValueComparatorstringdoubleplus implements Comparator<String>{
		 
		HashMap<String, Double> map = new HashMap<String, Double>();
	 
		public ValueComparatorstringdoubleplus(HashMap<String, Double> map){
			this.map.putAll(map);
		}
	 
		@Override
		public int compare(String s1, String s2) {
			int compare = map.get(s1).compareTo(map.get(s2));
			if (compare == 0) {
				compare = s1.compareTo(s2);
			}
			
			if(compare > 0) {
				return 1;
			} else if(compare < 0) {
				return -1;
			} else { // if map.get(s1).equals(map.get(s2))
				return 0;
			}
		}
	}

	public TreeMap<String, Double> sortMapByValuestringdoubleplus(HashMap<String, Double> map){
		Comparator<String> comparator = new ValueComparatorstringdoubleplus(map);
		//TreeMap is a map sorted by its keys. 
		//The comparator is used to sort the TreeMap by keys. 
		TreeMap<String, Double> result = new TreeMap<String, Double>(comparator);
		result.putAll(map);
		return result;
	}
}

	