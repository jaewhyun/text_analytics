package k_means;
import java.util.*;

import TextAnalytics.SortbyValue;

class Similarityvalues {
	private String articlefolder;
	private HashMap<String, Double> similarityvalues = new HashMap<String, Double>();
	private TreeMap<String, Double> sortedsimilarityvalues = new TreeMap<String, Double>();


	public void comparecosinesimilarityvalues(List<tfidfvalues> matrix, tfidfvalues currentvector) {
		String articlename = currentvector.returnarticlename();
		String foldername = currentvector.returnfoldername();
		ArrayList<Double> tfidfval = currentvector.returntfidfvaluesforrow();
		articlefolder = articlename+ "_" + foldername;
		CosineSimilarity calculator = new CosineSimilarity();
		for(int i = 0; i < matrix.size(); i++) {
			String compvectorarticlename = matrix.get(i).returnarticlename();
			String compvectorfoldername = matrix.get(i).returnfoldername();
			ArrayList<Double> compvector = matrix.get(i).returntfidfvaluesforrow();

			Double cosinesimilarity = calculator.CosineSimilarityCalc(tfidfval, compvector);

			String comparingwhat = compvectorarticlename + "_" + compvectorfoldername;

			String comparingthese = articlefolder + "," + comparingwhat;
			similarityvalues.put(comparingthese, cosinesimilarity);
		}

		Iterator<Map.Entry<String, Double>> iterator = similarityvalues.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, Double> entry = iterator.next();
			if((entry.getValue() > 0.989 && entry.getValue() <= 1) || entry.getValue() > 1) {
				iterator.remove();
			}
		}

		SortbyValue sortthisplz = new SortbyValue();
		sortedsimilarityvalues = sortthisplz.sortMapByValuestringdouble(similarityvalues);
	}

	public void compareeucdistancevalues(List<tfidfvalues> matrix, tfidfvalues currentvector) {
		String articlename = currentvector.returnarticlename();
		String foldername = currentvector.returnfoldername();
		ArrayList<Double> tfidfval = currentvector.returntfidfvaluesforrow();
		articlefolder = articlename+ "_" + foldername;
		EuclideanDistance calculator = new EuclideanDistance();
		for(int i = 0; i < matrix.size(); i++) {
			String compvectorarticlename = matrix.get(i).returnarticlename();
			String compvectorfoldername = matrix.get(i).returnfoldername();
			ArrayList<Double> compvector = matrix.get(i).returntfidfvaluesforrow();

			Double eucdistance = calculator.EuclideanDistanceCalc(tfidfval, compvector);

			String comparingwhat = compvectorarticlename + "_" + compvectorfoldername;

			String comparingthese = articlefolder + "," + comparingwhat;
			similarityvalues.put(comparingthese, eucdistance);
		}

		Iterator<Map.Entry<String, Double>> iterator = similarityvalues.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, Double> entry = iterator.next();
			if(entry.getValue() == 0.0) {
				iterator.remove();
			}
		}

		SortbyValue sortthisplz = new SortbyValue();
		sortedsimilarityvalues = sortthisplz.sortMapByValuestringdouble(similarityvalues);
	}

	public String returnarticlefolder() {
		return articlefolder;
	}

	public TreeMap<String, Double> returnsortedsimilarityvalues() {
		return sortedsimilarityvalues;
	}
}