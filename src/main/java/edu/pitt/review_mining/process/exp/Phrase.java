package edu.pitt.review_mining.process.exp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import edu.pitt.review_mining.utility.Config;
import edu.pitt.review_mining.utility.Helper;
import edu.pitt.review_mining.utility.Module;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.trees.Tree;

public class Phrase {
	final static String path = "data/the-halal-guys-new-york-2_text_full.txt";

	static void process() {
		try {
			LexicalizedParser lp = Module.getInst().getLexicalizedParser();
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			HashMap<String, Integer> phrases = new HashMap<>();
			String line = null;
			while (null != (line = reader.readLine())) {
				Tree tree = lp.parse(line);
				Queue<Tree> queue_nodes = new LinkedList<>();
				for (int i = 0; i < tree.children().length; i++) {
					queue_nodes.add(tree.children()[i]);
				}
				while (queue_nodes.size() >0) {
					Tree child_tree = queue_nodes.poll();
					for (int i = 0; i < child_tree.children().length; i++) {
						if (child_tree.children()[i].value().equals("NP") ) {
							String phrase = child_tree.children()[i].pennString();
							if (!phrases.containsKey(phrase)) {
								phrases.put(phrase, 0);
							}
							phrases.put(phrase, phrases.get(phrase) + 1);
						}
						queue_nodes.add(child_tree.children()[i]);
					}
				}
			}

			SortedSet<Map.Entry<String, Integer>> sort_phrases = new TreeSet<>(
					new Comparator<Map.Entry<String, Integer>>() {

						@Override
						public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
							int count1 = o1.getValue();
							int count2 = o2.getValue();
							return count2 - count1;
						}
					});
			sort_phrases.addAll(phrases.entrySet());
			
			for (Map.Entry<String, Integer> entry : sort_phrases) {
				System.out.println(entry.getKey() + ":" + entry.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Phrase.process();
	}
}
