package edu.pitt.review_mining.process;

import java.io.StringReader;
import java.util.List;

import edu.pitt.review_mining.constant.Module;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;

public class Word {

	private StanfordCoreNLP pipeline;

	public Word() {
	}

	public void processDocument(String doc) {

		DependencyParser parser = Module.getInst().getDependencyParser();
		MaxentTagger tagger = Module.getInst().getTagger();
		DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(doc));
		for (List<HasWord> sentence : tokenizer) {
			List<TaggedWord> tagged = tagger.tagSentence(sentence);
			GrammaticalStructure gs = parser.predict(tagged);
			for (TypedDependency typed_dependence : gs.allTypedDependencies()) {
				System.out.println(typed_dependence.dep().value());
				System.out.println(typed_dependence.gov().value());
				System.out.println(typed_dependence.reln().getShortName());
				System.out.println();
				System.out.println();
			}
		}
	}
}
