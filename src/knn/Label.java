package knn;
import java.util.*;

// class for label - keeps track of tp, tn, fp, fn for one label 
class Label {
	private String labelname;
	private LinkedHashMap<String, Integer> guessedforthislabel = new LinkedHashMap<String, Integer>();
	private Integer truepositive = 0;
	private Integer truenegative = 0;
	private Integer falsepositive = 0;
	private Integer falsenegative = 0;
	
	public LinkedHashMap<String, Integer> returnguesses() {
		return guessedforthislabel;
	}
	
	public void setlabels(List<String> listoflabels) {
		for(String labelname : listoflabels) {
			guessedforthislabel.put(labelname, 0);
		}
	}
	
	public Label(String labelname) {
		this.labelname = labelname;
	}
	
	public String returnlabelname() {
		return labelname;
	}
	
	
	public void tpincreasebyone() {
		truepositive++;
	}
	
	public void tnincreasebyone() {
		truenegative++;
	}
	
	public void fpincreasebyone() {
		falsepositive++;
	}

	public void fnincreasebyone() {
		falsenegative++;
	}
	
	public Integer returntruepositive() {
		return truepositive;
	}
	
	public Integer returntruenegative() {
		return truenegative;
	}
	
	public Integer returnfalsepositive() {
		return falsepositive;
	}
	
	public Integer returnfalsenegative() {
		return falsenegative;
	}
	
	// calculates accuracy - TRUE/ (T + F)
	public Double calcaccuracy() {
		return ((double)(truepositive + truenegative)/(double)(truepositive+truenegative+falsepositive+falsenegative));
	}
	
	// calculates error rate - FALSE / (T + F)
	public Double calcerrorrate() {
		return ((double)(falsepositive + falsenegative)/ (double)(truepositive+truenegative+falsepositive+falsenegative));
	}
	
	public Integer returntotal() {
		return truepositive+truenegative+falsepositive+falsenegative;
	}
}
