package edu.pitt.review_mining.process.text;

import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.utility.Config;


public class CLI {

	public static void main(String[] args) throws Exception {
		Graph graph = Report.readData(Config.PATH_TEXT);
		Report.intepretGraphEdge(graph, "edge.csv");
		
		//Report.generateReviewReport(Config.PATH_TEXT);
	}
}
