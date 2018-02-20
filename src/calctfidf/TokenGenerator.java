package calctfidf;
import java.util.*;
import TextAnalytics.SortbyValue;

public class TokenGenerator {
	private LinkedHashMap<String, LinkedList<String>> tokensforallfolders = new LinkedHashMap<String, LinkedList<String>>();
	private LinkedHashMap<String, Integer> monogramset = new LinkedHashMap<String, Integer>();
	private HashMap<String, Integer> bigramset = new HashMap<String, Integer>();
	private TreeMap<String, Integer> sortedbigramset = new TreeMap<String, Integer>();
	private HashMap<String, Integer> trigramset= new HashMap<String, Integer>();
	private TreeMap<String, Integer> sortedtrigramset = new TreeMap<String, Integer>();
	private HashMap<String, Integer> fourgramset = new HashMap<String, Integer>();
	private TreeMap<String, Integer> sortedfourgramset = new TreeMap<String, Integer>();
	private LinkedHashSet<String> thetokens = new LinkedHashSet<String>();
	
	public void combineallarticles(LinkedList<String> incomingarticle, String foldername) {
		LinkedList<String> addthis = new LinkedList<String>();
		addthis.addAll(incomingarticle);

		if(tokensforallfolders.containsKey(foldername)) {
			for(String currenttoken : incomingarticle) {
				tokensforallfolders.get(foldername).add(currenttoken);
			}
		} else {
			tokensforallfolders.put(foldername, addthis);
		}
	}
	
	public LinkedHashMap<String, LinkedList<String>> returnalltokensforallfolders() {
		return tokensforallfolders;
	}

	public void setfourgram() {
		Iterator<Map.Entry<String, LinkedList<String>>> iterator = tokensforallfolders.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, LinkedList<String>> entry = iterator.next();
			LinkedList<String> wordsinthisfolder = entry.getValue();
			setfourgram2(wordsinthisfolder);
		}
	}
	
	public void settrigram() {
		Iterator<Map.Entry<String, LinkedList<String>>> iterator = tokensforallfolders.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, LinkedList<String>> entry = iterator.next();
			LinkedList<String> wordsinthisfolder = entry.getValue();
			settrigram2(wordsinthisfolder);
		}

	}
	
	public void setbigram() {
		Iterator<Map.Entry<String, LinkedList<String>>> iterator = tokensforallfolders.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, LinkedList<String>> entry = iterator.next();
			LinkedList<String> wordsinthisfolder = entry.getValue();
			setbigram2(wordsinthisfolder);

		}
	}
	
	public void setmonogram() {
		Iterator<Map.Entry<String, LinkedList<String>>> iterator = tokensforallfolders.entrySet().iterator();
		while(iterator.hasNext()) {
			Map.Entry<String, LinkedList<String>> entry = iterator.next();
			LinkedList<String> wordsinthisfolder = entry.getValue();
			setmonogram2(wordsinthisfolder);
		}
	}
	
	public void setmonogram2(LinkedList<String> currentdocwords) {
		String monogram = null;
		Integer sizeofarray = currentdocwords.size();
		
		for(int i = 0; i < sizeofarray; i++) {
			monogram = currentdocwords.get(i);
			if(monogramset.containsKey(monogram)) {
				monogramset.put(monogram,  monogramset.get(monogram) +1);
			} else {
				monogramset.put(monogram, 1);
			}
		}
	}
	

	public void setfourgram2(LinkedList<String> currentdocwords) {
		String firstword = null;
		String secondword = null;
		String thirdword = null;
		String fourthword = null;
		String fourgram = null;
		Integer secondwordtrack = 0;
		Integer thirdwordtrack = 0;
		Integer fourthwordtrack = 0;
		Integer sizeofarray = currentdocwords.size();
		
		for(int i = 0; i < sizeofarray; i++) {
			firstword = currentdocwords.get(i);
			
			secondwordtrack = i + 1;
			
			if(secondwordtrack < sizeofarray) {
				secondword = currentdocwords.get(i+1);
			}

			thirdwordtrack = i + 2;
			if(thirdwordtrack < sizeofarray) {
				thirdword = currentdocwords.get(i+2);
			}

			fourthwordtrack = i + 3;
			if(fourthwordtrack < sizeofarray) {
				fourthword = currentdocwords.get(i+3);
				fourgram = firstword + " " + secondword + " " + thirdword + " " + fourthword;
				if(fourgramset.containsKey(fourgram)) {
					fourgramset.put(fourgram, fourgramset.get(fourgram) + 1);
				} else {
					fourgramset.put(fourgram, 1);
				}
			}
		}
	}

	public void settrigram2(LinkedList<String> currentdocwords) {
		String firstword = null;
		String secondword = null;
		String thirdword = null;
		String trigram = null;
		Integer secondwordtrack = 0;
		Integer thirdwordtrack = 0;
		Integer sizeofarray = currentdocwords.size();
		
		for(int i = 0; i < sizeofarray; i++) {
			firstword = currentdocwords.get(i);
			
			secondwordtrack = i + 1;
			
			if(secondwordtrack < sizeofarray) {
				secondword = currentdocwords.get(i+1);
			}
			
			thirdwordtrack = i + 2;
			if(thirdwordtrack < sizeofarray) {
				thirdword = currentdocwords.get(i+2);
				trigram = firstword + " " + secondword + " " + thirdword;
				if(trigramset.containsKey(trigram)) {
					trigramset.put(trigram, trigramset.get(trigram) + 1);
				} else {
					trigramset.put(trigram, 1);
				}
			}
		}
	}
	
	public void setbigram2(LinkedList<String> currentdocwords) {
		String firstword = null;
		String secondword = null;
		String bigram = null;
		Integer secondwordtrack = 0;
		Integer sizeofarray = currentdocwords.size();
		
		for(int i = 0; i < sizeofarray; i++) {
			firstword = currentdocwords.get(i);
			
			secondwordtrack = i + 1;
			
			if(secondwordtrack < sizeofarray) {
				secondword = currentdocwords.get(i+1);
				bigram = firstword + " " + secondword;
				if(bigramset.containsKey(bigram)) {
					bigramset.put(bigram,  bigramset.get(bigram) + 1);
				} else {
					bigramset.put(bigram,  1);
				}
			}
		}
	}
	
	public void cleanbigramset() {
		Iterator<Map.Entry<String, Integer>> iterator2 = bigramset.entrySet().iterator();
		while(iterator2.hasNext()) {
			Map.Entry<String, Integer> ridbi = iterator2.next();
			if(4 > ridbi.getValue()) {
				iterator2.remove();
			}
		}
	}
	
	
	public void cleantrigramset() {
		Iterator<Map.Entry<String, Integer>> iterator3 = trigramset.entrySet().iterator();
		while(iterator3.hasNext()) {
			Map.Entry<String, Integer> ridtri = iterator3.next();
			if(3 > ridtri.getValue()) {
				iterator3.remove();
			}
		}
	}

	public void cleanfourgramset() {
		Iterator<Map.Entry<String, Integer>> iterator4 = fourgramset.entrySet().iterator();
		while(iterator4.hasNext()) {
			Map.Entry<String, Integer> ridfour = iterator4.next();
			if(3 > ridfour.getValue()) {
				iterator4.remove();
			}
		}
	}

	public void cleanmonogramset() {
		Iterator<Map.Entry<String, Integer>> iterator1 = monogramset.entrySet().iterator();
		while(iterator1.hasNext()) {
			Map.Entry<String, Integer> ridmono = iterator1.next();
			if(4 > ridmono.getValue()) {
				iterator1.remove();
			}
		}
	}
	
	public void switchfourforone(DocTokens[] alldocsandtokens) {
		String firstword = null;
		String secondword = null;
		String thirdword = null;
		String fourthword = null;
		String fourgram = null;
		Integer secondwordtrack = 0;
		Integer thirdwordtrack = 0;
		Integer fourthwordtrack = 0;

		SortbyValue sortthisplz = new SortbyValue();
		sortedfourgramset = sortthisplz.sortMapByValuestringint(fourgramset);
		for(Map.Entry<String, Integer> entry : sortedfourgramset.entrySet()){
			String matchthistoken = entry.getKey();

			for(int i = 0; i < alldocsandtokens.length; i++) {
				LinkedList<String>wordsinthisdoc = alldocsandtokens[i].returnlistoftokens();
				for(int j = 0; j < wordsinthisdoc.size(); j++) {
					firstword = wordsinthisdoc.get(j);
				
					secondwordtrack = j + 1;
					if(secondwordtrack < wordsinthisdoc.size()) {
						secondword = wordsinthisdoc.get(secondwordtrack);
					}

					thirdwordtrack = j + 2;
					if(thirdwordtrack < wordsinthisdoc.size()) {
						thirdword = wordsinthisdoc.get(thirdwordtrack);
					}

					fourthwordtrack = j + 3;
					if(fourthwordtrack < wordsinthisdoc.size()) {
						fourthword = wordsinthisdoc.get(fourthwordtrack);
						fourgram = firstword + " " + secondword + " " + thirdword + " " + fourthword;

						if(fourgram.equals(matchthistoken)) {
							Integer temp = j;
							wordsinthisdoc.remove(j+3);
							wordsinthisdoc.remove(j+2);
							wordsinthisdoc.remove(j+1);						
							wordsinthisdoc.remove(j);
							wordsinthisdoc.add(temp, fourgram);
						}
					}
				}	
			}
		}
	}

	public void switchtriforone(DocTokens[] alldocsandtokens) {
		String firstword = null;
		String secondword = null;
		String thirdword = null;
		String trigram = null;
		Integer secondwordtrack = 0;
		Integer thirdwordtrack = 0;
		SortbyValue sortthisplz = new SortbyValue();
		sortedtrigramset = sortthisplz.sortMapByValuestringint(trigramset);

		for(Map.Entry<String, Integer> entry : sortedtrigramset.entrySet()){
			String matchthistoken = entry.getKey();
			for(int i = 0; i < alldocsandtokens.length; i++) {
				LinkedList<String>wordsinthisdoc = alldocsandtokens[i].returnlistoftokens();
				for(int j = 0; j < wordsinthisdoc.size(); j++) {
					firstword = wordsinthisdoc.get(j);
				
					secondwordtrack = j + 1;
					if(secondwordtrack < wordsinthisdoc.size()) {
						secondword = wordsinthisdoc.get(secondwordtrack);
					}

					thirdwordtrack = j + 2;
					if(thirdwordtrack < wordsinthisdoc.size()) {
						thirdword = wordsinthisdoc.get(thirdwordtrack);
						trigram = firstword + " " + secondword + " " + thirdword;
						
						if(trigram.equals(matchthistoken)) {
							Integer temp = j;
							wordsinthisdoc.remove(j+2);
							wordsinthisdoc.remove(j+1);
							wordsinthisdoc.remove(j);
							wordsinthisdoc.add(temp, trigram);
						}
					}
				}	
			}
		}
	}

	public void switchbiforone(DocTokens[] alldocsandtokens) {
		String firstword = null;
		String secondword = null;
		String bigram = null;
		Integer secondwordtrack = 0;

		SortbyValue sortthisplz = new SortbyValue();
		sortedbigramset = sortthisplz.sortMapByValuestringint(bigramset);
		for(Map.Entry<String, Integer> entry : sortedbigramset.entrySet()){
			String matchthistoken = entry.getKey(); 

			for(int i = 0; i < alldocsandtokens.length; i++) {
				LinkedList<String>wordsinthisdoc = alldocsandtokens[i].returnlistoftokens();
				for(int j = 0; j < wordsinthisdoc.size(); j++) {
					firstword = wordsinthisdoc.get(j);
				
					secondwordtrack = j + 1;
					if(secondwordtrack < wordsinthisdoc.size()) {
						secondword = wordsinthisdoc.get(secondwordtrack);
						bigram = firstword + " " + secondword;
						if(bigram.equals(matchthistoken)) {
							Integer temp = j;
							wordsinthisdoc.remove(j+1);
							wordsinthisdoc.remove(j);
							wordsinthisdoc.add(temp, bigram);
						}
					}
				}	
			}
		}
	}

	public void tokenswitch(DocTokens[] alldocsandtokens) {
		setfourgram();
		cleanfourgramset();
		switchfourforone(alldocsandtokens);
		settrigram();
		cleantrigramset();
		switchtriforone(alldocsandtokens);
		setbigram();
		cleanbigramset();
		switchbiforone(alldocsandtokens);
		setmonogram();
		cleanmonogramset();
	}
	
	public LinkedHashSet<String> combinealltokens() {
		for(Map.Entry<String, Integer> pair1 : monogramset.entrySet()) {
			thetokens.add(pair1.getKey());
		}

		for(Map.Entry<String, Integer> pair2 : bigramset.entrySet()) {
			thetokens.add(pair2.getKey());
		}

		for(Map.Entry<String, Integer> pair3 : trigramset.entrySet()) {
			thetokens.add(pair3.getKey());
		}

		for(Map.Entry<String, Integer> pair4 : fourgramset.entrySet()) {
			thetokens.add(pair4.getKey());
		}

		return thetokens;
	}

	public ArrayList<String> returnmonogramset() {
		ArrayList<String> monograms = new ArrayList<String>();
		for(Map.Entry<String, Integer> pair : monogramset.entrySet()) {
			monograms.add(pair.getKey());
		}
		return monograms;
	}

	public ArrayList<String> returnbigramset(){
		ArrayList<String> bigrams = new ArrayList<String>();
		for(Map.Entry<String, Integer> pair : sortedbigramset.entrySet()) {
			bigrams.add(pair.getKey());
		}
		return bigrams;
	}

	public ArrayList<String> returntrigramset() {
		ArrayList<String> trigrams = new ArrayList<String>();
		for(Map.Entry<String, Integer> pair : sortedtrigramset.entrySet()) {
			trigrams.add(pair.getKey());
		}
		return trigrams;
	}

	public ArrayList<String> returnfourgramset() {
		ArrayList<String> fourgrams = new ArrayList<String>();
		for(Map.Entry<String, Integer> pair : sortedfourgramset.entrySet()) {
			fourgrams.add(pair.getKey());
		}
		return fourgrams;
	}
}
