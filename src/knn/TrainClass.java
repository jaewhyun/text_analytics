package knn;
import java.util.*;

// Class to hold all 10 folds
class TrainClass {
	private List<EachFold> tenfoldtrainset = new ArrayList<EachFold>();
	
	public List<EachFold> returntenfoldtrainset(){
		return tenfoldtrainset;
	}
	
	// split the whole dataset into different folds
	public void splitintokfolds (List<tfidfvalues> wholematrix) {
		Random randomizer = new Random();
		
		Integer maxsize = wholematrix.size();
		
		// get random numbers
		Set<Integer> randomnumbers1 = new LinkedHashSet<Integer>();
		while(randomnumbers1.size() < maxsize) {
			Integer nextrandom = randomizer.nextInt(maxsize);
			randomnumbers1.add(nextrandom);
		}
		
		ArrayList<Integer> randomnumbers = new ArrayList<Integer>();
		for(Integer number : randomnumbers1) {
			randomnumbers.add(number);
		}
		
		Integer howmanyineachfold = wholematrix.size()/10;
		Integer count1 = 0;
		Integer count2 = 0;
		Integer foldnumber = 0;
		
		// mix up the vectors so that there are random 12 ~ 13 vectors in each fold --------
		while(count1 < randomnumbers.size() - 1 && foldnumber != 10) {
			if(count1 + howmanyineachfold < randomnumbers.size()) {
				if(count1 % howmanyineachfold == 0) {
					foldnumber++;
					EachFold newfold = new EachFold(foldnumber);
					
					count2 = 0;
					
					// making sure there are 122/10 folds in this fold
					while(count2 < howmanyineachfold) {
						Integer location = randomnumbers.get(count1);
						
						String articlename = wholematrix.get(location).returnarticlename();
						String foldername = wholematrix.get(location).returnfoldername();
						String labelname = wholematrix.get(location).returnlabelname();
						ArrayList<Double> tfidfvalues = wholematrix.get(location).returntfidfvaluesforrow();
					
						tfidfvalues addthistofold = new tfidfvalues(articlename, foldername, labelname, null, tfidfvalues);
					
						newfold.addtofold(addthistofold);
						
						count2++;
						count1++;
					}
					
					tenfoldtrainset.add(newfold);
				}
			} 
			
		}
		
		
		// account for leftover vectors that still needs to be inputted to a fold
		Integer leftovers = 0;
		if(wholematrix.size() - count1 > 0) {
			while(count1 < wholematrix.size() ) {
				//System.out.println("there are still " + (wholematrix.size() - count1) + " leftovers");
				String articlename = wholematrix.get(count1).returnarticlename();
				String foldername = wholematrix.get(count1).returnfoldername();
				String labelname = wholematrix.get(count1).returnlabelname();
				ArrayList<Double> tfidfvalues = wholematrix.get(count1).returntfidfvaluesforrow();
				
				tfidfvalues addthistofold = new tfidfvalues(articlename, foldername, labelname, null, tfidfvalues);
				
				//System.out.println("adding to " + tenfoldtrainset.get(leftovers).returnfoldnumber() + " fold");
				tenfoldtrainset.get(leftovers).addtofold(addthistofold);
				
				leftovers++;
				count1++;
			}
		}
	}
}
