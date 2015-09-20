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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;

import edu.pitt.review_mining.graph.Edge;
import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.graph.Node;
import edu.pitt.review_mining.utility.Config;
import edu.pitt.review_mining.utility.PartOfSpeech;
import scala.annotation.varargs;

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
			// report 1: graph presentation
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("result.txt")));
			Collection<Node> nodes = graph.getNodes();
			ArrayList<JsonObject> arr = new ArrayList<>();
			for (Node node : nodes) {
				if (node.getPOS() == PartOfSpeech.NOUN) {
					JsonObject obj = new JsonObject();
					obj.add("lemma", node.getLemma());
					List<JsonObject> arr_relation = new ArrayList<>();
					int total_count = 0;
					for (Edge edge : node.getOutcomingEdges()) {
						Node another_node = edge.getOtherNode(node);
						JsonObject rela = new JsonObject();
						rela.add("lemma", another_node.getLemma());
						rela.add("rel", edge.getDependencyType().name());
						rela.add("freq", edge.getCount());
						total_count += edge.getCount();
						arr_relation.add(rela);
					}
					Collections.sort(arr_relation, new Comparator<JsonObject>() {

						@Override
						public int compare(JsonObject o1, JsonObject o2) {
							int freq1 = o1.get("freq").asInt();
							int freq2 = o2.get("freq").asInt();
							return freq2 - freq1;
						}
					});
					JsonArray arr_json = new JsonArray();
					for (JsonObject item_arr_relation : arr_relation) {
						arr_json.add(item_arr_relation);
					}
					obj.add("rels", arr_json);
					arr.add(obj);
					obj.add("total_count", total_count);
				}
			}
			Collections.sort(arr, new Comparator<JsonObject>() {

				@Override
				public int compare(JsonObject o1, JsonObject o2) {
					int total_count1 = o1.get("total_count").asInt();
					int total_count2 = o2.get("total_count").asInt();
					return total_count2 - total_count1;
				}
			});
			JsonArray arr_json = new JsonArray();
			for (JsonObject item_arr : arr) {
				arr_json.add(item_arr);
			}
			writer.write(arr_json.toString());
			writer.close();
			// report 2 sentence presentation
			writer = new BufferedWriter(new FileWriter(new File("result2.txt")));
			HashMap<Integer, HashSet<Edge>> occurs = graph.getGlobalOccur();
			JsonArray arr_occur = new JsonArray();
			for (int i = 0; i < occurs.size(); i++) {
				HashSet<Edge> edges = occurs.get(i);
				JsonArray arr_occur_set = new JsonArray();
				if (edges != null) {
					for (Edge edge : edges) {
						JsonObject arr_occur_obj = new JsonObject();
						arr_occur_obj.add("id", edge.getIdentify());
						arr_occur_obj.add("freq", edge.getCount());
						arr_occur_set.add(arr_occur_obj);
					}
				}
				
				arr_occur.add(arr_occur_set);
			}
			writer.write(arr_occur.toString());
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

		intepretGraph(graph);
	}
}
