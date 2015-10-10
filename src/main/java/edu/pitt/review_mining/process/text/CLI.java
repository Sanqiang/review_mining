package edu.pitt.review_mining.process.text;

import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.utility.Config;


public class CLI {

	public static void main(String[] args) throws Exception {
		Graph graph = Report.readData(Config.PATH_TEXT,  10,false);
		Report.intepretGraphEdge(graph, "edge.csv");
		
		 graph = Report.readData(Config.PATH_TEXT,  0.0054969672,false);
		Report.intepretGraphEdge(graph, "edge_reduce.csv");
		
		Graph graph2 = Report.readData(Config.PATH_TEXT, 0.08,true);
		Report.intepretGraphEdge(graph2, "edge_reduce_sample.csv");
		
		//Report.generateReviewReport(Config.PATH_TEXT);
	}
}
