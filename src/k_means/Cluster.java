package k_means;
import java.util.*;

class Cluster {
	private String clusternumber;
	private ArrayList<Double> centroid = new ArrayList<Double>();
	private ArrayList<String> clusterlist = new ArrayList<String>();
	private ArrayList<String> previousclusterlist = new ArrayList<String>();
	private ArrayList<ArrayList<Double>> vectorsinthiscluster = new ArrayList<ArrayList<Double>>();
	private LinkedHashMap<String, ArrayList<Double>> coordinates = new LinkedHashMap<String, ArrayList<Double>>();

	public Cluster(String clusternumber, ArrayList<Double> centroid){
		this.clusternumber = clusternumber;
		this.centroid = centroid;
	}

	public String returnclusternumber() {
		return clusternumber;
	}

	public void setcentroid(ArrayList<Double> input) {
		centroid = input;
	}

	public void setclusterlist(ArrayList<String> input) {
		clusterlist = input;
	}

	public ArrayList<Double> returncentroid(){
		return centroid;
	}

	public ArrayList<String> returnclusterlist(){
		return clusterlist;
	}

	public ArrayList<String> returnpreviousclusterlist() {
		return previousclusterlist;
	}

	public void addtoclusterlist(String input) {
		clusterlist.add(input);
	}

	public void copyclusters() {
		for(String thisstring : clusterlist) {
			previousclusterlist.add(thisstring);
		}
	}

	public void resetpreviousclusterlist() {
		previousclusterlist = new ArrayList<String>();
	}

	public void resetclusterlist() {
		clusterlist = new ArrayList<String>();
	}

	public Boolean clustermatch(){
		HashSet<String> set1 = new HashSet<String>(clusterlist);
		HashSet<String> set2 = new HashSet<String>(previousclusterlist);
		
		if(set1.size() == 0 && set2.size() == 0){
			return true;
		}
		else if(set1.size() == set2.size()){
			return(set2.containsAll(set2));
		} else {
			return false;
		}
	}

	public void calcnewcentroid() {
		Integer numberofvectors = vectorsinthiscluster.size();
		// if the list of vectors is not empty 
		if(numberofvectors > 0) {
			Integer vectorsize = vectorsinthiscluster.get(0).size();
			ArrayList<Double> newcentroid = new ArrayList<Double>();
			Double newpointforcurrentoken = 0.0;

			for(int i = 0; i < vectorsize; i++) {
				for(int j = 0; j < numberofvectors; j++) {
					ArrayList<Double> currentvector = vectorsinthiscluster.get(j);
					Double tfidfvalueofcurrentvector = currentvector.get(i);

					newpointforcurrentoken = newpointforcurrentoken + tfidfvalueofcurrentvector;
				}

				newpointforcurrentoken = newpointforcurrentoken/((double) numberofvectors);
				newcentroid.add(newpointforcurrentoken);
			}

			centroid = newcentroid;
		} 
	}

	public void addtovectorsinthiscluster(ArrayList<Double> input) {
		vectorsinthiscluster.add(input);
	}

	public void addtocoordinatelist(String articlefolder, ArrayList<Double> coordinate) {
		coordinates.put(articlefolder, coordinate);
	}

	public LinkedHashMap<String, ArrayList<Double>> returncoordinates() {
		return coordinates;
	}

}
