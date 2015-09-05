package edu.pitt.review_mining.utility;

public class Helper {
	public static PartOfSpeech mapPartOfSpeech(String postag){
		PartOfSpeech partOfSpeech=PartOfSpeech.OTHER;
		switch (postag) {
		case "NN":
		case "NNS":
		case "NNP":
		case "NNPS":
			partOfSpeech=PartOfSpeech.NOUN;
			break;
		case "VB":
		case "VBD":
		case "VBG":
		case "VBN":
		case "VBP":
		case "VBZ":
			partOfSpeech=PartOfSpeech.VERB;
			break;
		case "JJ":
		case "JJR":
		case "JJS":
			partOfSpeech=PartOfSpeech.ADJECTIVE;
			break;
		case "RB":
		case "RBR":
		case "RBS":
			partOfSpeech=PartOfSpeech.ADVERB;
			break;
		default:
			partOfSpeech=PartOfSpeech.OTHER;
			break;
		}
		return partOfSpeech;
	}
	
	public static DependencyType mapRelationTypes(String dependencyShortName){
		DependencyType mappedRelType=DependencyType.OTHER;
		switch (dependencyShortName) {
		case "amod":
			mappedRelType= DependencyType.AdjectivalModifier;
			break;
		case "nn":
			mappedRelType= DependencyType.NominalSubject;
			break;
		case "nsubj":
			mappedRelType= DependencyType.NominalSubject;
			break;
		case "dobj":
			mappedRelType= DependencyType.DirectObject;
			break;
		case "conj":
			mappedRelType= DependencyType.Conjunction;
			break;
		default:
			mappedRelType=DependencyType.OTHER;
			break;
		}
		return mappedRelType;
	}
}