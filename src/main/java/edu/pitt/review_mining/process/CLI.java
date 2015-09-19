package edu.pitt.review_mining.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.graph.Node;
import edu.pitt.review_mining.utility.Config;

public class CLI {
	public static Graph readData(String path) {
		ProcessUtility process = new ProcessUtility();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(path)));
			String review = null;
			int review_id = 0;
			while ((review = reader.readLine()) != null) {
				process.processReviews(review, review_id++);
				System.out.println(review);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("finish !");
		}
		return process.getGraph();
	}

	public static void intepretGraph(Graph graph) {
		Collection<Node> nodes = graph.getNodes();
		for (int i = 0; i < nodes.size(); i++) {
			
		}
	}

	public static void main(String[] args) {
		readData(Config.PATH_TEXT);
	}
}
