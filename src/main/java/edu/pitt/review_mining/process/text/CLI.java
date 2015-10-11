package edu.pitt.review_mining.process.text;

import java.util.HashMap;

import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.utility.Config;
import edu.pitt.review_mining.utility.Helper;


public class CLI {

	public static void main(String[] args) throws Exception {
		Graph graph = Report.readData(Config.PATH_TEXT,  10,false);
		HashMap<String, Double> map_all = Report.intepretGraphEdge(graph, "edge_all.csv");
		
		Graph graph1 = Report.readData(Config.PATH_TEXT,  0.0166040503,false);
		HashMap<String, Double> map_reduce = Report.intepretGraphEdge(graph1, "edge_reduce.csv");
		
		Graph graph2 = Report.readData(Config.PATH_TEXT, 0.226,true);
		HashMap<String, Double> map_reduce_sample = Report.intepretGraphEdge(graph2, "edge_reduce_sample.csv");
		
		System.out.println(Helper.getCosinSim(map_all, map_reduce));
		
		System.out.println(Helper.getCosinSim(map_all, map_reduce_sample));
		
		//Report.generateReviewReport(Config.PATH_TEXT);
	}
}
