package edu.pitt.review_mining.graph;

import java.util.ArrayList;

import edu.pitt.review_mining.utility.DependencyType;
import edu.pitt.review_mining.utility.PartOfSpeech;

public class Node {
	private PartOfSpeech _pos;
	private String _lemma;
	ArrayList<Edge> _incoming_edges;
	ArrayList<Edge> _outcoming_edges;
	// optional
	private int _loc_sent;
	private int _idx_sent;

	public Node(PartOfSpeech pos, String lemma) {
		this._pos = pos;
		this._lemma = lemma;
		this._incoming_edges = new ArrayList<>();
		this._outcoming_edges = new ArrayList<>();
	}

	public Node(PartOfSpeech pos, String lemma, int loc_sent, int idx_sent) {
		this._pos = pos;
		this._lemma = lemma;
		this._incoming_edges = new ArrayList<>();
		this._outcoming_edges = new ArrayList<>();
		this._loc_sent = loc_sent;
		this._idx_sent = idx_sent;
	}

	public String getIdentify() {
		return String.join("_", _lemma,_pos.name(), String.valueOf(_loc_sent));
	}
	
	public PartOfSpeech getPOS() {
		return _pos;
	}
	
	public String getLemma() {
		return _lemma;
	}
	
	public ArrayList<Edge> getIncomingEdges() {
		return _incoming_edges;
	}
	
	public ArrayList<Edge> getOutcomingEdges() {
		return _outcoming_edges;
	}

	// gov -> dep
	@Deprecated
	public Edge addEdge(Node node, DependencyType type, boolean is_self_gov) {
		Edge e = null;
		if (is_self_gov) { // self is gov and node is dep so build edge from gov
							// to dep
			e = new Edge(this, node, type);
			this._outcoming_edges.add(e);
			node._incoming_edges.add(e);
		} else {
			e = new Edge(node, this, type);
			this._incoming_edges.add(e);
			node._outcoming_edges.add(e);
		}
		return e;
	}
}
