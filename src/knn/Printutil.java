package knn;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Printutil {
	public void  printeverything(List<Label> guessesfortheselabels, List<String> listoflabels, String inputstring) throws IOException {
		FileWriter outputconfusion = new FileWriter(inputstring);
		
		outputconfusion.write("label" + ",");
		
		for(String label : listoflabels) {
			outputconfusion.write(label + ",");
		}
		
		outputconfusion.write("\n");
		
		for(int i = 0 ; i < guessesfortheselabels.size(); i++) {
			Label currentlabel = guessesfortheselabels.get(i);
			
			outputconfusion.write(currentlabel.returnlabelname()+",");
			LinkedHashMap<String, Integer> guessesforthislabel = currentlabel.returnguesses();
			
			Iterator<Map.Entry<String, Integer>> iterator = guessesforthislabel.entrySet().iterator();
			
			while(iterator.hasNext()) {
				Map.Entry<String, Integer> entry = iterator.next();
				
				outputconfusion.write(entry.getValue() + ",");
			}
			
			outputconfusion.write("\n");
		}
		outputconfusion.close();
	}
	
	public void print_tfidf_matrix(Integer numberoffiles, List<tfidfvalues> testtfidfmatrix)
			throws IOException {
		// write out new vectors to an output file -----
		FileWriter tfidfmatrixwriter = new FileWriter("newresults.csv");
		
		for(Integer i = 0; i < numberoffiles; i++) {
			tfidfvalues thisone = testtfidfmatrix.get(i);
			String articlename = thisone.returnarticlename();
			String foldername = thisone.returnfoldername();
			ArrayList<Double> vector = thisone.returntfidfvaluesforrow();
			tfidfmatrixwriter.write(articlename+",");
			tfidfmatrixwriter.write(foldername+",");
	
			for(Double value : vector) {
				tfidfmatrixwriter.write(value+",");
				
			}
			tfidfmatrixwriter.write("\n");
		}
		
		tfidfmatrixwriter.close();
	}
}
