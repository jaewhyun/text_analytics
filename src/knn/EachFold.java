package knn;
import java.util.*;

class EachFold {
	private List<tfidfvalues> docsinthisfold = new ArrayList<tfidfvalues>();
	private Integer foldnumber;
	
	public EachFold(Integer foldnumber) {
		this.foldnumber = foldnumber;
	}
	
	public void addtofold(tfidfvalues thistfidfvalues) {
		docsinthisfold.add(thistfidfvalues);
	}
	
	public Integer returnfoldnumber() {
		return foldnumber;
	}
	
	public Integer numberofdocsinthisfold() {
		return docsinthisfold.size();
	}
	
	public List<tfidfvalues> returnlistoftfidfs() {
		return docsinthisfold;
	}
}
