package edu.pitt.review_mining.constant;

import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class Module {
	// singular set up
	private static Module _inst = null;
	private static Object _lock = new Object();

	public static Module getInst() {
		synchronized (_lock) {
			if (_inst == null) {
				_inst = new Module();
			}
		}
		return _inst;
	}

	// nested module
	private MaxentTagger _tagger = null;
	private DependencyParser _parser = null;

	private Module() {
		_tagger = new MaxentTagger(Config.PATH_TAGGER);
		_parser = DependencyParser.loadFromModelFile(Config.CONFIG_DEPENDENCY_PARSER);
	}

	public MaxentTagger getTagger() {
		return getInst()._tagger;
	}

	public DependencyParser getDependencyParser() {
		return getInst()._parser;
	}
}
