package TextAnalytics;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.*;
import java.io.*;

public class StanfordNLP {
	
	public LinkedList<String> nlponthisfile(File thisarticle) {
		LinkedList<String> listoftokens = new LinkedList<String>();
		//System.out.println("we are here1");
		String nowstring = doctostring(thisarticle);
//		System.out.println("we are here2");
		//System.out.println(nowstring);
		
		listoftokens = nlpthetext(nowstring);
		
		return listoftokens;
	}
	
	public String doctostring(File thisarticle) {
		String articleinastring = null;
//		System.out.println("we are here3");
		try {
//			System.out.println("we are here 4");
			Scanner Readertxt = new Scanner(new BufferedReader(new FileReader(thisarticle))).useDelimiter("\n");
			String paragraph;
			StringBuilder everything = new StringBuilder();
			
			while(Readertxt.hasNext()) {
//				System.out.println("we are here5");
//				System.out.println("Readertxt has next");
				paragraph = Readertxt.nextLine();
//				System.out.println("this is the paragraph: " + paragraph);
				everything.append(paragraph);
				everything.append(" ");
			}
			
			articleinastring = everything.toString();
//			System.out.println(articleinastring);
			
			Readertxt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		System.out.println(articleinastring);
		return articleinastring;
	}
	
	public LinkedList<String> nlpthetext(String article) {
		LinkedList<String> alltokensfromthisfile = new LinkedList<String>();
		HashSet<String> stopwords = new HashSet<String>();
		
		ReadFile stopwordfile = new ReadFile();
		stopwords = stopwordfile.readstopwords();
		
		Properties props = new Properties();
		
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		Annotation document = new Annotation(article);

		pipeline.annotate(document);

		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		
		StringBuffer currentNER = new StringBuffer();
		String newnerword;
		StringBuffer previousNERTAG = new StringBuffer();
		
		//System.out.println("we are here");
		for(CoreMap sentence: sentences) {
			for (CoreLabel token : sentence.get(TokensAnnotation.class)){
			    	// this is the text of the token
			    	String word = token.get(TextAnnotation.class);
//			    	System.out.println("the word is: " + word);
			    // this is the lemma of the token
			    String lemma = token.get(LemmaAnnotation.class);
//			    System.out.println("lemma of this word is: " + lemma);
			    // this is the POS tag of the token
			    String pos = token.get(PartOfSpeechAnnotation.class);
//			    System.out.println("part of speach of this word is: " + pos);
			    // this is the NER label of the token
			    String ne = token.get(NamedEntityTagAnnotation.class);
//			    System.out.println("NER tag of this word is: " + ne);
		
			    String pattern = "\\p{Punct}";
			    // if(!ne.equals("NUMBER")) {
			    	if(ne.equals("ORGANIZATION")||
			    		ne.equals("PERSON")||
			    		ne.equals("LOCATION")||
			    		ne.equals("MISC")||
			    		ne.equals("MONEY")||
			    		ne.equals("ORDINAL")||
			    		ne.equals("PERCENT")||
			    		ne.equals("DATE")||
			    		ne.equals("TIME")||
			    		ne.equals("DURATION")||
			    		ne.equals("SET")){
				    		
			    		if(ne.equals(previousNERTAG.toString())) {
			    			if(!word.equals("``") && !word.equals("''") && !word.equals("--") && !word.equals("-RSB-") && !word.equals("-LSB-") && !word.equals("-RRB-") && !word.equals("-LRB-")) {
			    				word = word.trim();
				    			word = word.replaceAll("[\\s+]","");
				    			currentNER.append(" " + word);
				    			previousNERTAG = previousNERTAG.replace(0,  previousNERTAG.length(), ne);
			    			}
			    		} else {
			    			if(currentNER.length() != 0) {
			    				if(!word.equals("``") && !word.equals("''") && !word.equals("--") && !word.equals("-RSB-") && !word.equals("-LSB-") && !word.equals("-RRB-") && !word.equals("-LRB-")) {
			    					newnerword = currentNER.toString();
				    				newnerword = newnerword.trim();
//				    				System.out.println("newnerword is: " + newnerword);
				    				alltokensfromthisfile.add(newnerword);
				    				currentNER.setLength(0);
				    				previousNERTAG = previousNERTAG.replace(0,  previousNERTAG.length(), ne);
				    				word = word.trim();
				    				word = word.replaceAll("[\\s+]", "");
				    				currentNER.append(word);
			    				}
			    			}
			    			else {
			    				if(!word.equals("``") && !word.equals("''") && !word.equals("--") && !word.equals("-RSB-") && !word.equals("-LSB-") && !word.equals("-RRB-") && !word.equals("-LRB-")) {
			    					previousNERTAG = previousNERTAG.replace(0,  previousNERTAG.length(), ne);
				    				word = word.trim();
				    				word = word.replaceAll("[\\s+]", "");
				    				currentNER.append(" " + word);
			    				}
			    			}
			    		}   		
			    } else {
			    		if(!ne.equals("NUMBER")) {
			    			
				    		if(!lemma.equals(pattern) && !lemma.equals("...") && !lemma.equals("`") && !lemma.equals("``") && !lemma.equals("--") && !lemma.equals("''") && !pos.equals("POS")) {
				    			if(currentNER.length() != 0) {
				    				newnerword = currentNER.toString();
				    				newnerword = newnerword.trim();
//				    				System.out.println("newnerword is: " + newnerword);
				    				alltokensfromthisfile.add(newnerword);
				    				currentNER.setLength(0);
				    				previousNERTAG.setLength(0);	
				    			}
				    			
				    			lemma = lemma.toLowerCase();
				    			
				    			if(!lemma.equals("-lrb-") && !lemma.equals("-rrb-") && !lemma.equals("-lsb-") && !lemma.equals("-rsb-")) {
				    				if(lemma.length() != 1) {
				    					if(!stopwords.contains(lemma)) {
//				    						System.out.println("the lemma of the word being added is:" + lemma);
				    						lemma = lemma.trim();
				    						lemma = lemma.replaceAll("[\\s+]", "");
				    						alltokensfromthisfile.add(lemma);
				    					}
				    				}
				    			}
				    		}
			    		}
			    } 
			}
		}
		
		return alltokensfromthisfile;
	}
	
}
