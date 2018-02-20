package knn;
import java.util.*;

// List of tokens for a specific document
class DocTokens {
	private String foldername;
	private String articlename;
	private String labelname;
	private LinkedList<String> listoftokens = new LinkedList<String>();
	
	public void setlabelname(String currentlabelname) {
		labelname = currentlabelname;
	}
	
	public String returnlabelname(String returnlabelname) {
		return labelname;
	}
	
	public void setarticlename(String currentarticlename) {
		articlename = currentarticlename;
	}

	public String returnarticlename() {
		return articlename;
	}
	
	public void setfoldername(String thisfolder) {
		foldername = thisfolder;
	}
	
	public String returnfoldername() {
		return foldername; 
	}
	
	public void settokens(LinkedList<String> token) {
		listoftokens = token;
	}
	
	public LinkedList<String> returnlistoftokens() {
		return listoftokens;
	}
}
