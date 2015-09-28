package edu.pitt.review_mining.process.text;

import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.utility.Config;

public class CLI {

	public static void main(String[] args) throws Exception {
		Graph graph = Report.readData(Config.PATH_TEXT);
		Report.intepretGraph(graph);
		//Report.intepretGraph2(graph);
	}
}
