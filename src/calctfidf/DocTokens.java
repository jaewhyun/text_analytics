package calctfidf;
import java.util.*;

class DocTokens {
	private String foldername;
	private String articlename;
	private LinkedList<String> listoftokens = new LinkedList<String>();
	
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
