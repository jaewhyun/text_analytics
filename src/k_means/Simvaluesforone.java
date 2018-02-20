package k_means;
import java.util.*;
import TextAnalytics.SortbyValue;

class Simvaluesforone {
	private HashMap<String, Double> topones = new HashMap<String, Double>();
	private TreeMap<String, Double> sortedtop = new TreeMap<String, Double>();
	private ArrayList<String> topdiffarticles = new ArrayList<String>();
	private List<Similarityvalues> articlecomparisons = new ArrayList<Similarityvalues>();
	private String topkey;

	public void makecosinesimilarity(List<tfidfvalues> matrix) {
		// go through all the docs and figure out the cosine similarity doc vs doc
		for(int i = 0; i < matrix.size(); i++) {
			tfidfvalues comparethisdoc = matrix.get(i);
			Similarityvalues simvalfordoc = new Similarityvalues();
			simvalfordoc.comparecosinesimilarityvalues(matrix, comparethisdoc);
			articlecomparisons.add(simvalfordoc);
			TreeMap<String, Double> cosinesimilarityvalues = simvalfordoc.returnsortedsimilarityvalues();
			Map.Entry<String, Double> entry1 = cosinesimilarityvalues.firstEntry();
			String topkey = entry1.getKey();
			Double topvalue = entry1.getValue();
			topones.put(topkey, topvalue);
		}

		// sort the top ones
		SortbyValue sortthisplz = new SortbyValue();
		sortedtop = sortthisplz.sortMapByValuestringdoubleplus(topones);

		// get the very first key
		topkey = sortedtop.firstKey();

		// split the string to get the two articles to use as centroids
		List<String> twovectorlocs =  Arrays.asList(topkey.split(","));
		String first = twovectorlocs.get(0);
		String second = twovectorlocs.get(1);
		topdiffarticles.add(first);
		topdiffarticles.add(second);
		String erasedupentry = second+","+first;

		// erase itself and duplicates from the list for the next round to get K - 2 centroids
		// ex. if top key is article05_C1,article03_C2 then remove that one so we don't get that back and also remove article03_C2,article05_C1
		// iterating through the list of comparisons
		erasedupsfornextround(first, second, erasedupentry);
	}

	private void erasedupsfornextround(String first, String second, String erasedupentry) {
		for(int i = 0; i < articlecomparisons.size(); i++) {
			// get articlefolder name
			String articlefolder = articlecomparisons.get(i).returnarticlefolder();
			// if article folders match
			if(articlefolder.equals(first) || articlefolder.equals(second)) {
				// retrieve the cosine similarity values
				TreeMap<String, Double> thistreemap = articlecomparisons.get(i).returnsortedsimilarityvalues();
				// iterate through the similarity values
				Iterator<Map.Entry<String, Double>> iterator = thistreemap.entrySet().iterator();
				Map.Entry<String, Double> entry = iterator.next();
				// if the iterator equals key, remove the entry
				if(entry.getKey().equals(topkey) || entry.getKey().equals(erasedupentry)) {
					// delete 
					iterator.remove();
				}
			}
		}

		sortedtop = new TreeMap<String, Double>();
		topones = new HashMap<String, Double>();
	} 

	public void makeeucdistance(List<tfidfvalues> matrix) {
		// go through all the docs and figure out the euclidean distance doc vs doc
		for(int i = 0; i < matrix.size(); i++) {
			tfidfvalues comparethisdoc = matrix.get(i);
			Similarityvalues simvalfordoc = new Similarityvalues();
			simvalfordoc.compareeucdistancevalues(matrix, comparethisdoc);
			articlecomparisons.add(simvalfordoc);
			TreeMap<String, Double> eucdistancevalues = simvalfordoc.returnsortedsimilarityvalues();
			Map.Entry<String, Double> entry1 = eucdistancevalues.firstEntry();
			String topkey = entry1.getKey();
			Double topvalue = entry1.getValue();
			topones.put(topkey, topvalue);
		}

		// sort the top ones
		SortbyValue sortthisplz = new SortbyValue();
		sortedtop = sortthisplz.sortMapByValuestringdouble(topones);

		// get the very first key
		topkey = sortedtop.firstKey();

		// split the string to get the two articles to use as centroids
		List<String> twovectorlocs =  Arrays.asList(topkey.split(","));
		String first = twovectorlocs.get(0);
		String second = twovectorlocs.get(1);
		topdiffarticles.add(first);
		topdiffarticles.add(second);
		String erasedupentry = second+","+first;

		erasedupsfornextround(first, second, erasedupentry);
	} 

	public ArrayList<String> returntopcosineK(int K, List<tfidfvalues> matrix) {
		makecosinesimilarity(matrix);

		if(K == 2) {
			return topdiffarticles;

		} else { // if K is bigger than 2,
			returnsimilarityvaluesforarticle();

			// sort the values 
			SortbyValue sortthisplz = new SortbyValue();
			TreeMap<String, Double> sortedtop = sortthisplz.sortMapByValuestringdoubleplus(topones);

			kminustwovalues(K, sortedtop);

			return topdiffarticles;
		}
	}

	public ArrayList<String> returnrandomcentroid(int K, List<tfidfvalues> matrix) {
		Random randomizer = new Random();
		Integer maxsize = matrix.size();

		if(maxsize < K) {
			throw new IllegalArgumentException("can't ask for more numbers than are available");
		}

		return randomcentroidgen(K, maxsize, matrix, randomizer);
	}

	public ArrayList<String> returnrandomcentroidselect(int K, Integer total, List<tfidfvalues> matrix) {
		Random randomizer = new Random();

		if(total < K) {
			throw new IllegalArgumentException("can't ask for more numbers than are available");
		}

		return randomcentroidgen(K, total, matrix, randomizer);
	}

	private ArrayList<String> randomcentroidgen(int K, Integer total, List<tfidfvalues> matrix, Random randomizer) {
		Set<Integer> randomnumbers = new LinkedHashSet<Integer>();
		while(randomnumbers.size() < K) {
			Integer nextrandom = randomizer.nextInt(total);
			randomnumbers.add(nextrandom);
		}

		ArrayList<String> randomcentroids = new ArrayList<String>();
		for(Integer number : randomnumbers) {
			String articlename = matrix.get(number).returnarticlename();
			String foldername = matrix.get(number).returnfoldername();
			String articlefolder = articlename + "_" + foldername;
			randomcentroids.add(articlefolder);
		}

		return randomcentroids;
	}



	public ArrayList<String> returntopeucK(int K, List<tfidfvalues> matrix) {
		makeeucdistance(matrix);

		if(K == 2) {
			return topdiffarticles;

		} else { // if K is bigger than 2,
			// for each string
			returnsimilarityvaluesforarticle();

			SortbyValue sortthisplz = new SortbyValue();
			TreeMap<String, Double> sortedtop = sortthisplz.sortMapByValuestringdouble(topones);

			kminustwovalues(K, sortedtop);

			return topdiffarticles;
		}
	}

	private void returnsimilarityvaluesforarticle() {
		for(String onevec : topdiffarticles) {
			for(int i = 0; i < articlecomparisons.size(); i++) {
				String isitthis = articlecomparisons.get(i).returnarticlefolder();
				// get the cosinesimilarityvalues for that articlefoldername
				if(onevec.equals(isitthis)) {
					TreeMap<String, Double> forthisdoc = articlecomparisons.get(i).returnsortedsimilarityvalues();
					for(Map.Entry<String, Double> entry : forthisdoc.entrySet()) {
						// add the value to the array
						topones.put(entry.getKey(), entry.getValue());
					}
				}
			}
		}
	}

	private void kminustwovalues(int K, TreeMap<String, Double> sortedtop) {
		// only get k - 2 values
		TreeMap<String, Double> sortedtopK = sortedtop.entrySet().stream()
			.limit(K - 2)
			.collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

		for(Map.Entry<String, Double> entry : sortedtopK.entrySet()) {
			List<String> twovectorlocs =  Arrays.asList(topkey.split(","));
			String secondpart = twovectorlocs.get(1);
			topdiffarticles.add(secondpart);
		}
	}

	

}