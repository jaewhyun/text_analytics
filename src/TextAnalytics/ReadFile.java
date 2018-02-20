package TextAnalytics;

import java.util.*;
import java.io.*;

public class ReadFile {
	TreeMap<File, String> filebyfolder = new TreeMap<File,String>();
	ArrayList<File> listofallfiles = new ArrayList<File>();
	HashSet<String> stopwords = new HashSet<String>();
	HashSet<String> listoffoldernames = new HashSet<String>();
	
	public Integer returnnumberoffiles() {
		return listofallfiles.size();
	}
	
	public TreeMap<File, String> folderreader(String absolutepath) {
		File directory = new File(absolutepath);
		File[] fList = directory.listFiles();
		
		String foldername;
		for(File file : fList) {
			if(file.isFile() && file.getName().endsWith(".txt")) {
				
				listofallfiles.add(file);
				foldername = file.getParent().substring(file.getParent().lastIndexOf(File.separator) + 1);
				listoffoldernames.add(foldername);
				
				filebyfolder.put(file, foldername);
				//System.out.println("added file");
				
			} else if(file.isDirectory()) {
				folderreader(file.getAbsolutePath());
			}
		}
		
		return filebyfolder;
	}
	
	public LinkedHashMap<String, Double> readtokens () {
		LinkedHashMap<String, Double> alltokens = new LinkedHashMap<String, Double>();
		String csvfile = "alltokens.csv";
		
		try(BufferedReader br = new BufferedReader(new FileReader(csvfile))) {
			String line = br.readLine();
			
			while(line != null) {
				String[] row = line.split(",");
				
				alltokens.put(row[0], Double.parseDouble(row[1]));
				
				line = br.readLine();
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		return alltokens;
	}
	
	public LinkedHashMap<String, ArrayList<String>> ngramset() {
		String csvfile = "ngrams.csv";
		
		LinkedHashMap<String, ArrayList<String>> ngrams = new LinkedHashMap<String, ArrayList<String>>();
		try(BufferedReader br = new BufferedReader(new FileReader(csvfile))) {
			String line = br.readLine();
			
			while(line != null) {
				String[] row = line.split(",");
				
				ArrayList<String> gramlist = new ArrayList<String>();
				for(int i = 1; i < row.length; i++) {
					gramlist.add(row[i]);
				}
				
				ngrams.put(row[0], gramlist);
				
				line = br.readLine();
			} 
		}	catch (IOException ioe) {
				ioe.printStackTrace();
		}
			
		return ngrams;
	}
	
	
	
	public HashSet<String> readstopwords() {
		try {
			File stopwordlist = new File("Data/stopwords.txt");
			String stop;
			Scanner inputstop = new Scanner(stopwordlist);
			
			while(inputstop.hasNext()) {
				stop = inputstop.next();
				String punctregex = "[\\p{Punct}]";
				stop = stop.replaceAll(punctregex, "");
				stopwords.add(stop);
			}
			
			inputstop.close();
		} catch (Exception e) {
			e.printStackTrace();;
		}
		
		return stopwords;
	}
	
	public HashSet<String> returnlistoffoldernames() {
		return listoffoldernames;
	}
	
	public Integer returnnumberoffolders() {
		return listoffoldernames.size();
	}
}
