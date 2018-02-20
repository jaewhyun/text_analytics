package k_means;

import java.util.*;
import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException{
		ReadMatrix matrixreader = new ReadMatrix();
		List<tfidfvalues> tfidfmatrix = matrixreader.readcsv();

		Kmeans calckmeanscosinesim = new Kmeans();
		System.out.println("returning similar docs for whole matrix: ");
		calckmeanscosinesim.returnMsimilardocs(tfidfmatrix);
		System.out.println("returning kmeans using cosine similarity for whole matrix: ");
		Kmeans calckmeanscosine = new Kmeans();
		calckmeanscosine.kmeanscosine(tfidfmatrix);
		System.out.println("returning kmeans using cosine similarity using your choice of folders");
		Kmeans calckmeanscos3 = new Kmeans();
		calckmeanscos3.kmeanscosinepick3(tfidfmatrix);
		Kmeans calckmeanseuc = new Kmeans();
		System.out.println("returning kmeans using euclidean distance for whole matrix: ");
		calckmeanseuc.kmeanseuclidean(tfidfmatrix);
		System.out.println("returning kmeans using euclidean distance using your choice of folders");
		Kmeans calckmeanseuc3 = new Kmeans();
		calckmeanseuc3.kmeanseuclideanpick3(tfidfmatrix);

		// for plotting 
		SVD SVDdecomposition = new SVD();

		double[][] newmatrix = SVDdecomposition.convertto2d(tfidfmatrix);
		// newmatrix.Kmeans(3);
		SVDdecomposition.converttomatrix(newmatrix);
		SVDdecomposition.doeverything();

		double[][] reducedmatrixforplot = SVDdecomposition.return2d();
		double[][] reducedmatrixkmeans = SVDdecomposition.returntopd();
		double[][] sigmamatrix = SVDdecomposition.returnsigma();
		
		Printutil printthisplz = new Printutil();
		printthisplz.print_sigmamatrix(SVDdecomposition, sigmamatrix);

		LinkedHashMap<String, ArrayList<Double>> articlesandcoord = new LinkedHashMap<String, ArrayList<Double>>();

		ArrayList<String> listofarticles = matrixreader.fulllistofarticles();

		for(int i = 0; i < listofarticles.size(); i++) {
			String article = listofarticles.get(i);
			ArrayList<Double> coordinates = new ArrayList<Double>();
			for(int j = 0; j < 2; j++) {
				double coordinate = reducedmatrixforplot[i][j];
				Double D = new Double(coordinate);
				coordinates.add(D);
			}

			articlesandcoord.put(article, coordinates);
		}

		List<tfidfvalues> reducedtfidfmatrix = new ArrayList<>();
		for(int i = 0; i < listofarticles.size(); i++) {
			String article = listofarticles.get(i);
			List<String> articleandfolder =  Arrays.asList(article.split("_"));
			String articlename = articleandfolder.get(0);
			String foldername = articleandfolder.get(1);
			String labelname = articleandfolder.get(2);

			ArrayList<Double> tfidfvalueforrow = new ArrayList<Double>();
			for(int j = 0; j < 15; j++) {
				double tfidfvalue = reducedmatrixkmeans[i][j];
				Double D = new Double(tfidfvalue);
				tfidfvalueforrow.add(D);
			}

			tfidfvalues reducedvalueforrow = new tfidfvalues(articlename, foldername, labelname, null, tfidfvalueforrow);
			reducedtfidfmatrix.add(reducedvalueforrow);
		}

		Kmeans calckmeanscosinereduced = new Kmeans();
		System.out.println("returning similar docs using reduced matrix: ");
		calckmeanscosinereduced.returnMsimilardocs(reducedtfidfmatrix);
		System.out.println("returning kmeans using cosine similarity on reduced matrix: ");
		calckmeanscosinereduced.kmeanscosine(reducedtfidfmatrix);
		Kmeans calckmeanseucreduced = new Kmeans();
		System.out.println("returning kmeans using euclidean distance on reduced matrix: ");
		calckmeanseucreduced.kmeanseuclidean(reducedtfidfmatrix);
		System.out.println("returning kmeans using cosine similarity on reduced matrix using your choice of folders");
		Kmeans calckmeanscos3reduced = new Kmeans();
		calckmeanscos3reduced.kmeanscosinepick3(tfidfmatrix);
		System.out.println("returning kmeans using euclidean distance on reduced matrix using your choice of folders");
		Kmeans calckmeanseuc3reduced = new Kmeans();
		calckmeanseuc3reduced.kmeanseuclideanpick3(tfidfmatrix);

		Allclusters listofclusters = calckmeanscosine.returnlistofclusters();
		Integer clustersize = calckmeanscosine.returnclustersize();

		printthisplz.print_plot(articlesandcoord, listofclusters, clustersize);
	}
}

