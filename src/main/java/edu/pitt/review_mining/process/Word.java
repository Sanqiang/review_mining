package edu.pitt.review_mining.process;

import java.io.StringReader;
import java.util.List;
import java.util.Properties;

import edu.pitt.review_mining.constant.Module;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.trees.TypedDependency;
import edu.stanford.nlp.util.CoreMap;

public class Word {

	private StanfordCoreNLP pipeline;

	public Word() {
	}

	public void processDocument(String doc) {
		Properties props = new Properties();
		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		Annotation document = new Annotation(doc);
		pipeline.annotate(document);
		List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				// this is the text of the token
				String word = token.get(TextAnnotation.class);
				// this is the POS tag of the token
				String pos = token.get(PartOfSpeechAnnotation.class);
				// this is the NER label of the token
				String ne = token.get(NamedEntityTagAnnotation.class);
			}
			// this is the parse tree of the current sentence
			Tree tree = sentence.get(TreeAnnotation.class);

			// this is the Stanford dependency graph of the current sentence
			SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
			
		}
		/*
		 * DependencyParser parser = Module.getInst().getDependencyParser();
		 * MaxentTagger tagger = Module.getInst().getTagger();
		 * DocumentPreprocessor tokenizer = new DocumentPreprocessor(new
		 * StringReader(doc)); for (List<HasWord> sentence : tokenizer) {
		 * List<TaggedWord> tagged = tagger.tagSentence(sentence);
		 * GrammaticalStructure gs = parser.predict(tagged);
		 * 
		 * for (int i = 0; i < tagged.size(); i++) {
		 * System.out.println(tagged.get(i)); }
		 * 
		 * for (TypedDependency typed_dependence : gs.allTypedDependencies()) {
		 * if ("nsubj".equals(typed_dependence.reln().getShortName())) {
		 * System.out.println(typed_dependence.dep().value());
		 * System.out.println(typed_dependence.gov().value());
		 * System.out.println(typed_dependence.reln().getShortName());
		 * System.out.println(); }
		 * 
		 * 
		 * } }
		 */
	}
}
