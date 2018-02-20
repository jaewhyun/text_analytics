package knn;
import java.util.*;

public class TheseTokens {
	private ArrayList<String> bigramset = new ArrayList<String>();
	private ArrayList<String> trigramset = new ArrayList<String>();
	private ArrayList<String> fourgramset = new ArrayList<String>();

	public void setmonogram(ArrayList<String> monogram) {
	}
	
	public void setbigram(ArrayList<String> bigram) {
		bigramset = bigram;
	}
	
	public void settrigram(ArrayList<String> trigram) {
		trigramset = trigram;
	}
	
	public void setfourgram(ArrayList<String> fourgram) {
		fourgramset = fourgram;
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

		for(String matchthistoken : fourgramset){
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

		for(String matchthistoken : trigramset){
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

		for(String matchthistoken : bigramset){
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
		switchfourforone(alldocsandtokens);
		switchtriforone(alldocsandtokens);
		switchbiforone(alldocsandtokens);
	}

}
