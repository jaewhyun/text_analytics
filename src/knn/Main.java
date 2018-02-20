package knn;
import java.util.*;
import java.io.*;
import TextAnalytics.SortbyValue;
import TextAnalytics.ReadFile;
import TextAnalytics.StanfordNLP;

public class Main {
	public static void main(String[] args) throws IOException {
		ReadFile readingfiles = new ReadFile();
		// Read in test data
		TreeMap<File, String> newfiles = readingfiles.folderreader(args[0]);
		
		//Read in the train matrix from the csv file
		ReadMatrix readmatrixcsv = new ReadMatrix();
		
		//Make a list of tfidfvalues to store document vectors from the training data
		List<tfidfvalues> traintfidfmatrix = new ArrayList<tfidfvalues>();
		traintfidfmatrix = readmatrixcsv.readcsv();
		
		// import list of folders of the training data
		List<String> listoffolders = readmatrixcsv.returnlistoffolders();
		
		// import list of labels of the training data
		List<String> listoflabels = readmatrixcsv.returnsortedlabels(listoffolders);
		
		// set (tokens , idf) values from alltokens.csv from the first homework
		LinkedHashMap<String, Double> alltokens = readingfiles.readtokens();
		
		// read and set mono,bi,tri,four grams to process test data later
		LinkedHashMap<String, ArrayList<String>> ngramset = readingfiles.ngramset();
		// count the number of files coming in
		Integer numberoffiles = newfiles.size();
		
		// new array to hold new unlabeled document vectors
		DocTokens[] newdocsandtokens = new DocTokens[numberoffiles];
		
		//initiate Stanford nlp class
		StanfordNLP nlpstep = new StanfordNLP();
		
		Integer whereamI = 0;
		
		// process the new documents into vectors based on n-grams from homework 1 ----------
		while(whereamI < numberoffiles) {
			for(Map.Entry<File, String> file : newfiles.entrySet()) {
				LinkedList<String> newtokenlist = nlpstep.nlponthisfile(file.getKey());
				String foldername = file.getValue();
				String articlename = file.getKey().getName();
				articlename = articlename.substring(0, articlename.lastIndexOf("."));
				
				newdocsandtokens[whereamI] = new DocTokens();
				newdocsandtokens[whereamI].setarticlename(articlename);
				newdocsandtokens[whereamI].setfoldername(foldername);
				newdocsandtokens[whereamI].settokens(newtokenlist);
				whereamI++;
			}
		}
		
		
		TheseTokens tokenizeeverything = new TheseTokens();
		for(Map.Entry<String, ArrayList<String>> currentngram : ngramset.entrySet()) {
			String ngramname = currentngram.getKey();
			
			if(ngramname.equals("monogram")) {
				tokenizeeverything.setmonogram(currentngram.getValue());
			} else if(ngramname.equals("bigram")) {
				tokenizeeverything.setbigram(currentngram.getValue());
			} else if(ngramname.equals("trigram")) {
				tokenizeeverything.settrigram(currentngram.getValue());
			} else if(ngramname.equals("fourgram")) {
				tokenizeeverything.setfourgram(currentngram.getValue());
			}
		}
		
		tokenizeeverything.tokenswitch(newdocsandtokens);
		
		List<tfidfvalues> testtfidfmatrix = new ArrayList<tfidfvalues>();

		// initialize a list of tfidfvalues from newly processed doc vectors ----------
		for(int i = 0; i < newdocsandtokens.length; i++) {
			String articlename = newdocsandtokens[i].returnarticlename();
			String foldername = newdocsandtokens[i].returnfoldername();
			LinkedList<String> listoftokens = newdocsandtokens[i].returnlistoftokens();
			Vectorize vectorthis = new Vectorize();
			testtfidfmatrix.add(vectorthis.calctfidf(alltokens, foldername, articlename, null, listoftokens));
		}
		
		Printutil printplz = new Printutil();
		printplz.print_tfidf_matrix(numberoffiles, testtfidfmatrix);
		// ---- end output ----- 

		// ----- keep track of best k from 20 rounds of cross validation 
		// did 20 times to get the stability of the cross validation results
		HashMap<Integer, Integer> bestkmapunsorted = new HashMap<Integer, Integer>();
		
		Integer bestk2 = 0;
		Integer count = 0;
		while(count < 20) {
			// cross validation process
			// create folds
			TrainClass trainingclass = new TrainClass();
			// split the training data into 10 different folds
			trainingclass.splitintokfolds(traintfidfmatrix);
			List<EachFold> allfolds = new ArrayList<EachFold>();
			allfolds = trainingclass.returntenfoldtrainset();
			
			// retrieve bestk from newfold
			CrossValidation newfoldcv = new CrossValidation();
			bestk2 = newfoldcv.crossvalidation(allfolds, listoflabels);
			
			//input bestk retrieved from current round of cross validation in bestkmapunsorted
			if(bestkmapunsorted.containsKey(bestk2)) {
				bestkmapunsorted.put(bestk2, bestkmapunsorted.get(bestk2) + 1);
			} else {
				bestkmapunsorted.put(bestk2,  1);
			}
			
			count++;
		}

		SortbyValue sortthisplz = new SortbyValue();
		// sort for the bestk value that appeared the most often
		TreeMap<Integer, Integer> bestksorted = sortthisplz.sortMapByValueintintminus(bestkmapunsorted);
		Integer bestk = bestksorted.firstKey();
		System.out.println("the best k value from crossvalidation is: " + bestk);
		
		//  inorder to make a confusion matrix, we set the labels for the unknown documents
		for(int i = 0 ; i < testtfidfmatrix.size(); i ++) {
			String foldername = testtfidfmatrix.get(i).returnfoldername();
			
			if(foldername.equals("1")) {
				testtfidfmatrix.get(i).setlabelname("Irma & Harvey");
			} else if(foldername.equals("2")) {
				testtfidfmatrix.get(i).setlabelname("Predictive Analytics");
			}
		}
		
		ConfusionMatrix generateconfusionfortrain = new ConfusionMatrix();
		ConfusionMatrix generateconfusionfortest = new ConfusionMatrix();
		
		// output confusion matrix for the 15 folders
		generateconfusionfortrain.confusionmatrixfortrain(traintfidfmatrix, listoflabels, listoffolders, bestk);
		printplz.printeverything(generateconfusionfortrain.returnguessesforlabels(), listoflabels, "outputfortrain.csv");
		
		// output confusion matrix for the new test documents
		generateconfusionfortest.confusionmatrixfortest(traintfidfmatrix, testtfidfmatrix, listoflabels, bestk);
		printplz.printeverything(generateconfusionfortest.returnguessesforlabels(), listoflabels, "outputfortest.csv");

	}

	
}
