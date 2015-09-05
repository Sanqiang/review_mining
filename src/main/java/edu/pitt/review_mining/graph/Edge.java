package edu.pitt.review_mining.graph;


import edu.pitt.review_mining.utility.DependencyType;

public class Edge {
	private DependencyType _type;
	// gov -> dep
	private Node _dep;
	private Node _gov;

	public Edge(Node gov, Node dep, DependencyType type) {
		this._type = type;
		this._dep = dep;
		this._gov = gov;
	}

	public String getIdentify() {
		return String.join("_", _dep.getIdentify(), _type.name(), _gov.getIdentify());
	}
	
	public Node getOtherNode(Node node) {
		if (node == _dep) {
			return _gov;
		}else if (node == _gov) {
			return _dep;
		}else{
			return null;
		}
	}
}
