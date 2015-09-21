package edu.pitt.review_mining.process;

import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.utility.Config;

public class CLI {

	public static void main(String[] args) throws Exception {
		Graph graph = Report.readData(Config.PATH_TEXT);
		//File file = new File("graph.out");
		// ObjectOutputStream oout = new ObjectOutputStream(new
		// FileOutputStream(file));
		// oout.writeObject(graph);
		// oout.close();

		Report.intepretGraph2(graph);
	}
}
