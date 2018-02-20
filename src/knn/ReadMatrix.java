package knn;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import TextAnalytics.AlphanumComparator;

// reads the matrix
class ReadMatrix {
	private ArrayList<String> listofarticles = new ArrayList<String>();
	private List<String>listoflabels = new ArrayList<String>();
	private LinkedHashMap<String, String> unsortedlabels = new LinkedHashMap<String, String>();
	private LinkedHashSet<String>listoffolders = new LinkedHashSet<String>();

	//http://www.java67.com/2015/08/how-to-load-data-from-csv-file-in-java.html
	public List<tfidfvalues> readcsv() {

		String csvfile = "results.csv";
		List<tfidfvalues> tfidfmatrix = new ArrayList<>();

		try(BufferedReader br = new BufferedReader(new FileReader(csvfile))) {
			String line = br.readLine();

			while(line != null) {
				String[] row = line.split(",");
				listoffolders.add(row[1]);
				unsortedlabels.put(row[1], row[2]);
			
				tfidfvalues tfidfvalues = createtfidfrow(row);

				tfidfmatrix.add(tfidfvalues);

				line = br.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return tfidfmatrix;
		
	}
	
	// list of folder names is also collected
	public List<String> returnlistoffolders() {
		List<String> beforesorted = new ArrayList<String>();
		
		for(String thisfolder : listoffolders) {
			beforesorted.add(thisfolder);
		}
		
		List<String> sorted = beforesorted.stream().sorted(new AlphanumComparator()).collect(Collectors.toList());
		
		return sorted;
	}
	
	// list of labels is also collected
	public List<String> returnsortedlabels(List<String> foldernames) {
		
		for(String folder : foldernames) {
			Iterator<Map.Entry<String, String>> iterator = unsortedlabels.entrySet().iterator();
			
			while(iterator.hasNext()) {
				Map.Entry<String, String> entry = iterator.next();
				
				String foldername = entry.getKey();
				if(foldername.equals(folder)) {
					listoflabels.add(entry.getValue());
				}
			}
		}
		
		return listoflabels;
	}

	// creates tfidfvalues
	public tfidfvalues createtfidfrow (String[] input) {
		String articlename = input[0];
		String foldername = input[1];
		String labelname = input[2];
		
		String articlefolder = articlename + "_" + foldername;
		listofarticles.add(articlefolder);
		ArrayList<Double> tfidfvaluesforrow = new ArrayList<Double>();
		for(int i = 3; i < input.length; i++) {
			tfidfvaluesforrow.add(Double.parseDouble(input[i]));
		}

		return new tfidfvalues(articlename, foldername, labelname, null, tfidfvaluesforrow);
	}
	
}