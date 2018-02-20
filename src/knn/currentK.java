package knn;

import java.util.*;

// class for the current K 
class currentK {
	private Integer numberK;
	private List<Label> labelsforthisk = new ArrayList<Label>();
	
	public currentK(Integer numberK) {
		this.numberK = numberK;
	}
	
	public Integer returnK() {
		return numberK;
	}
	
	public void setlabels(List<String> listoflabels) {
		for(String labelname : listoflabels) {
			Label currentlabel = new Label(labelname);
			labelsforthisk.add(currentlabel);
		}
	}
	
	public List<Label> returnlabels() {
		return labelsforthisk;
	}
	
}
