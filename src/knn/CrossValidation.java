package knn;
import java.util.*;
import TextAnalytics.SortbyValue;

// Class for Cross validation process
class CrossValidation {
	// list of currentK objects to keep track of all k values
	private List<currentK> listofks = new ArrayList<currentK>();

	// validation process
	public void validationprocess (List<EachFold> trainset, EachFold validationset, Integer K, currentK thisK) {
		// validation fold
		List<tfidfvalues> validationsettfidf = validationset.returnlistoftfidfs();
		// the 9 other folds that serve as training fold that needs to be put into a list
		List<tfidfvalues> alltraining = new ArrayList<tfidfvalues>();
	
		// from the 9 other folds, collect all tfidf vectors into a list
		for(int i = 0; i < trainset.size(); i++) {
			EachFold currentfold = trainset.get(i);
			List<tfidfvalues> trainingsettfidf = currentfold.returnlistoftfidfs();
			for(int j = 0; j < trainingsettfidf.size(); j++) {
				tfidfvalues currentdoc = trainingsettfidf.get(j);
				alltraining.add(currentdoc);
			}
		}
		
		// for each article vector in validation set
		for(int i = 0; i < validationsettfidf.size(); i++) {
			tfidfvalues currentdoc = validationsettfidf.get(i);
			ArrayList<Double> tfidfvalues = currentdoc.returntfidfvaluesforrow();
			String labelname = currentdoc.returnlabelname();
			List<Label> listoflabels = thisK.returnlabels();
			
			
			KNN knnalgorithm = new KNN();
			// apply knn algorithm and get the label
			String knearestlabel = knnalgorithm.returnKnearestlabel(alltraining, tfidfvalues, K);
			
			//correctly guessed
			if(knearestlabel.equals(labelname)) {
				for(int j = 0; j < listoflabels.size(); j++) {

					Label currentlabel = listoflabels.get(j);
					String labelnamefromlist = currentlabel.returnlabelname();
				
					if(labelnamefromlist.equals(labelname)) {
						// increase correctly guessed for this label
						currentlabel.tpincreasebyone();
					} else {
						// increase correctly not guessed for this label
						currentlabel.tnincreasebyone();
					}
				}
				//we did not predict correctly
			} else {
				
				for(int j = 0; j < listoflabels.size(); j++) {
					
					Label currentlabel = listoflabels.get(j);
					String labelnamefromlist = currentlabel.returnlabelname();
					// we predicted yes, but it wasn't it
					if(labelnamefromlist.equals(knearestlabel)) {
						currentlabel.fpincreasebyone();
					// we predicted no, but it was it
					} else if (labelnamefromlist.equals(labelname)) {
						
						currentlabel.fnincreasebyone();
					// we predicted no correctly
					} else {
						currentlabel.tnincreasebyone();
					}
				}
			}
		}
	}
	
	public Integer crossvalidation (List<EachFold> allfolds, List<String> listoflabels) {
		
		for(int k = 1; k < 11; k++) {
			currentK thisK = new currentK(k);
			thisK.setlabels(listoflabels);
			
			for(int i = 0; i < allfolds.size(); i++) {
				EachFold validationfold = allfolds.get(i);
				
				// combine 9 other folds into trainfolds
				List<EachFold> trainfold = new ArrayList<EachFold>();
				
				if(i > 0) {
					for(int j = 0; j < i; j++) {
						EachFold intrainfold = allfolds.get(j);
						trainfold.add(intrainfold);
					}
					
					if((i+1) == (allfolds.size() - 1)) {
						EachFold intrainfold = allfolds.get(i+1);
						trainfold.add(intrainfold);
					} else if (i < allfolds.size() - 2) {
						for(int z = (i+1); z < allfolds.size(); z++) {
							EachFold intrainfold = allfolds.get(z);
							trainfold.add(intrainfold);
						}
					}
				} else {
					
					for(int j = 1; j < allfolds.size(); j++) {
						EachFold intrainfold = allfolds.get(j);
						trainfold.add(intrainfold);
					}
				}
				
				validationprocess(trainfold, validationfold, k, thisK);
			}
			
			listofks.add(thisK);
		}
		
		// keep track of both best K and error rate and accuracy rate
		HashMap<Integer, Double> unsortedbestK = new HashMap<Integer, Double>();
		HashMap<Integer, Double> unsortedleasterror = new HashMap<Integer, Double>();
		TreeMap<Integer, Double> sortedleasterror = new TreeMap<Integer, Double>();
		
		// for each K
		for(int i = 0 ; i < listofks.size(); i++) {
			currentK thisK = listofks.get(i);
			Double totalaccuracy = 0.0;
			Double totalerror = 0.0;
			// collect all accuracy for all labels
			for(int j = 0; j < thisK.returnlabels().size(); j++) {
				// collect accuracy and error for all labels
				Label thislabel = thisK.returnlabels().get(j);
				Double accuracy2 = thislabel.calcaccuracy();
				Double error2 = thislabel.calcerrorrate();
				totalaccuracy = totalaccuracy + accuracy2;
				totalerror = totalerror + error2;
			}
			
			// get average accuracy and error rate for this k 
			Double averageaccuracy = totalaccuracy/15;
			Double averageerror = totalerror / 15;
			
			// input the items into respective lists - i did not use the accuracy but if  need be, can be used
			unsortedbestK.put(thisK.returnK(), averageaccuracy);
			unsortedleasterror.put(thisK.returnK(), averageerror);
		}
		
		// sort the unsorted into sorted so that we get least amount of error K
		SortbyValue sortthisplz = new SortbyValue();
		sortedleasterror = sortthisplz.sortMapByValueintintplus(unsortedleasterror);
		
		// return best K with least amount of error
		Integer bestK = sortedleasterror.firstKey();
		return bestK;
	}
	
	// comparators that sort -----

}
