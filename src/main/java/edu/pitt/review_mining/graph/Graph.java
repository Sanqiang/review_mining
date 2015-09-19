package edu.pitt.review_mining.graph;

import java.util.Collection;
import java.util.HashMap;

import edu.pitt.review_mining.utility.DependencyType;
import edu.pitt.review_mining.utility.PartOfSpeech;

public class Graph {
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

	public Node createNode(PartOfSpeech pos, String lemma) {
		Node n = new Node(pos, lemma);
		if (_nodes.containsKey(n.getIdentify())) {
			return _nodes.get(n.getIdentify());
		} else {
			_nodes.put(n.getIdentify(), n);
			return n;
		}
	}

	public Edge createEdge(Node gov, Node dep, DependencyType type, int sentence_idx, int word_idx) {
		Edge e = new Edge(gov, dep, type);
		if (_edges.containsKey(e.getIdentify())) {
			e = _edges.get(e.getIdentify());
			e.incrementCount(sentence_idx,word_idx);
			return e;
		} else {
			gov._outcoming_edges.add(e);
			dep._incoming_edges.add(e);
			_edges.put(e.getIdentify(), e);
			return e;
		}

	}

}
