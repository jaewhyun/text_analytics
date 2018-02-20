package calctfidf;
import TextAnalytics.SortbyValue;
import TextAnalytics.StanfordNLP;
import TextAnalytics.ReadFile;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;
import TextAnalytics.AlphanumComparator; 

public class Main {
	
	public static void main(String[] args) throws IOException{

		ReadFile readingfiles = new ReadFile();
		// String absolutepath = input;
		TreeMap<File, String> filebyfolder = new TreeMap<File, String>();
		filebyfolder = readingfiles.folderreader(args[0]);

		HashSet<String> listoffolders = readingfiles.returnlistoffoldernames();
		
		List<String> list = new ArrayList<String>(listoffolders);
		List<String> sortedlistoffolders = list.stream().sorted(new AlphanumComparator()).collect(Collectors.toList());
		
		Integer numberoffiles = readingfiles.returnnumberoffiles();
		
		DocTokens[] alldocsandtokens = new DocTokens[numberoffiles];

		StanfordNLP nlpstep = new StanfordNLP();
		
		Integer whereamI = 0;
		while(whereamI < numberoffiles) {
			for(Map.Entry<File, String> file : filebyfolder.entrySet()) {
				LinkedList<String> newtokenlist = nlpstep.nlponthisfile(file.getKey());
				String foldername = file.getValue();
				String articlename = file.getKey().getName();
				// https://codereview.stackexchange.com/questions/97800/removing-the-extension-from-a-filename
				articlename = articlename.substring(0, articlename.lastIndexOf("."));
				alldocsandtokens[whereamI] = new DocTokens();
				alldocsandtokens[whereamI].setarticlename(articlename);
				alldocsandtokens[whereamI].setfoldername(foldername);
				alldocsandtokens[whereamI].settokens(newtokenlist);
						
				whereamI++;
			}
		}

		TokenGenerator tokensperfolder = new TokenGenerator();
	
		for(String currentfolder : sortedlistoffolders) {
			for(int i = 0; i < numberoffiles; i++) {
				if(alldocsandtokens[i].returnfoldername().equals(currentfolder)) {
					tokensperfolder.combineallarticles(alldocsandtokens[i].returnlistoftokens(), currentfolder);
				}
			}
		}

		tokensperfolder.tokenswitch(alldocsandtokens);

		LinkedHashSet<String> alltokens = tokensperfolder.combinealltokens();

		ArrayList<String> monogram = tokensperfolder.returnmonogramset();
		ArrayList<String> bigram = tokensperfolder.returnbigramset();
		ArrayList<String> trigram = tokensperfolder.returntrigramset();
		ArrayList<String> fourgram = tokensperfolder.returnfourgramset();

		Printutil printthisplz = new Printutil();
		printthisplz.printngrams(monogram, bigram, trigram, fourgram);
		
		tfidfMatrix settingnumberofdocsfortoken = new tfidfMatrix();
		LinkedHashMap<String, Integer> numberofdocsfortoken = settingnumberofdocsfortoken.howmanydocshavethistoken(numberoffiles, alltokens, alldocsandtokens);
		LinkedHashSet<String> alltokens2 = settingnumberofdocsfortoken.cleantokenlist(alltokens, numberofdocsfortoken);

		tfidfMatrix[] matrix = new tfidfMatrix[numberoffiles];
		List<String> alltokens3 = new ArrayList<String>(alltokens2);

		
		printthisplz.printalltokens(numberoffiles, alldocsandtokens, numberofdocsfortoken, matrix, alltokens3);

		printthisplz.printtfidfmatrix(sortedlistoffolders, numberoffiles, matrix);
		
		SortbyValue sortthisplz = new SortbyValue();
		tfidfMatrix newmatrix = new tfidfMatrix();

		printthisplz.printtoptokens(sortedlistoffolders, numberoffiles, matrix, sortthisplz, newmatrix);

	}
	
}
