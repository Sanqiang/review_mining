package edu.pitt.review_mining.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import edu.pitt.review_mining.utility.DependencyType;

public class Edge {
	private DependencyType _type;
	// gov -> dep
	private Node _dep;
	private Node _gov;

	// private int _count;
	private HashMap<Integer,HashSet<Integer>> _occurs = null;

	public Edge(Node gov, Node dep, DependencyType type) {
		this._type = type;
		this._dep = dep;
		this._gov = gov;
		this._occurs = new HashMap<>();
	}

	// public void incrementCount() {
	// ++this._count;
	// }

	public DependencyType getDependencyType() {
		return _type;
	}
	
	public int getCount() {
		int count = 0;
		for (HashSet<Integer> sentence_occur_set : this._occurs.values()) {
			count += sentence_occur_set.size();
		}
		return count;
	}

	public void incrementCount(int sentence_idx, int word_idx){
		if (!this._occurs.containsKey(sentence_idx)) {
			this._occurs.put(sentence_idx, new HashSet<>());
		}
		this._occurs.get(sentence_idx).add(word_idx);
	}
	
	public String getIdentify() {
		return String.join("_", _dep.getIdentify(), _type.name(), _gov.getIdentify());
	}

	public Node getOtherNode(Node node) {
		if (node == _dep) {
			return _gov;
		} else if (node == _gov) {
			return _dep;
		} else {
			return null;
		}
	}
}
