package k_means;
import java.util.*;

class Allclusters {
	private ArrayList<Cluster> listofclusters = new ArrayList<>();
	private Integer numberofclusters;

	public Cluster returncluster(int index) {
		return listofclusters.get(index);
	}

	public void addtolistofclusters(Cluster clusterinput) {
		listofclusters.add(clusterinput);
	}

	public void setnumberofclusters(int K) {
		numberofclusters = K;
	}

	public Boolean sameclusterdiff() {
		ArrayList<Boolean> trueorfalse = new ArrayList<Boolean>();
		ArrayList<Integer> sizeofclusters = new ArrayList<Integer>();

		for(int i = 0; i < numberofclusters; i++) {
			// get the clusterlistsize
			Integer clustersize = listofclusters.get(i).returnclusterlist().size();
			sizeofclusters.add(clustersize);
		}

		int count = 0 ;
		while(count < numberofclusters) {
			Integer size = sizeofclusters.get(count);
			if(size > 0) {
				for(int j = 0; j < numberofclusters; j++) {
					Boolean sameornotsame = listofclusters.get(j).clustermatch();
					trueorfalse.add(sameornotsame);
				}

				for(int j = 0; j < trueorfalse.size(); j++) {
					Boolean sameornotsame = trueorfalse.get(j);
					if(sameornotsame == false) {
						return false;
					}
					return true;
				}
			} else {
				return false;
			}

			count++;
		}

		return false;
	}

}
