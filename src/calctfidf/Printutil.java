package calctfidf;

import java.io.*;
import java.util.*;

import TextAnalytics.SortbyValue;

class Printutil {
	public void printngrams(ArrayList<String> monogram, ArrayList<String> bigram, ArrayList<String> trigram,
			ArrayList<String> fourgram) throws IOException {
		FileWriter ngrams = new FileWriter("ngrams.csv");

		ngrams.write("monogram" + ",");
		for(String token : monogram) {
			ngrams.write(token+",");
		}
		ngrams.write("\n");
		ngrams.write("bigram" + ",");
		for(String token : bigram) {
			ngrams.write(token+",");
		}
		ngrams.write("\n");
		ngrams.write("trigram" + ",");
		for(String token : trigram) {
			ngrams.write(token+",");
		}
		ngrams.write("\n");
		ngrams.write("fourgram" + ",");
		for(String token : fourgram) {
			ngrams.write(token+",");
		}

		ngrams.close();
	}

	public void printalltokens(Integer numberoffiles, DocTokens[] alldocsandtokens,
			LinkedHashMap<String, Integer> numberofdocsfortoken, tfidfMatrix[] matrix, List<String> alltokens3)
			throws IOException {
		FileWriter tokenoutput = new FileWriter("alltokens.csv");

		for(Integer i = 0; i < numberoffiles; i++) {
			LinkedList<String> tokensinthisarticle = new LinkedList<String>();
			tokensinthisarticle = alldocsandtokens[i].returnlistoftokens();
			String foldername = alldocsandtokens[i].returnfoldername();
			String articlename = alldocsandtokens[i].returnarticlename();
			matrix[i] = new tfidfMatrix();
			matrix[i].calctfidf(foldername, articlename, numberofdocsfortoken, numberoffiles, alltokens3, tokensinthisarticle);	
			// matrix[i].normalizethis();		
		}

		LinkedHashMap<String, Double> tokenidfvalues = matrix[0].returntokenidfvalues();

		for(Map.Entry<String, Double> tokenidf : tokenidfvalues.entrySet()) {
			String token = tokenidf.getKey();
			Double idf = tokenidf.getValue();

			tokenoutput.write(token+",");
			tokenoutput.write(idf+",");
			tokenoutput.write("\n");
		}

		tokenoutput.close();
	}
	public void printtoptokens(List<String> sortedlistoffolders, Integer numberoffiles, tfidfMatrix[] matrix,
			SortbyValue sortthisplz, tfidfMatrix newmatrix) throws IOException {
		FileWriter TokenWriter = new FileWriter("toptokens.csv");

		// for each folder 
		for(String foldername : sortedlistoffolders) {
			
			List<String> top40tokens = new LinkedList<String>();
			
			HashMap<String, Double> toptokensforfolder = new HashMap<String, Double>();
			
			// go through every single number of files
			for(Integer i = 0; i < numberoffiles; i++) {
				// get the foldername for object in matrix[i]
				String matrixfoldername = matrix[i].returnfoldername();
				// if the folder names match
				if(foldername.equals(matrixfoldername)) {
					// return the hashmap of <token, tfidf> per each article 
					HashMap<String, Double> frequencyoftokens = matrix[i].returntfidfmatrix();
					// need to update toptokensforfolder
					newmatrix.updatetokensforthisfolder(toptokensforfolder, frequencyoftokens);
				}
			}

			TreeMap<String, Double> sortedtoptokens = sortthisplz.sortMapByValuestringdouble(toptokensforfolder);
			TreeMap<String, Double> sortedtop40tokens = sortedtoptokens.entrySet().stream()
				.limit(40)
				.collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

			for(Map.Entry<String, Double> entry : sortedtop40tokens.entrySet()) {
				String key = entry.getKey();
				top40tokens.add(key);
			}


			for(String token : top40tokens) {
				TokenWriter.write(token + ",");
			}

			TokenWriter.write("\n");
		}

		TokenWriter.close();
	}
	
	public void printtfidfmatrix(List<String> sortedlistoffolders, Integer numberoffiles, tfidfMatrix[] matrix)
			throws IOException {
		FileWriter tfidfmatrixwriter = new FileWriter("results.csv");
			// tfidfmatrixwriter.write(",");
			// tfidfmatrixwriter.write(",");
			// tfidfmatrixwriter.write(",");
			// for(String token : alltokens3) {
			// 	tfidfmatrixwriter.write(token+",");
			// }
			// tfidfmatrixwriter.write("\n");
		for(String folderorder : sortedlistoffolders) {
			for(Integer i = 0; i < numberoffiles; i++) {
				String foldername = matrix[i].returnfoldername();
				if(foldername.equals(folderorder)) {
					String articlename = matrix[i].returnarticlename();
					ArrayList<Double> vector = matrix[i].returnnormalizedtfidfvector();
					tfidfmatrixwriter.write(articlename+",");
					tfidfmatrixwriter.write(foldername+",");
					if(foldername.equals("C1")) {
						tfidfmatrixwriter.write("Airline Safety"+",");
					} else if (foldername.equals("C2")) {
						tfidfmatrixwriter.write("Amphetamine"+",");
					} else if (foldername.equals("C3")) {
						tfidfmatrixwriter.write("China and Spy Plan and Captives"+",");
					} else if (foldername.equals("C4")) {
						tfidfmatrixwriter.write("Hoof and Mouth Disease"+",");
					} else if (foldername.equals("C5")) {
						tfidfmatrixwriter.write("Iran Nuclear"+",");
					} else if (foldername.equals("C6")) {
						tfidfmatrixwriter.write("Korea and Nuclear Capability"+",");
					} else if (foldername.equals("C7")) {
						tfidfmatrixwriter.write("Mortgage Rates"+",");
					} else if (foldername.equals("C8")) {
						tfidfmatrixwriter.write("Ocean and Pollution"+",");
					} else if (foldername.equals("C9")) {
						tfidfmatrixwriter.write("Satanic Cult"+",");
					} else if (foldername.equals("C10")) {
						tfidfmatrixwriter.write("Storm Irene"+",");
					} else if (foldername.equals("C11")) {
						tfidfmatrixwriter.write("Volcano"+",");
					} else if (foldername.equals("C12")) {
						tfidfmatrixwriter.write("Saddam Hussein"+",");
					} else if (foldername.equals("C13")) {
						tfidfmatrixwriter.write("Kim Jong-un"+",");
					} else if (foldername.equals("C14")) {
						tfidfmatrixwriter.write("Predictive Analytics"+",");
					} else if (foldername.equals("C15")) {
						tfidfmatrixwriter.write("Irma & Harvey"+",");
					} 

					for(Double value : vector) {
						tfidfmatrixwriter.write(value+",");
						
					}

					tfidfmatrixwriter.write("\n");
				}
			}
		}

		tfidfmatrixwriter.close();
	}	
}
