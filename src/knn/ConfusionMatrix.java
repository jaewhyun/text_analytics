package knn;
import java.util.*;

class ConfusionMatrix {
	// list of class Labels to keep track of tp, fp, tn, fn
	private List<Label> guessesfortheselabels = new ArrayList<Label>();
	
	// setting confusion matrix values for test
	public void confusionmatrixfortest(List<tfidfvalues> wholefifteen, List<tfidfvalues> testset, List<String> listoflabels, Integer K) {		
		// set all labels for each Label in the list of guessesfortheselables - to keep track of what was guessed
		for(String label : listoflabels) {
			Label newlabel = new Label(label);
			newlabel.setlabels(listoflabels);
			guessesfortheselabels.add(newlabel);
		}
		
		Iterator<tfidfvalues> iteratorfornew = testset.iterator();
		while(iteratorfornew.hasNext()) {
			// retrieve a doc's tfidfvalues that we want to guess for from the test set
			tfidfvalues thisdoc = iteratorfornew.next();
			ArrayList<Double> tfidfforthisrow = thisdoc.returntfidfvaluesforrow();
			String labelname = thisdoc.returnlabelname();
			
			guessthelabel(K, labelname, wholefifteen, tfidfforthisrow);		
		}
	}

	private void keepcount(String returnedlabel, int i) {
		Label currentlabel = guessesfortheselabels.get(i);
		LinkedHashMap<String, Integer> guesses = currentlabel.returnguesses();
		if(guesses.containsKey(returnedlabel)) {
			//increase count
			guesses.put(returnedlabel, guesses.get(returnedlabel) + 1);
		}
	}
	
	public List<Label> returnguessesforlabels() {
		return guessesfortheselabels;
	}
	
	// setting confusion matrix values for train
	public void confusionmatrixfortrain(List<tfidfvalues> wholefifteen, List<String> listoflabels, List<String> listoffolders, Integer K) {
		// set all labels for each Label in the list of guessesfortheselables - to keep track of what was guessed
		for(String label : listoflabels) {
			Label newlabel = new Label(label);
			newlabel.setlabels(listoflabels);
			guessesfortheselabels.add(newlabel);
		}
		
		// retrieve a doc's tfidfvalues that we want to guess for from the test set
		Iterator<tfidfvalues> iteratorformatrix = wholefifteen.iterator();
		while(iteratorformatrix.hasNext()) {
			tfidfvalues thisdoc = iteratorformatrix.next();
			String labelname = thisdoc.returnlabelname();
			
			List<tfidfvalues> therest = new ArrayList<tfidfvalues>();
			
			// to ensure that you are exluding yourself from k nearest neighbours
			for(tfidfvalues thisarticle : wholefifteen) {
				if(!thisarticle.equals(thisdoc)) {
					therest.add(thisarticle);
				}
			}
			
			ArrayList<Double> tfidfforthisrow = thisdoc.returntfidfvaluesforrow();
			
			guessthelabel(K, labelname, therest, tfidfforthisrow);
		}
	}

	private void guessthelabel(Integer K, String labelname, List<tfidfvalues> therest,
			ArrayList<Double> tfidfforthisrow) {
		KNN returnlabel = new KNN();
		String returnedlabel = returnlabel.returnKnearestlabel(therest, tfidfforthisrow, K);
		
		// go through the list of the labels
		for(int i = 0; i < guessesfortheselabels.size(); i++) {
			// find the label with the current doc label
			if(labelname.equals(guessesfortheselabels.get(i).returnlabelname())) {
				// if the label returned was a match with the currentlabel
				if(returnedlabel.equals(labelname)) {
					keepcount(returnedlabel, i);
				} else {
					keepcount(returnedlabel, i);
				}
			}
		}
	}
}
