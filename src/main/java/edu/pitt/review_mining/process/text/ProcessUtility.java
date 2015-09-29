package edu.pitt.review_mining.process.text;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.net.ssl.HttpsURLConnection;

import edu.pitt.review_mining.graph.Edge;
import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.graph.Node;
import edu.pitt.review_mining.utility.Config;
import edu.pitt.review_mining.utility.DependencyType;
import edu.pitt.review_mining.utility.Helper;
import edu.pitt.review_mining.utility.Module;
import edu.pitt.review_mining.utility.PartOfSpeech;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TypedDependency;

public class ProcessUtility {

	private GloVe _glove = null;
	private Graph _graph = null;

	public ProcessUtility() {
		_glove = new GloVe();
		_graph = new Graph();
	}

	public Graph getGraph() {
		return this._graph;
	}

	// CLI function
	public void processReviews(String review, int review_id, double review_weight) {
		String[] paragraphs = review.split("\n");
		for (String paragraph : paragraphs) {
			if (paragraph.length() > 0) {
				ArrayList<String> sentences = segSentence(paragraph);
				for (int i = 0; i < sentences.size(); i++) {
					int pos_feature = 0;
					if (i == 0) {
						pos_feature = Helper.setBit(pos_feature, 0, true);
					} else if (i == sentences.size() - 1) {
						pos_feature = Helper.setBit(pos_feature, 1, true);
					}
					processSentence(sentences.get(i).toLowerCase(), review_id, pos_feature, review_weight);
				}
			}
		}
	}

	// process the sentence CLI
	public void processSentence(String sentence, int review_id, int pos_feature, double review_weight) {
		ArrayList<Node> candidates_nodes = new ArrayList<>();
		// does detect phrase for now, use dependency parser of compound
		// relationship
		// sentence = preprocessSentence(sentence);
		sentence = filterSentence(sentence);
		// split tree break dependency parser so deprecated
		// ArrayList<Tree> trees = splitTree(tree);
		// for (int i = trees.size() - 1; i >= 0; i--) {
		// Tree child_tree = trees.get(i);
		// // ArrayList<Node> candidates_temp_nodes
		// //
		// =getCenterWordCandidatesFromGraph(Helper.mapTreeSentence(child_tree));
		// ArrayList<Node> candidates_temp_nodes =
		// getCenterWordCandidatesFromSimMeasure(
		// Helper.mapTreeSentence(child_tree), "food");
		// candidates_nodes.addAll(candidates_temp_nodes);
		// }
		// candidates_nodes.addAll(getCenterWordCandidatesFromGraph(Helper.mapTreeSentence(tree)));
		// so instead use
		generateDependencyGraph(sentence, review_id, pos_feature, review_weight);
		// generateDependencyGraph(sentence, review_id, pos_feature);
	}

	public ArrayList<String> segSentence(String paragraph) {
		ArrayList<String> sentences = new ArrayList<>();
		DocumentPreprocessor dp = new DocumentPreprocessor(new StringReader(paragraph));
		for (List<HasWord> sentence : dp) {
			String sentenceString = Sentence.listToString(sentence);
			sentences.add(sentenceString.toString());
		}
		return sentences;
	}

	public String filterSentence(String sentence) {
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

		if (Helper.mapTreeSentence(tree).length() * 2 <= sentence.length()) {
			return sentence;
		} else {
			return Helper.mapTreeSentence(tree);
		}
	}

	// generate graph of dependency relation
	public void generateDependencyGraph(String clause, int review_id, int pos_feature, double review_weight) {
		Graph local_graph = new Graph();
		DependencyParser parser = Module.getInst().getDependencyParser();
		MaxentTagger tagger = Module.getInst().getTagger();
		DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(clause));
		// build up the graph, informed with POS tag and dependency relationship
		for (List<HasWord> token_part : tokenizer) {
			List<TaggedWord> tagged = tagger.tagSentence(token_part);
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
				Node gov = local_graph.createNode(pos_gov, lemma_gov, review_id, idx_gov);
				Node dep = local_graph.createNode(pos_dep, lemma_dep, review_id, idx_dep);

				DependencyType dependency_type = Helper.mapRelationTypes(typed_dependence.reln().getShortName());
				local_graph.createEdge(gov, dep, dependency_type, review_id, idx_gov, pos_feature, review_weight);
			}
		}

		// refine rule
		for (Node node : local_graph.getNodes()) {
			if (node.getPOS() == PartOfSpeech.VERB) {
				for (Edge edge : node.getOutcomingEdges()) {
					Node another_node = edge.getOtherNode(node);
					// DependencyType.XComp : food smells good.
					if (edge.getDependencyType() == DependencyType.Complement
							&& another_node.getPOS() == PartOfSpeech.ADJECTIVE) {
						Node node_global = this._graph.createNode(node);
						Node another_node_global = this._graph.createNode(another_node);
						this._graph.createEdge(node_global, another_node_global, DependencyType.XComplement, review_id,
								node.getSentenceLoc(), pos_feature, review_weight);
					}
				}
			}

			if (node.getPOS() == PartOfSpeech.NOUN) {
				// for out coming edges
				for (Edge edge : node.getOutcomingEdges()) {
					Node another_node = edge.getOtherNode(node);
					// DependencyType.SingleAmod : red food is good.
					if (edge.getDependencyType() == DependencyType.AdjectivalModifier
							&& another_node.getPOS() == PartOfSpeech.ADJECTIVE) {
						Node node_global = this._graph.createNode(node);
						Node another_node_global = this._graph.createNode(another_node);
						this._graph.createEdge(node_global, another_node_global, DependencyType.SingleAmod, review_id,
								node.getSentenceLoc(), pos_feature, review_weight);
					}

					// DependencyType.ConjAndComp : The chicken and rice with
					// white sauce is delicious. chicken rice is delicious.
					if (edge.getDependencyType() == DependencyType.Conjunction
							&& another_node.getPOS() == PartOfSpeech.NOUN) {
						Node node_global = this._graph.createNode(node);
						Node another_node_global = this._graph.createNode(another_node);
						this._graph.createEdge(node_global, another_node_global, DependencyType.ConjAndComp, review_id,
								node.getSentenceLoc(), pos_feature, review_weight);
					}
				}

				// for in coming edges
				for (Edge edge : node.getIncomingEdges()) {
					Node another_node = edge.getOtherNode(node);
					// DependencyType.AmodSubj chicken is delicious food.
					if (edge.getDependencyType() == DependencyType.NominalSubject
							&& another_node.getPOS() == PartOfSpeech.NOUN) {
						for (Edge edge2 : another_node.getOutcomingEdges()) {
							Node another_node2 = edge2.getOtherNode(another_node);
							if (edge2.getDependencyType() == DependencyType.AdjectivalModifier
									&& another_node2.getPOS() == PartOfSpeech.ADJECTIVE) {
								Node node_global = this._graph.createNode(node);
								Node another_node_global = this._graph.createNode(another_node2);
								this._graph.createEdge(node_global, another_node_global, DependencyType.AmodSubj,
										review_id, node.getSentenceLoc(), pos_feature, review_weight);
							}
						}
					}

					// DependencyType.SingleSubj food is delicious.
					if (edge.getDependencyType() == DependencyType.NominalSubject
							&& another_node.getPOS() == PartOfSpeech.ADJECTIVE) {
						Node node_global = this._graph.createNode(node);
						Node another_node_global = this._graph.createNode(another_node);
						this._graph.createEdge(node_global, another_node_global, DependencyType.SingleSubj, review_id,
								node.getSentenceLoc(), pos_feature, review_weight);
					}
				}
			}
		}
	}

	// preprocess: tokenize by tagger / phrase detection by xx xx NOUN / xx NOUN
	// with wikipedia
	@Deprecated
	public String preprocessSentence(String sentence) {
		StringBuilder sb = new StringBuilder();
		// format punctuation and phrase
		MaxentTagger tagger = Module.getInst().getTagger();
		List<List<HasWord>> sents = MaxentTagger.tokenizeText(new StringReader(sentence));
		for (List<HasWord> sent : sents) {
			List<TaggedWord> tagged_sent = tagger.tagSentence(sent);
			for (int i = 0; i < tagged_sent.size(); i++) {
				// detect xx NOUN phrase
				if (i + 2 < tagged_sent.size()
						&& Helper.mapPartOfSpeech(tagged_sent.get(i + 2).tag()) == PartOfSpeech.NOUN) {
					String trigram = String.join(" ", tagged_sent.get(i).word(), tagged_sent.get(i + 1).word(),
							tagged_sent.get(i + 2).word());
					if (detectPhrase(trigram)) {
						sb.append(String.join("_", tagged_sent.get(i).word(), tagged_sent.get(i + 1).word(),
								tagged_sent.get(i + 2).word())).append(" ");
						i += 2;
						continue;
					}
				}
				// detect xx xx NOUN phrase
				if (i + 1 < tagged_sent.size()
						&& Helper.mapPartOfSpeech(tagged_sent.get(i + 1).tag()) == PartOfSpeech.NOUN) {
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

	// using wikipedia for phrase detect
	// one-layer split tree
	@Deprecated
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

	@Deprecated
	public boolean detectPhrase(String phrase) {
		return detectPhrase(phrase, false, true);
	}

	// using wikipedia for phrase detect
	@Deprecated
	public boolean detectPhrase(String phrase, boolean allow_wiki_redirect, boolean allow_transfor_lowercase) {
		String url = "https://en.wikipedia.org/wiki/" + phrase.replace(" ", "_");
		try {
			URL obj = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
			con.setInstanceFollowRedirects(true);
			con.setRequestMethod("GET");
			// if there is no such word, (no redirect) throw
			// FileNotFoundException
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			// wiki will redirect the page: sample is
			// https://en.wikipedia.org/wiki/new_york_pizza
			// if we not allow wiki redirect, we need to check whether redirect
			// happens for lower case or not
			if (allow_wiki_redirect) {
				return true;
			} else if (!allow_wiki_redirect && allow_transfor_lowercase) {
				String line = "";
				while ((line = in.readLine()) != null) {
					// access to <title> line
					if (line.indexOf("<title>") == 0) {
						String title = line.substring(line.indexOf("<title>") + 7, line.indexOf(" - Wikipedia"));
						if (allow_transfor_lowercase) {
							title = title.toLowerCase();
							phrase = phrase.toLowerCase();
						}
						if (title.equals(phrase)) {
							return true;
						} else {
							return false;
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	// filter out unimportant (Config.TAGS_FILTER_OUT) CFG parts
	@Deprecated
	public ArrayList<Node> getCenterWordCandidatesFromSimMeasure(String clause, String target_word) {
		ArrayList<Node> candidates_nodes = new ArrayList<>();
		MaxentTagger tagger = Module.getInst().getTagger();
		DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(clause));
		for (List<HasWord> token_part : tokenizer) {
			List<TaggedWord> tagged = tagger.tagSentence(token_part);
			for (TaggedWord taggedWord : tagged) {
				if (Helper.mapPartOfSpeech(taggedWord.tag()) == PartOfSpeech.NOUN) {
					String word = taggedWord.word();
					Node node = new Node(PartOfSpeech.NOUN, word);
					if (word.contains("_")) {
						word = word.substring(word.lastIndexOf("_") + 1);
					}
					node.setScore(_glove.sim(word, target_word));
					candidates_nodes.add(node);
				}
			}
		}
		// sort finally instead
		// Collections.sort(candidates_nodes, new Comparator<Node>() {
		// @Override
		// public int compare(Node o1, Node o2) {
		// return (int) (o2.getScore() - o1.getScore());
		// }
		// });
		return candidates_nodes;
	}

	@Deprecated
	public void getCenterWordCandidatesFromGraph(String clause) {
		generateDependencyGraph(clause, 0, 0, 0);
		getCenterWordCandidatesFromGraph(this._graph);
	}

	@Deprecated
	public void getCenterWordCandidatesFromGraph(Graph graph) {
		ArrayList<Node> candidates_nodes = new ArrayList<>();
		Collection<Node> nodes = graph.getNodes();
		for (Node node : nodes) {
			if (node.getPOS() == PartOfSpeech.NOUN) {
				node.setScore((node.getIncomingEdges().size() + node.getOutcomingEdges().size()));
				candidates_nodes.add(node);
			}
		}
		// sort finally instead
		// Collections.sort(candidates_nodes, new Comparator<Node>() {
		// @Override
		// public int compare(Node o1, Node o2) {
		// return (o2.getIncomingEdges().size() + o2.getOutcomingEdges().size())
		// - (o1.getIncomingEdges().size() + o1.getOutcomingEdges().size());
		// }
		// });
	}

}