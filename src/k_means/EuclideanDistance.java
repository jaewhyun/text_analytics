package k_means;

import java.util.*;

import TextAnalytics.SortbyValue;

class EuclideanDistance {

	private HashMap<String, Double> euc_dist_similarityvalues = new HashMap<String, Double>();
	private TreeMap<String, Double> sorted_similarity_values = new TreeMap<String, Double>();

	public Double EuclideanDistanceCalc(ArrayList<Double> vectortocompare, ArrayList<Double> centroid) {
		Integer size = vectortocompare.size();
		Double totalundersquare = 0.0;
		Double euclideandistance = 0.0;
		for(int i = 0; i < size; i++) {
			Double currentvalueforvector = vectortocompare.get(i);
			Double currentvalueforcentroid = centroid.get(i);
			Double subtractedvalue = currentvalueforvector - currentvalueforcentroid;
			Double powerof = Math.pow(subtractedvalue, 2);
			totalundersquare = totalundersquare + powerof;
		}

		euclideandistance = Math.sqrt(totalundersquare);

		return euclideandistance;
	}

	public TreeMap<String, Double> seteucsimilarityvalues(List<tfidfvalues> matrix, ArrayList<Double> currentvector) {
		for(int i = 0; i < matrix.size(); i++) {
			String compvectorarticlename = matrix.get(i).returnarticlename();
			String compvectorfoldername = matrix.get(i).returnfoldername();
			ArrayList<Double> compvector = matrix.get(i).returntfidfvaluesforrow();

			Double euclideandistance = EuclideanDistanceCalc(compvector, currentvector);

			String comparingwhat = compvectorarticlename+"_"+compvectorfoldername;

			euc_dist_similarityvalues.put(comparingwhat, euclideandistance);
		}

		Iterator<Map.Entry<String, Double>> iterator = euc_dist_similarityvalues.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, Double> entry = iterator.next();
			if(entry.getValue() == 0) {
				iterator.remove();
			}
		}

		SortbyValue sortthisplz = new SortbyValue();
		sorted_similarity_values = sortthisplz.sortMapByValuestringdoubleplus(euc_dist_similarityvalues);

		return sorted_similarity_values;
	}


}