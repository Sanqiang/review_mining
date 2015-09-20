package edu.pitt.review_mining.graph;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import edu.pitt.review_mining.utility.DependencyType;
import edu.pitt.review_mining.utility.PartOfSpeech;

public class Graph implements Serializable {

	private static final long serialVersionUID = 1L;
	private HashMap<String, Node> _nodes;
	private HashMap<String, Edge> _edges;
	private HashMap<Integer, HashSet<Edge>> _edge_occurs;

	public Graph() {
		this._nodes = new HashMap<>();
		this._edges = new HashMap<>();
		_edge_occurs = new HashMap<>();
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

	public void incrementGlobalCount(Edge e,int sentence_idx, int word_idx) {
		if (!this._edge_occurs.containsKey(sentence_idx)) {
			this._edge_occurs.put(sentence_idx, new HashSet<>());
		}
		this._edge_occurs.get(sentence_idx).add(e);
	}
	
	public HashMap<Integer, HashSet<Edge>> getGlobalOccur() {
		return this._edge_occurs;
	}
	
	public Edge createEdge(Node gov, Node dep, DependencyType type, int sentence_idx, int word_idx) {
		Edge e = new Edge(gov, dep, type);
		if (_edges.containsKey(e.getIdentify())) {
			e = _edges.get(e.getIdentify());
			e.incrementCount(sentence_idx, word_idx);
			incrementGlobalCount(e, sentence_idx, word_idx);
			return e;
		} else {
			gov._outcoming_edges.add(e);
			dep._incoming_edges.add(e);
			_edges.put(e.getIdentify(), e);
			e.incrementCount(sentence_idx, word_idx);
			incrementGlobalCount(e, sentence_idx, word_idx);
			return e;
		}

	}

}
