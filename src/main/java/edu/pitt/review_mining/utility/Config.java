package edu.pitt.review_mining.utility;

import edu.stanford.nlp.parser.nndep.DependencyParser;

public class Config {
	// path
	public final static String PATH_TEXT = "r/B000CNB4LE_sorted.txt";
	public final static String PATH_WEIGHT = "r/weight.txt";

	// for word2vec
	@Deprecated
	public final static String PATH_AMAZON_REVIEW = "C:/Downloads/complete.json";
	@Deprecated
	public final static String PATH_WORD2VEC_MODEL = "w2v.model";

	// for GloVe
	public final static String PATH_COMMON_300D = "C:/git/vectors.840B.300d.txt";
	public final static String PATH_WIKI_300D = "C:/git/glove.6B.300d.txt";
	public final static String PATH_INDEX = "C:/git/";

	// tagger
	public final static String PATH_TAGGER = "edu/stanford/nlp/models/pos-tagger/english-left3words/english-left3words-distsim.tagger";
	public final static String CONFIG_DEPENDENCY_PARSER = DependencyParser.DEFAULT_MODEL;
	public final static String CONFIG_PCFG_PARSER = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";

	// for CFG
	// reference tags: https://gist.github.com/nlothian/9240750
	public final static String[] PENNTREE_CLAUSE_TAGS = { "S", "SBAR", "SBARQ", "SINV", "SQ" };
	public final static String[] TAGS_FILTER_OUT = { "PP", "INTJ" };

	// for report
	public final static String PATH_REPORT_GRAPH  = "repot_graph.json";
	public final static String PATH_REPORT_ASPECT  = "repot_aspect.json";
	public final static double[] WEIGHT_FORWARD_BACKWARD = { 0.50049, 0.25024, 0.12512, 0.062561, 0.031281, 0.01564, 0.0078201,
			0.0039101, 0.001955, 0.00097752 };
}
