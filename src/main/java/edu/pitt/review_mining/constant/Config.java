package edu.pitt.review_mining.constant;

import edu.stanford.nlp.parser.nndep.DependencyParser;

public class Config {
	//path
	public final static String PATH_TEXT = "data\the-halal-guys-new-york-2_text.txt";
	
	//tagger
	public final static String PATH_TAGGER = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";
	public final static String CONFIG_DEPENDENCY_PARSER = DependencyParser.DEFAULT_MODEL;
}
