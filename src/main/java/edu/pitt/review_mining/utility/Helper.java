package edu.pitt.review_mining.utility;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import edu.stanford.nlp.trees.LabeledScoredTreeNode;
import edu.stanford.nlp.trees.Tree;

public class Helper {
	public static PartOfSpeech mapPartOfSpeech(String postag) {
		PartOfSpeech partOfSpeech = PartOfSpeech.OTHER;
		switch (postag) {
		case "NN":
		case "NNS":
		case "NNP":
		case "NNPS":
			partOfSpeech = PartOfSpeech.NOUN;
			break;
		case "VB":
		case "VBD":
		case "VBG":
		case "VBN":
		case "VBP":
		case "VBZ":
			partOfSpeech = PartOfSpeech.VERB;
			break;
		case "JJ":
		case "JJR":
		case "JJS":
			partOfSpeech = PartOfSpeech.ADJECTIVE;
			break;
		case "RB":
		case "RBR":
		case "RBS":
			partOfSpeech = PartOfSpeech.ADVERB;
			break;
		default:
			partOfSpeech = PartOfSpeech.OTHER;
			break;
		}
		return partOfSpeech;
	}

	public static DependencyType mapRelationTypes(String dependencyShortName) {
		DependencyType mappedRelType = DependencyType.OtherLocalType;
		switch (dependencyShortName) {
		case "neg":
			mappedRelType = DependencyType.Negative;
			break;
		case "nmod":
			mappedRelType = DependencyType.NounModifier;
			break;
		case "amod":
			mappedRelType = DependencyType.AdjectivalModifier;
			break;
		case "dep":
			mappedRelType = DependencyType.Dependent;
			break;
		case "nn":
			mappedRelType = DependencyType.NounCompoundModifier;
			break;
		case "nsubj":
			mappedRelType = DependencyType.NominalSubject;
			break;
		// case "dobj":
		// mappedRelType = DependencyType.DirectObject;
		// break;
		case "conj:and":
		case "compound":
		case "conj":
			mappedRelType = DependencyType.Conjunction;
			break;
		case "acomp":
		case "ccomp":
		case "xcomp":
		case "dobj":
		case "iobj":
		case "pobj":
			mappedRelType = DependencyType.Complement;
			break;
		default:
			mappedRelType = DependencyType.OtherLocalType;
			break;
		}
		return mappedRelType;
	}

	public static boolean isInArray(String target, String[] arr) {
		boolean is_in_array = false;
		for (String item : arr) {
			if (item.equals(target)) {
				is_in_array = true;
				break;
			}
		}
		return is_in_array;
	}

	public static String mapTreeSentence(Tree tree) {
		List<LabeledScoredTreeNode> leaves = tree.getLeaves();
		StringBuilder sb = new StringBuilder();
		for (LabeledScoredTreeNode leave : leaves) {
			sb.append(leave.value()).append(" ");
		}
		return sb.toString();
	}

	public static int setBit(int val, int pos, boolean flag) {
		if (flag) {
			val |= (1 << pos);
		}
		return val;
	}

	public static boolean getBit(int val, int pos) {
		return (val >> pos) % 2 > 0;
	}

	public static double getCosinSim2(HashMap<String, Double> map1, HashMap<String, Double> map2) {
		double sim = 0d, norm1 = 0d, norm2 = 0d;
		for (String key1 : map1.keySet()) {
			if (map2.containsKey(key1)) {
				sim += map1.get(key1) * map2.get(key1);
			}
			norm1 += map1.get(key1) * map1.get(key1);
		}
		for (String key2 : map2.keySet()) {
			norm2 += map2.get(key2) * map2.get(key2);
		}
		sim /= (Math.sqrt(norm1) * Math.sqrt(norm2));
		return sim;
	}
	
	public static double getCosinSim(HashMap<String, Double> map1, HashMap<String, Double> map2) {
		double sim = 0d, norm1 = 0d, norm2 = 0d, mean1 = 0d, mean2 = 0d;
		int count1 = 0, count2= 0;
		for (String key1 : map1.keySet()) {
			mean1 += map1.get(key1);
			++count1;
		}
		mean1 /= count1;
		for (String key2 : map2.keySet()) {
			mean2 += map2.get(key2);
			++count2;
		}
		mean2 /= count2;
		
		for (String key1 : map1.keySet()) {
			if (map2.containsKey(key1)) {
				sim += (map1.get(key1)-mean1) * (map2.get(key1)-mean2);
			}
			norm1 += (map1.get(key1)-mean1) * (map1.get(key1)-mean1);
		}
		for (String key2 : map2.keySet()) {
			norm2 += (map2.get(key2)-mean2) * (map2.get(key2) - mean2);
		}
		sim /= (Math.sqrt(norm1) * Math.sqrt(norm2));
		return sim;
	}
}
