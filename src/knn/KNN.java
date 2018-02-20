package knn;
import java.util.*;
import TextAnalytics.SortbyValue;

// class for knn algorithm
class KNN {
	private String firstkeytoreturn;

	// returns Knearestneighbor ourcome algorithm - returns majority label
	// takes in training data and a document vector with a K value
	public String returnKnearestlabel(List<tfidfvalues> trainmatrix, ArrayList<Double> frequencyoftokensmatrix, Integer K) {
		CosineSimilarity cosinesimilaritymethod = new CosineSimilarity();
		// calculates cosine similarity and returns a list of K sorted cosine similarity values 
		TreeMap<String, Double> similarityvalues2 = cosinesimilaritymethod.setcosinesimilarityvalues(trainmatrix, frequencyoftokensmatrix);
		TreeMap<String, Double> topnumberofmatrices2 = similarityvalues2.entrySet().stream()
			.limit(K)
			.collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

		HashMap<String, Integer> whichlabel = new HashMap<String, Integer>();
		HashMap<String, Double> breakties = new HashMap<String, Double>();
		
		// process the return treemap containing K nearest neighbours
		for(Map.Entry<String, Double> entry : topnumberofmatrices2.entrySet()) {
			String key = entry.getKey();
			String[] splittingfolder = key.split("__");
			String labelnametho = splittingfolder[1];
			// input into hashmap the returned labels
			if(whichlabel.containsKey(labelnametho)) {
				whichlabel.put(labelnametho, whichlabel.get(labelnametho) + 1);
				Iterator<Map.Entry<String, Double>> iterator1 = breakties.entrySet().iterator();
				while(iterator1.hasNext() ) {
					Map.Entry<String, Double> pair = iterator1.next();
					String labelnameinbreaktie = pair.getKey();
					// just in case we need to break ties, return cosine similarity values that are added up for each label
					Double cosinesimbreaktie = pair.getValue() + entry.getValue();
					if(labelnameinbreaktie.equals(labelnametho)) {
						breakties.put(labelnameinbreaktie, cosinesimbreaktie);
					}
				}
			} else {
				whichlabel.put(labelnametho, 1);
				breakties.put(labelnametho, entry.getValue());
			}
		}
		
		TreeMap<String, Integer> sortedlabels = new TreeMap<String, Integer>();
		
		SortbyValue sortthisplz = new SortbyValue();
		sortedlabels = sortthisplz.sortMapByValuestringint(whichlabel);
		
		// if we have a clear winner with no ties, 
		String firstkeytoreturn2 = sortedlabels.firstKey();
		Integer firstkeyvalue = sortedlabels.get(firstkeytoreturn2);
		
		
		// we got more labels back than the one that matches
		if(sortedlabels.size() > 1) {
		Iterator<Map.Entry<String, Integer>> iteratorforlabel = sortedlabels.entrySet().iterator();
			while(iteratorforlabel.hasNext()) {
				Map.Entry<String, Integer> pair = iteratorforlabel.next();
			
				Integer value = pair.getValue();
				// if we check the values of the ones in the returned list that matches with the value of the first in the list, that means that there is a match
				if(firstkeyvalue==value) {
				//if it matches and the key is also different, then there is a tie
					if(!firstkeytoreturn2.equals(pair.getKey())) {
						// sort break ties in order to get the top cosine value
						TreeMap<String, Double> breakties2 = new TreeMap<String, Double>();
						breakties2 = sortthisplz.sortMapByValuestringdouble(breakties);
						
						//get the value from breakties with the higher cosine similarity
						String firstkey2 = breakties2.firstKey();
						
						//set first key to breaktie key
						firstkeytoreturn2 = firstkey2;
						
					}
				} 
			}
		}
		
		return firstkeytoreturn2;
	}
	
	public String returnfirstkey() {
		return firstkeytoreturn;
	}
}
