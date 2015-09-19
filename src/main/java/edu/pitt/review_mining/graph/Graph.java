package edu.pitt.review_mining.graph;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import edu.pitt.review_mining.utility.DependencyType;
import edu.pitt.review_mining.utility.PartOfSpeech;

public class Graph implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, Node> _nodes;
	private HashMap<String, Edge> _edges;

	public Graph() {
		this._nodes = new HashMap<>();
		this._edges = new HashMap<>();
	}

	public Collection<Edge> getEdges() {
		return _edges.values();
	}

	public Collection<Node> getNodes() {
		return _nodes.values();
	}

	public Node createNode(PartOfSpeech pos, String lemma, int review_id, int review_loc) {
		Node n = new Node(pos, lemma, review_id, review_loc);
		if (_nodes.containsKey(n.getIdentify())) {
			return _nodes.get(n.getIdentify());
		} else {
			_nodes.put(n.getIdentify(), n);
			return n;
		}
	}

	public Node createNode(PartOfSpeech pos, String lemma) {
		return createNode(pos, lemma, 0, 0);
	}

	public Node createNode(Node n) {
		try {
			n = (Node) n.clone();
			if (_nodes.containsKey(n.getIdentify())) {
				return _nodes.get(n.getIdentify());
			} else {
				_nodes.put(n.getIdentify(), n);
				return n;
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return n;
		}

	}

	public Edge createEdge(Node gov, Node dep, DependencyType type, int sentence_idx, int word_idx) {
		Edge e = new Edge(gov, dep, type);
		if (_edges.containsKey(e.getIdentify())) {
			e = _edges.get(e.getIdentify());
			e.incrementCount(sentence_idx, word_idx);
			return e;
		} else {
			gov._outcoming_edges.add(e);
			dep._incoming_edges.add(e);
			_edges.put(e.getIdentify(), e);
			return e;
		}

	}

}
