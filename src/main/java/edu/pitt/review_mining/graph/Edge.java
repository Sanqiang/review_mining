package edu.pitt.review_mining.graph;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

import edu.pitt.review_mining.utility.DependencyType;

public class Edge implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;
	private DependencyType _type;
	// gov -> dep
	private Node _dep;
	private Node _gov;
	// features are whether happens in first 0/last 1 sentence of paragraph.
	private int _features = 0;
	// private int _count;
	private HashMap<Integer, HashSet<Integer>> _occurs = null;
	private double _review_weight;
	private boolean _is_negative;
	private int _rating = 0;
	private String _sentence;

	public Edge(Node gov, Node dep, DependencyType type, double review_weight, boolean is_negative, int rating,
			String sentence) {
		this._type = type;
		this._dep = dep;
		this._gov = gov;
		this._review_weight = review_weight;
		this._is_negative = is_negative;
		this._occurs = new HashMap<>();
		this._rating = rating;
		this._sentence = sentence;
	}

	public Edge(Node gov, Node dep, DependencyType type, double review_weight) {
		this(gov, dep, type, review_weight, false, -1, "");
	}

	public String getSentence() {
		return this._sentence;
	}

	public double getRating() {
		return this._rating;
	}

	// public void setRating(int rating) {
	// this._rating = rating;
	// }

	public Node getGov() {
		return this._gov;
	}

	public Node getDep() {
		return this._dep;
	}

	public boolean isNegative() {
		return this._is_negative;
	}

	public void setFeatures(int feature) {
		this._features = feature;
	}

	public int getFeatures() {
		return this._features;
	}

	// public void incrementCount() {
	// ++this._count;
	// }

	public DependencyType getDependencyType() {
		return _type;
	}

	public double getReviewWeight() {
		return this._review_weight;
	}

	public int getCount() {
		int count = 0;
		for (HashSet<Integer> sentence_occur_set : this._occurs.values()) {
			count += sentence_occur_set.size();
		}
		return count;
	}

	public void incrementCount(int sentence_idx, int word_idx) {
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

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
