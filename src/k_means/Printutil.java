package k_means;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Printutil {
	public void print_sigmamatrix(SVD SVDdecomposition, double[][] sigmamatrix) throws IOException {
		FileWriter sigmaoutput = new FileWriter("sigmamatrix.csv");
		
		for(int i = 0; i < SVDdecomposition.sigmarows(); i++) {
			for(int j = 0; j < SVDdecomposition.sigmacolumns(); j++) {
				double output = sigmamatrix[i][j];
				sigmaoutput.write (output + ",");
			}
			
			sigmaoutput.write("\n");
		}

		sigmaoutput.close();
	}
	
	public void print_plot(LinkedHashMap<String, ArrayList<Double>> articlesandcoord,
			Allclusters listofclusters, Integer clustersize) throws IOException {
		FileWriter coordinateoutput = new FileWriter("plot.csv");

		for(Map.Entry<String, ArrayList<Double>> entry : articlesandcoord.entrySet()) {
			String articlefolder = entry.getKey();
			ArrayList<Double> coordinates = entry.getValue();
			for(int i = 0; i < clustersize; i++) {
				ArrayList<String> clusterlist = listofclusters.returncluster(i).returnclusterlist();
				for(String isthisit : clusterlist) {
					if(articlefolder.equals(isthisit)) {
						listofclusters.returncluster(i).addtocoordinatelist(articlefolder, coordinates);
					}
				}
			}
		}

		for(int i = 0; i < clustersize; i++) {
			for(Map.Entry<String, ArrayList<Double>> entry : listofclusters.returncluster(i).returncoordinates().entrySet()) {
				coordinateoutput.write(listofclusters.returncluster(i).returnclusternumber() + ",");
				String articlefolder = entry.getKey();
				coordinateoutput.write(articlefolder+",");
				ArrayList<Double> coordinates = entry.getValue();
				for(Double coordinate : coordinates) {
					coordinateoutput.write(coordinate + ",");
				}

				coordinateoutput.write("\n");
			}
		}

		coordinateoutput.close();
	}
}
