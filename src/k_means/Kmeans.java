package k_means;
import java.util.*;

import TextAnalytics.SortbyValue;

class Kmeans{
	private Allclusters listofclusters = new Allclusters();
	private Integer clustersize = 0;

	public void returnMsimilardocs(List<tfidfvalues> matrix)
	{
		Scanner scanner1 = new Scanner(System.in);
		System.out.print("Which folder?: ");
		String foldername = scanner1.nextLine();

		Scanner scanner4 = new Scanner(System.in);
		System.out.print("Which article?: ");
		String articlename = scanner4.nextLine();

		for(int i = 0; i < matrix.size(); i++) {
			String compvectorarticlename = matrix.get(i).returnarticlename();
			String compvectorfoldername = matrix.get(i).returnfoldername();

			if(compvectorarticlename.equals(articlename) && compvectorfoldername.equals(foldername)) {
				ArrayList<Double> frequencyoftokensmatrix = matrix.get(i).returntfidfvaluesforrow();

				Scanner scanner3 = new Scanner(System.in);
				System.out.print("How many similar documents would you like to get?");
				Integer numberofreturndocuments = scanner3.nextInt();

				EuclideanDistance EuclideanDistancemethod = new EuclideanDistance();
				TreeMap<String, Double> similarityvalues = EuclideanDistancemethod.seteucsimilarityvalues(matrix, frequencyoftokensmatrix);
				TreeMap<String, Double> topnumberofmatrices = similarityvalues.entrySet().stream()
					.limit(numberofreturndocuments)
					.collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

				System.out.println("According to euclidean distance, the similar documents are: ");
				for(Map.Entry<String, Double> entry : topnumberofmatrices.entrySet()) {
					String key = entry.getKey();
					System.out.println(key);
				}

				CosineSimilarity cosinesimilaritymethod = new CosineSimilarity();
				TreeMap<String, Double> similarityvalues2 = cosinesimilaritymethod.setcosinesimilarityvalues_kmeans(matrix, frequencyoftokensmatrix);
				TreeMap<String, Double> topnumberofmatrices2 = similarityvalues2.entrySet().stream()
					.limit(numberofreturndocuments)
					.collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);

				System.out.println("According to cosine similarity, the similar documents are: ");
				for(Map.Entry<String, Double> entry : topnumberofmatrices2.entrySet()) {
					String key = entry.getKey();
					System.out.println(key);
				}
				
			}
		}
	}

	public void kmeanscosine(List<tfidfvalues> matrix) {
		Scanner scanner1 = new Scanner(System.in);
		System.out.print("Enter K: ");
		Integer K = 0;
		if(scanner1.hasNextInt()) {
			K = scanner1.nextInt();
		} else {
			K = -1;
		}
		
		clustersize = K;

		//allclusters listofclusters = new allclusters();
		listofclusters.setnumberofclusters(K);

		Simvaluesforone getK = new Simvaluesforone();
		ArrayList<String> centroidarticle = getK.returnrandomcentroid(K, matrix);//getK.returntopcosineK(K, matrix);

		establishclusters(matrix, centroidarticle);

		CosineSimilarity calccosinesimilarity = new CosineSimilarity();
		// while the clusters don't match the previous cluster 
		// at the beginning it won't be a match anyways because previouscluster and currentcluster won't be a match anyways
		int iterations = 0;
		while(!listofclusters.sameclusterdiff() && iterations <= 100) {
			//System.out.println("we tested false");
			// calc new centroid...if there are no other vectors in the vector list, then it won't do anything
			for(int i = 0 ; i < clustersize; i++) {
				listofclusters.returncluster(i).calcnewcentroid();
			}
			
			//after calculating new centroid, since the two lists don't match anyways
			resetclusters(K);

			// go through all the articles in the matrix
			for(int i = 0; i < matrix.size(); i++) {
				HashMap<String, Double> rankclusters = new HashMap<String, Double>();
				ArrayList<Double> currentvector = matrix.get(i).returntfidfvaluesforrow();
				String foldername = matrix.get(i).returnfoldername();
				String articlename = matrix.get(i).returnarticlename();
				String articlefolder = articlename +"_" + foldername;
				for(int j = 0; j < K; j++) {
					ArrayList<Double> centroid = listofclusters.returncluster(j).returncentroid();
					String clustername = listofclusters.returncluster(j).returnclusternumber();
					Double similarity = calccosinesimilarity.CosineSimilarityCalc(centroid, currentvector);
					
					rankclusters.put(clustername, similarity);
				}

				SortbyValue sortthisplz = new SortbyValue();
				TreeMap<String, Double> sortedclusterrank = sortthisplz.sortMapByValuestringdouble(rankclusters);
				String intothiscluster = sortedclusterrank.firstKey();

				// add the article to a cluster
				addarticlestocluster(rankclusters, currentvector, articlefolder, intothiscluster);
			}

			iterations++;
		}

		numberofiterations(K, iterations);
	}

	private void numberofiterations(Integer K, int iterations) {
		System.out.println("the number of iterations were: " + iterations);
		for(int i = 0; i < K; i++) {
			System.out.println(listofclusters.returncluster(i).returnclusternumber());
			System.out.println(listofclusters.returncluster(i).returnclusterlist());
		}
	}

	private void addarticlestocluster(HashMap<String, Double> rankclusters, ArrayList<Double> currentvector,
			String articlefolder, String intothiscluster) {
		for(int j = 0; j < rankclusters.size(); j++) {
			String clustername = listofclusters.returncluster(j).returnclusternumber();
			if(intothiscluster.equals(clustername)) {
				listofclusters.returncluster(j).addtoclusterlist(articlefolder);
				listofclusters.returncluster(j).addtovectorsinthiscluster(currentvector);
			}
		}
	}

	private void resetclusters(Integer K) {
		for(int j = 0; j < K; j++) {
			// erase previous cluster
			listofclusters.returncluster(j).resetpreviousclusterlist();
			// copy the current cluster into the previous cluster
			listofclusters.returncluster(j).copyclusters();
			listofclusters.returncluster(j).resetclusterlist();
		}
	}

	private void establishclusters(List<tfidfvalues> matrix, ArrayList<String> centroidarticle) {
		int count = 0;
		for(String articlefolder : centroidarticle) {
			for(int i = 0; i < matrix.size(); i++) {
				String articlename = matrix.get(i).returnarticlename();
				String foldername = matrix.get(i).returnfoldername();
				String articlefoldercomp = articlename + "_" + foldername;

				if(articlefolder.equals(articlefoldercomp)) {
					ArrayList<Double> centroid = matrix.get(i).returntfidfvaluesforrow();
					String clustername = "Cluster_" + (count+1);
					Cluster clusters = new Cluster(clustername, centroid);
					listofclusters.addtolistofclusters(clusters);
					System.out.println(clusters.returnclusternumber());
					count++;
				}
			}
		}
	}

	public void kmeanscosinepick3(List<tfidfvalues> matrix) {
		Scanner scanner1 = new Scanner(System.in);
		System.out.print("Enter K: ");
		Integer K = scanner1.nextInt();
		clustersize = K;

		HashSet<String> listofthreefolders = userthreefolderinput(matrix, K);

		CosineSimilarity calccosinesimilarity = new CosineSimilarity();
	
		int iterations = 0;
		while(!listofclusters.sameclusterdiff() && iterations <= 100) {
			for(int i = 0 ; i < clustersize; i++) {
				listofclusters.returncluster(i).calcnewcentroid();
			}
			
			resetclusters(clustersize);

			// go through all the articles in the matrix
			for(int i = 0; i < matrix.size(); i++) {
				HashMap<String, Double> rankclusters = new HashMap<String, Double>();
				String foldername = matrix.get(i).returnfoldername();
				if(listofthreefolders.contains(foldername) == true) {
					ArrayList<Double> currentvector = matrix.get(i).returntfidfvaluesforrow();
				
					String articlename = matrix.get(i).returnarticlename();
					String articlefolder = articlename +"_" + foldername;
					for(int j = 0; j < K; j++) {
	
						ArrayList<Double> centroid = listofclusters.returncluster(j).returncentroid();
						String clustername = listofclusters.returncluster(j).returnclusternumber();
						Double similarity = calccosinesimilarity.CosineSimilarityCalc(centroid, currentvector);
						
						rankclusters.put(clustername, similarity);
					}

					SortbyValue sortthisplz = new SortbyValue();

					TreeMap<String, Double> sortedclusterrank = sortthisplz.sortMapByValuestringdouble(rankclusters);
					String intothiscluster = sortedclusterrank.firstKey();


					addarticlestocluster(rankclusters, currentvector, articlefolder, intothiscluster);
				}
			}

			iterations++;
		}
		
		numberofiterations(K, iterations);
	}		

	public void kmeanseuclidean(List<tfidfvalues> matrix) {
		Scanner scanner1 = new Scanner(System.in);
		System.out.print("Enter K: ");
		Integer K = scanner1.nextInt();
		clustersize = K;

		// allclusters listofclusters = new allclusters();
		listofclusters.setnumberofclusters(K);


		Simvaluesforone getK = new Simvaluesforone();
		ArrayList<String> centroidarticle = getK.returnrandomcentroid(K, matrix);//getK.returntopeucK(K, matrix);

		establishclusters(matrix, centroidarticle);

		EuclideanDistance calceucsimilarity = new EuclideanDistance();
		// while the clusters don't match the previous cluster 
		// at the beginning it won't be a match anyways because previouscluster and currentcluster won't be a match anyways
		int iterations = 0;
		while(listofclusters.sameclusterdiff() == false && iterations <= 100) {
			//System.out.println("we tested false");
			// calc new centroid...if there are no other vectors in the vector list, then it won't do anything
			for(int i = 0 ; i < K ; i++) {
				listofclusters.returncluster(i).calcnewcentroid();
			}
			
			resetclusters(K);

			// go through all the articles in the matrix
			for(int i = 0; i < matrix.size(); i++) {
				HashMap<String, Double> rankclusters = new HashMap<String, Double>();
				ArrayList<Double> currentvector = matrix.get(i).returntfidfvaluesforrow();
				String foldername = matrix.get(i).returnfoldername();
				String articlename = matrix.get(i).returnarticlename();
				String articlefolder = articlename +"_" + foldername;
				for(int j = 0; j < K; j++) {
					ArrayList<Double> centroid = listofclusters.returncluster(j).returncentroid();
					String clustername = listofclusters.returncluster(j).returnclusternumber();
					Double similarity = calceucsimilarity.EuclideanDistanceCalc(centroid, currentvector);
					
					rankclusters.put(clustername, similarity);
				}

				SortbyValue sortthisplz = new SortbyValue();
				TreeMap<String, Double> sortedclusterrank = sortthisplz.sortMapByValuestringdoubleplus(rankclusters);
				String intothiscluster = sortedclusterrank.firstKey();

				// add the article to a cluster
				addarticlestocluster(rankclusters, currentvector, articlefolder, intothiscluster);
				
			}
			
			iterations++;
		}
		numberofiterations(K, iterations);
	}

	public void kmeanseuclideanpick3(List<tfidfvalues> matrix) {
		Scanner scanner1 = new Scanner(System.in);
		System.out.print("Enter K: ");
		Integer K = scanner1.nextInt();
		clustersize = K;

		HashSet<String> listofthreefolders = userthreefolderinput(matrix, K);

		EuclideanDistance calceucsimilarity = new EuclideanDistance();
		// while the clusters don't match the previous cluster 
		// at the beginning it won't be a match anyways because previouscluster and currentcluster won't be a match anyways
		int iterations = 0;
		while(!listofclusters.sameclusterdiff() && iterations <= 100) {
			//System.out.println("we tested false");
			// calc new centroid...if there are no other vectors in the vector list, then it won't do anything
			for(int i = 0 ; i < clustersize ; i++) {
				listofclusters.returncluster(i).calcnewcentroid();
			}
			
			resetclusters(clustersize);

			// go through all the articles in the matrix
			for(int i = 0; i < matrix.size(); i++) {
				HashMap<String, Double> rankclusters = new HashMap<String, Double>();
				ArrayList<Double> currentvector = matrix.get(i).returntfidfvaluesforrow();
				String foldername = matrix.get(i).returnfoldername();

				if(listofthreefolders.contains(foldername) == true) {
					String articlename = matrix.get(i).returnarticlename();
					String articlefolder = articlename +"_" + foldername;
					for(int j = 0; j < K; j++) {
						ArrayList<Double> centroid = listofclusters.returncluster(j).returncentroid();
						String clustername = listofclusters.returncluster(j).returnclusternumber();
						Double similarity = calceucsimilarity.EuclideanDistanceCalc(centroid, currentvector);
						
						rankclusters.put(clustername, similarity);
					}

					SortbyValue sortthisplz = new SortbyValue();
					TreeMap<String, Double> sortedclusterrank = sortthisplz.sortMapByValuestringdoubleplus(rankclusters);
					//System.out.println(sortedclusterrank);
					String intothiscluster = sortedclusterrank.firstKey();
					//System.out.println(intothiscluster);

					addarticlestocluster(rankclusters, currentvector, articlefolder, intothiscluster);
				}
			}

			iterations++;
		}
		

		numberofiterations(K, iterations);
	}

	private HashSet<String> userthreefolderinput(List<tfidfvalues> matrix, Integer K) {
		Scanner scanner2 = new Scanner(System.in);
		System.out.print("Input folders of your choice, separated by commas (ex. C1, C2, C3): ");
		String threefolders = scanner2.nextLine();

		List <String> listofthreefolders2 =  Arrays.asList(threefolders.split(", "));
		HashSet<String> listofthreefolders = new HashSet<String>();

		for(String folder : listofthreefolders2) {
			listofthreefolders.add(folder);
		}

		
		Integer total = inputtotalarticlesinfolders();

		listofclusters.setnumberofclusters(K);

		Simvaluesforone getK = new Simvaluesforone();
		ArrayList<String> centroidarticle = getK.returnrandomcentroidselect(K, total, matrix);

		establishclusters(matrix, centroidarticle);
		return listofthreefolders;
	}

	private Integer inputtotalarticlesinfolders() {
		Scanner scanner3 = new Scanner(System.in);
		System.out.println("Input total number of aricles in those folders");
		System.out.println("For reference: (C1, 8), (C2, 8), (C3, 4), (C4, 8), (C5, 13), (C6, 5)");
		System.out.println("               (C7, 8), (C8, 10), (C9, 4), (C10, 18), (C11, 8)");
		System.out.println("               (C12, 10), (C13, 7), (C14, 5), (C15, 6)");
		Integer total = scanner3.nextInt();
		
		return total;
	}

	public Allclusters returnlistofclusters() {
		return listofclusters;
	}	

	public Integer returnclustersize() {
		return clustersize;
	}
}
