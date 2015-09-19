package edu.pitt.review_mining.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import edu.pitt.review_mining.graph.Edge;
import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.graph.Node;
import edu.pitt.review_mining.utility.Config;
import edu.pitt.review_mining.utility.PartOfSpeech;

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
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("result.txt")));
			Collection<Node> nodes = graph.getNodes();
			JsonArray arr = new JsonArray();
			for (Node node : nodes) {
				JsonObject obj = new JsonObject();
				obj.add("lemma", node.getLemma());
				JsonArray arr_relation = new JsonArray();
				if (node.getPOS() == PartOfSpeech.NOUN) {
					for (Edge edge : node.getOutcomingEdges()) {
						Node another_node = edge.getOtherNode(node);
						JsonObject rela = new JsonObject();
						rela.add("lemma", another_node.getLemma());
						rela.add("rel", edge.getDependencyType().name());
						arr_relation.add(rela);
					}
					obj.add("rels", arr_relation);
					arr.add(obj);
				}
			}
			writer.write(arr.toString());
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void main(String[] args) throws Exception {
		Graph graph = readData(Config.PATH_TEXT);
		File file = new File("graph.out");
        ObjectOutputStream oout = new ObjectOutputStream(new FileOutputStream(file));
        oout.writeObject(graph);
        oout.close();
		
		//intepretGraph(graph);
	}
}
