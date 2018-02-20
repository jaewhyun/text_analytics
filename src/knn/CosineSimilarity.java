package knn;
import TextAnalytics.SortbyValue;

import java.util.*;
// class to calculate cosine similarity 
class CosineSimilarity {
	ArrayList<String> labels = new ArrayList<String>();
	HashMap<String, Double> cosinesimilarityvalues = new HashMap<String, Double>();
	TreeMap<String, Double> sortedcosinesimilarityvalues = new TreeMap<String, Double>();

	public Double CosineSimilarityCalc(ArrayList<Double> vectortocompare1, ArrayList<Double> vectortocompare2) {
		Integer size = vectortocompare1.size();
		Double cosinesimilarity = 0.0;
		Double totalproduct = 0.0;
		Double totalundersquarevec1 = 0.0;
		Double totalundersquarevec2 = 0.0;
		Double normalizedforvector1 = 0.0;
		Double normalizedforvector2 = 0.0;

		for(int i = 0; i < size; i++) {
			Double currentvalueforvector1 = vectortocompare1.get(i);
			Double currentvalueforvector2 = vectortocompare2.get(i);
			Double product = currentvalueforvector1 * currentvalueforvector2;
			totalproduct = totalproduct + product;

			totalundersquarevec1 = totalundersquarevec1 + (currentvalueforvector1 * currentvalueforvector1);
			totalundersquarevec2 = totalundersquarevec2 + (currentvalueforvector2 * currentvalueforvector2);
		}

		normalizedforvector1 = Math.sqrt(totalundersquarevec1);
		normalizedforvector2 = Math.sqrt(totalundersquarevec2);

		cosinesimilarity = totalproduct / (normalizedforvector2 * normalizedforvector1);

		return cosinesimilarity;
	}

	public TreeMap<String, Double> setcosinesimilarityvalues(List<tfidfvalues> matrix, ArrayList<Double> currentvector) {

		for(int i = 0; i < matrix.size(); i++) {
			String compvectorarticlename = matrix.get(i).returnarticlename();
			String compvectorfoldername = matrix.get(i).returnfoldername();
			String compvectorlabelname = matrix.get(i).returnlabelname();

			ArrayList<Double> compvector = matrix.get(i).returntfidfvaluesforrow();

			Double cosinesimilarity = CosineSimilarityCalc(currentvector, compvector);

			String comparingwhat = compvectorarticlename + "_" + compvectorfoldername + "__" + compvectorlabelname;

			cosinesimilarityvalues.put(comparingwhat, cosinesimilarity);
		}

		Iterator<Map.Entry<String, Double>> iterator = cosinesimilarityvalues.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, Double> entry = iterator.next();
			if((entry.getValue() > 0.998 && entry.getValue() <= 1) || entry.getValue() > 1 ) {
				iterator.remove();
			}
		}

		SortbyValue sortthisplz = new SortbyValue();

		sortedcosinesimilarityvalues = sortthisplz.sortMapByValuestringdouble(cosinesimilarityvalues);

		return sortedcosinesimilarityvalues;
	}
}
