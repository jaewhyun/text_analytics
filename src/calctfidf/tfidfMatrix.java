package calctfidf;
import java.util.*;


class tfidfMatrix {
	private HashMap<String, Double> frequencyoftokens = new HashMap<String, Double>();
	private ArrayList<Double> frequencyoftokensmatrix = new ArrayList<Double>();
	private ArrayList<Double> normalizedtfidfvector = new ArrayList<Double>();
	private LinkedHashMap<String, Integer> numberofdocsfortoken = new LinkedHashMap<String, Integer>();
	private LinkedHashMap<String, Double> tokenidfvalues = new LinkedHashMap<String, Double>();
	private String foldername;
	private String articlename;

	// public void normalizethis() {
		
	// }
	
	public LinkedHashMap<String, Integer> howmanydocshavethistoken(Integer numberoffiles, LinkedHashSet<String> alltokens, DocTokens[] alldocsandtokens) {
		Integer arraysize = alldocsandtokens.length;
		// for this token from the total list of tokens
		for(String token : alltokens) {
			
			for(Integer i = 0; i < arraysize; i++) {
				Integer totalcount = 0;
				// from this one article
				LinkedList<String> listoftokensforthisarticle = alldocsandtokens[i].returnlistoftokens();
				
				// going through the words one by one in the article
				for(String doesthetokenappear : listoftokensforthisarticle) {
					// does the article match 
					if(doesthetokenappear.equals(token)) {
						totalcount++;
					}
				}
				// yes the words appeared in this article
				if(totalcount > 0) {
					// great update linkedhashmap
					if(numberofdocsfortoken.containsKey(token)) {
						numberofdocsfortoken.put(token, numberofdocsfortoken.get(token) + 1);
					} else {
						numberofdocsfortoken.put(token, 1);
					}
				}
			}
		}
		
		return numberofdocsfortoken;
	}

	public LinkedHashSet<String> cleantokenlist(LinkedHashSet<String> thetokens, LinkedHashMap<String, Integer> numberofdocsfortoken2) {
		Iterator<String> iterator = thetokens.iterator();
		while(iterator.hasNext()) {
			String token = iterator.next();
			if(!numberofdocsfortoken2.containsKey(token)) {
				iterator.remove();
			}
		}

		return thetokens;
	}

	
	public void calctfidf(String foldername2, String articlename2, LinkedHashMap<String, Integer> numberofdocsfortoken2, Integer numberoffiles, List<String> alltokens, LinkedList<String> listoftokensforthisarticle) {
		foldername = foldername2;
		articlename = articlename2;

		for(String token : alltokens) {
			Integer totalcountforthisdoc = 0;
			
			Integer totaldocfreq = numberofdocsfortoken2.get(token);
			for(String doesthetokenappear : listoftokensforthisarticle) {
				// does the article match 
				if(token.equals(doesthetokenappear)) {
					totalcountforthisdoc++;
				}
			}

			if(totaldocfreq == 0) {
				tokenidfvalues.put(token, 0.0);
				frequencyoftokens.put(token, 0.0);
				frequencyoftokensmatrix.add(0.0);
			} else {
				Double idf = Math.log10(numberoffiles/totaldocfreq);
				tokenidfvalues.put(token, idf);
				Double tfidf = totalcountforthisdoc * idf;
				frequencyoftokens.put(token, tfidf);
				frequencyoftokensmatrix.add(tfidf);	
			}
			
		}

		Double totalsum = 0.0;
		Double vectorlength = 0.0;

		for(Double thistfidf : frequencyoftokensmatrix) {
			totalsum = totalsum + Math.pow(thistfidf, 2);
		}

		vectorlength = Math.sqrt(totalsum);

		for(Double thistfidf : frequencyoftokensmatrix) {
			thistfidf = thistfidf/vectorlength;
			normalizedtfidfvector.add(thistfidf);
		}
	}

	public ArrayList<Double> returnnormalizedtfidfvector() {
		return normalizedtfidfvector;
	}

	public LinkedHashMap<String, Double> returntokenidfvalues() {
		return tokenidfvalues;
	}
	
	public HashMap<String, Double> returntfidfmatrix() {
		return frequencyoftokens;
	}

	public ArrayList<Double> returnfrequencyoftokensmatrix() {
		return frequencyoftokensmatrix;
	}

	public String returnarticlename() {
		return articlename;
	}

	public String returnfoldername() {
		return foldername;
	}
	
	public void updatetokensforthisfolder(HashMap<String, Double> toptokensforfolder, HashMap<String, Double> frequencyoftokens2) {
		Iterator<Map.Entry<String, Double>> iterator = frequencyoftokens2.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, Double> tokentfidfpair = iterator.next();

			String token = tokentfidfpair.getKey();
			Double tfidfvalue = tokentfidfpair.getValue();

			if(toptokensforfolder.containsKey(token)) {
				toptokensforfolder.put(token, toptokensforfolder.get(token) + tfidfvalue);
			} else {
				toptokensforfolder.put(token, tfidfvalue);
			}
		}
	}
	
}
