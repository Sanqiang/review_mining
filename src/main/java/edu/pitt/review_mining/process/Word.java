package edu.pitt.review_mining.process;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

import edu.pitt.review_mining.graph.Edge;
import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.graph.Node;
import edu.pitt.review_mining.utility.DependencyType;
import edu.pitt.review_mining.utility.Helper;
import edu.pitt.review_mining.utility.Module;
import edu.pitt.review_mining.utility.PartOfSpeech;
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
		Graph graph = new Graph();
		DependencyParser parser = Module.getInst().getDependencyParser();
		MaxentTagger tagger = Module.getInst().getTagger();
		DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(doc));
		System.out.println("Start processing: " + doc);
		for (List<HasWord> sentence : tokenizer) {
			List<TaggedWord> tagged = tagger.tagSentence(sentence);
			GrammaticalStructure gs = parser.predict(tagged);

			for (TypedDependency typed_dependence : gs.allTypedDependencies()) {
				int idx_gov = typed_dependence.gov().index() - 1;
				int idx_dep = typed_dependence.dep().index() - 1;
				PartOfSpeech pos_gov = PartOfSpeech.OTHER;
				if (idx_gov >= 0) {
					pos_gov = Helper.mapPartOfSpeech(tagged.get(idx_gov).tag());
				}
				PartOfSpeech pos_dep = PartOfSpeech.OTHER;
				if (idx_dep >= 0) {
					pos_dep = Helper.mapPartOfSpeech(tagged.get(idx_dep).tag());
				}

				String lemma_gov = typed_dependence.gov().value();
				String lemma_dep = typed_dependence.dep().value();
				Node gov = graph.createNode(pos_gov, lemma_gov, idx_gov, 0);
				Node dep = graph.createNode(pos_dep, lemma_dep, idx_dep, 0);

				DependencyType dependency_type = Helper.mapRelationTypes(typed_dependence.reln().getShortName());
				graph.createEdge(gov, dep, dependency_type);
			}
		}

		// find center aspect word
		ArrayList<Node> candidates_nodes = getCenterWordCandidates(graph);
		for (Node node : candidates_nodes) {
			System.out.println("Center word is: " + node.getLemma() + ", InComing: " + node.getIncomingEdges().size()
					+ " OutComing: " + node.getOutcomingEdges().size());
		}

	}

	public ArrayList<Node> getCenterWordCandidates(Graph graph) {
		ArrayList<Node> candidates_nodes = new ArrayList<>();
		Collection<Node> nodes = graph.getNodes();
		for (Node node : nodes) {
			if (node.getPOS() == PartOfSpeech.NOUN) {
				candidates_nodes.add(node);
			}
		}
		Collections.sort(candidates_nodes, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return (o2.getIncomingEdges().size() + o2.getOutcomingEdges().size())
						- (o1.getIncomingEdges().size() + o1.getOutcomingEdges().size());
			}
		});
		return candidates_nodes;
	}
}
