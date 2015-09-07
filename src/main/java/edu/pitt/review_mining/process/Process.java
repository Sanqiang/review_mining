package edu.pitt.review_mining.process;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.net.ssl.HttpsURLConnection;

import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.graph.Node;
import edu.pitt.review_mining.utility.Config;
import edu.pitt.review_mining.utility.DependencyType;
import edu.pitt.review_mining.utility.Helper;
import edu.pitt.review_mining.utility.Module;
import edu.pitt.review_mining.utility.PartOfSpeech;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;

public class Process {

	public Process() {
	}

	public String preprocessSentence(String sentence) {
		StringBuilder sb = new StringBuilder();
		// format punctuation
		MaxentTagger tagger = Module.getInst().getTagger();
		List<List<HasWord>> sents = MaxentTagger.tokenizeText(new StringReader(sentence));
		for (List<HasWord> sent : sents) {
			List<TaggedWord> tagged_sent = tagger.tagSentence(sent);
			for (int i = 0; i < tagged_sent.size(); i++) {
				if (i + 2 < tagged_sent.size() && Helper.mapPartOfSpeech(tagged_sent.get(i+2).tag()) == PartOfSpeech.NOUN ) {
					String trigram = String.join(" ", tagged_sent.get(i).word(), tagged_sent.get(i + 1).word(),
							tagged_sent.get(i + 2).word());
					if (detectPhrase(trigram)) {
						sb.append(String.join("_", tagged_sent.get(i).word(), tagged_sent.get(i + 1).word(),
								tagged_sent.get(i + 2).word())).append(" ");
						i += 2;
						continue;
					}
				}
				if (i + 1 < tagged_sent.size() && Helper.mapPartOfSpeech(tagged_sent.get(i+1).tag()) == PartOfSpeech.NOUN) {
					String bigram = String.join(" ", tagged_sent.get(i).word(), tagged_sent.get(i + 1).word());
					if (detectPhrase(bigram)) {
						sb.append(String.join("_", tagged_sent.get(i).word(), tagged_sent.get(i + 1).word()))
								.append(" ");
						i += 1;
						continue;
					}
				}
				sb.append(tagged_sent.get(i).word()).append(" ");
			}
		}
		return sb.toString();
	}

	public boolean detectPhrase(String phrase) {
		phrase = phrase.replace(" ", "_");
		String url = "https://en.wikipedia.org/wiki/" + phrase;
		try {
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.getInputStream();
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	public Tree filterSentence(String sentence) {
		LexicalizedParser lp = Module.getInst().getLexicalizedParser();
		Tree tree = lp.parse(sentence);
		tree = tree.children()[0]; // skip ROOT tag
		Queue<Tree> queue_nodes = new LinkedList<>();
		for (int i = 0; i < tree.children().length; i++) {
			queue_nodes.add(tree.children()[i]);
		}
		while (true) {
			Tree child_tree = queue_nodes.poll();
			for (int i = 0; i < child_tree.children().length; i++) {
				if (Helper.isInArray(child_tree.children()[i].value(), Config.TAGS_FILTER_OUT)) {
					child_tree.removeChild(i);
				} else {
					queue_nodes.add(child_tree.children()[i]);
				}
			}
			if (queue_nodes.size() == 0) {
				break;
			}
		}
		return tree;
	}

	public ArrayList<Tree> splitTree(Tree tree) {
		ArrayList<Tree> trees = new ArrayList<>();
		boolean is_simple_sent = true;
		for (Tree child_tree : tree.children()) {
			if (Helper.isInArray(child_tree.value(), Config.PENNTREE_CLAUSE_TAGS)) {
				is_simple_sent = false;
			}
			trees.add(child_tree);
		}
		if (is_simple_sent) {
			trees.clear();
			trees.add(tree);
		}
		return trees;
	}

	public void processClause(String clause) {
		Graph graph = new Graph();
		DependencyParser parser = Module.getInst().getDependencyParser();
		MaxentTagger tagger = Module.getInst().getTagger();
		DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(clause));
		System.out.println("Parsing : " + clause);
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
