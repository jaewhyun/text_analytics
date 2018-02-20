package knn;
import java.util.*;

//this class stores document vectors
class tfidfvalues {
	ArrayList<Double> tfidfvaluesforrow = new ArrayList<Double>();
	LinkedHashMap<String, Double> tokenplustfidf = new LinkedHashMap<String, Double>();
	String foldername;
	String articlename;
	String labelname;

	public tfidfvalues(String articlename, String foldername, String labelname, LinkedHashMap<String, Double> tokenplustfidf, ArrayList<Double> tfidfvaluesforrow) {
		this.articlename = articlename;
		this.foldername = foldername;
		this.labelname = labelname;
		this.tokenplustfidf = tokenplustfidf;
		this.tfidfvaluesforrow = tfidfvaluesforrow;
	}
	
	public void setlabelname(String currentlabelname) {
		labelname = currentlabelname;
	}

	public void settfidfvaluesforrow(ArrayList<Double> input){
		tfidfvaluesforrow = input;
	}

	public ArrayList<Double> returntfidfvaluesforrow() {
		return tfidfvaluesforrow;
	}

	public void setfoldername(String input) {
		foldername = input;
	}

	public String returnfoldername(){
		return foldername;
	}

	public void setarticlename(String input) {
		articlename = input;
	}

	public String returnarticlename() {
		return articlename;
	}
	
	public String returnlabelname() {
		return labelname;
	}
}