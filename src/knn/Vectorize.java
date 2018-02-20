package knn;
import java.util.*;

// class to change the new articles into vectors
public class Vectorize {
	
	public tfidfvalues calctfidf(LinkedHashMap<String, Double> oldtokenidfvalues, String foldername2, String articlename2, String labelname2, LinkedList<String> listoftokensforthisarticle) {
		String foldername = foldername2;
		String articlename = articlename2;
		
		System.out.println("the article_folder is: " + articlename + "_"+foldername);

		LinkedHashMap<String, Double> newtokentfidfvalues = new LinkedHashMap<String, Double>();
		ArrayList<Double> justtfidfvalues = new ArrayList<Double>();
		ArrayList<Double> normalizedtfidfvalues = new ArrayList<Double>();
		
		for(Map.Entry<String, Double> entry : oldtokenidfvalues.entrySet()) {
			String token = entry.getKey();
			Double idfvalue = entry.getValue();
			
			Integer totalcountforthisdoc = 0;
			for(String doesthetokenappear : listoftokensforthisarticle) {
				if(token.equals(doesthetokenappear)) {
					totalcountforthisdoc++;
				}
			}
			
			Double tfidf = totalcountforthisdoc * idfvalue;
			
			newtokentfidfvalues.put(token, tfidf);
			justtfidfvalues.add(tfidf);
		}
		
		Double totalsum = 0.0;
		Double vectorlength = 0.0;

		for(Double thistfidf : justtfidfvalues) {
			totalsum = totalsum + Math.pow(thistfidf, 2);
		}

		vectorlength = Math.sqrt(totalsum);

		for(Double thistfidf : justtfidfvalues) {
			thistfidf = thistfidf/vectorlength;
			normalizedtfidfvalues.add(thistfidf);
		}
		
		tfidfvalues tfidfvaluesforthisarticle = new tfidfvalues(articlename, foldername, labelname2, newtokentfidfvalues, normalizedtfidfvalues);
		
		return tfidfvaluesforthisarticle;
	}
	
}
