package edu.pitt.review_mining.process.text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import edu.pitt.review_mining.graph.Edge;
import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.graph.Node;
import edu.pitt.review_mining.process.rating.KalmanUtility;
import edu.pitt.review_mining.utility.Config;
import edu.pitt.review_mining.utility.Helper;
import edu.pitt.review_mining.utility.PartOfSpeech;

public class Report {

	public static Graph readData(String path) {
		KalmanUtility ku = new KalmanUtility(Config.PATH_WEIGHT, 20);
		ProcessUtility process = new ProcessUtility();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(path)));
			String line = null;
			int review_idx = 0;
			while ((line = reader.readLine()) != null) {
				String[] items = line.split("\t");
				if (items.length == 3) {
					int rating = Integer.parseInt(items[0]);
					if (rating != 1) {
						continue;
					}
					String review = items[2];
					double review_weight = ku.getWeight(review_idx, rating);
					process.processReviews(review, review_idx++, review_weight);
				} else {
					System.err.println(line);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("finish !");
		}
		return process.getGraph();
	}

	// report 1: graph presentation
	public static void intepretGraph(Graph graph) {
		try {

			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("result.txt")));
			Collection<Node> nodes = graph.getNodes();
			ArrayList<JsonObject> arr = new ArrayList<>();
			for (Node node : nodes) {
				if (node.getPOS() == PartOfSpeech.NOUN) {
					JsonObject obj = new JsonObject();
					obj.add("lemma", node.getLemma());
					List<JsonObject> arr_relation = new ArrayList<>();
					int total_count = 0;
					double review_weight = 0;
					for (Edge edge : node.getOutcomingEdges()) {
						Node another_node = edge.getOtherNode(node);
						JsonObject rela = new JsonObject();
						rela.add("lemma", another_node.getLemma());
						rela.add("rel", edge.getDependencyType().name());
						rela.add("freq", edge.getCount());
						rela.add("r_weight", edge.getReviewWeight());
						total_count += edge.getCount();
						review_weight += edge.getReviewWeight();
						arr_relation.add(rela);
					}
					Collections.sort(arr_relation, new Comparator<JsonObject>() {

						@Override
						public int compare(JsonObject o1, JsonObject o2) {
							// int freq1 = o1.get("freq").asInt();
							// int freq2 = o2.get("freq").asInt();
							// return freq2 - freq1;
							double r_weight1 = o1.get("r_weight").asDouble();
							double r_weight2 = o2.get("r_weight").asDouble();
							if (r_weight2 > r_weight1) {
								return 1;
							}else if (r_weight2 > r_weight1) {
								return -1;
							}else {
								return 0;
							}
						}
					});
					JsonArray arr_json = new JsonArray();
					for (JsonObject item_arr_relation : arr_relation) {
						arr_json.add(item_arr_relation);
					}
					obj.add("rels", arr_json);
					arr.add(obj);
					obj.add("total_count", total_count);
					review_weight /= node.getOutcomingEdges().size();
					if (Double.isNaN(review_weight)) {
						review_weight = 0d;
					}
					obj.add("r_weight", review_weight);
				}
			}
			Collections.sort(arr, new Comparator<JsonObject>() {

				@Override
				public int compare(JsonObject o1, JsonObject o2) {
					// int total_count1 = o1.get("total_count").asInt();
					// int total_count2 = o2.get("total_count").asInt();
					// return total_count2 - total_count1;
					double r_weight1 = o1.get("r_weight").asDouble();
					double r_weight2 = o2.get("r_weight").asDouble();
					if (r_weight2 > r_weight1) {
						return 1;
					}else if (r_weight2 > r_weight1) {
						return -1;
					}else {
						return 0;
					}
				}
			});
			JsonArray arr_json = new JsonArray();
			for (JsonObject item_arr : arr) {
				arr_json.add(item_arr);
			}
			writer.write(arr_json.toString());
			writer.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	// report 2 sentence presentation
	public static void intepretGraph2(Graph graph) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("result2.txt")));
			HashMap<Integer, HashSet<Edge>> occurs = graph.getGlobalOccur();
			JsonArray arr_occur = new JsonArray();
			for (int i = 0; i < occurs.size(); i++) {
				HashSet<Edge> edges = occurs.get(i);
				ArrayList<JsonObject> arr_occur_set = new ArrayList<>();
				if (edges != null) {
					for (Edge edge : edges) {
						JsonObject arr_occur_obj = new JsonObject();
						arr_occur_obj.add("id", edge.getIdentify());
						arr_occur_obj.add("freq", edge.getCount());
						arr_occur_obj.add("pos_first", Helper.getBit(edge.getFeatures(), 0));
						arr_occur_obj.add("pos_last", Helper.getBit(edge.getFeatures(), 1));
						arr_occur_set.add(arr_occur_obj);
					}
				}
				Collections.sort(arr_occur_set, new Comparator<JsonObject>() {

					@Override
					public int compare(JsonObject o1, JsonObject o2) {
						int freq1 = o1.get("freq").asInt();
						int freq2 = o2.get("freq").asInt();
						return freq2 - freq1;
					}
				});
				JsonArray arr_json_occur_set = new JsonArray();
				for (JsonObject occur_set : arr_occur_set) {
					arr_json_occur_set.add(occur_set);
				}
				arr_occur.add(arr_json_occur_set);
			}

			// weight tune
			double[] weight = Config.WEIGHT_FORWARD_BACKWARD;
			for (int i = 0; i < arr_occur.size(); i++) {
				JsonArray arr_json_occur_set = arr_occur.get(i).asArray();
				for (JsonValue arr_occur_obj_value : arr_json_occur_set) {
					JsonObject arr_occur_obj = arr_occur_obj_value.asObject();
					String id = arr_occur_obj.get("id").asString();
					// backward-match
					double score = 0;
					for (int j = i - 1; j >= 0 && i - j <= weight.length; j--) {
						JsonArray arr_json_occur_set_loop = arr_occur.get(j).asArray();
						for (JsonValue arr_occur_obj_value_loop : arr_json_occur_set_loop) {
							JsonObject arr_occur_obj_loop = arr_occur_obj_value_loop.asObject();
							String id_loop = arr_occur_obj_loop.get("id").asString();
							if (id.equals(id_loop)) {
								score += weight[i - j - 1];
								break;
							}
						}
					}
					// forward-match
					for (int j = i + 1; j < arr_occur.size() && j - i <= weight.length; j++) {
						JsonArray arr_json_occur_set_loop = arr_occur.get(j).asArray();
						for (JsonValue arr_occur_obj_value_loop : arr_json_occur_set_loop) {
							JsonObject arr_occur_obj_loop = arr_occur_obj_value_loop.asObject();
							String id_loop = arr_occur_obj_loop.get("id").asString();
							if (id.equals(id_loop)) {
								score += weight[j - i - 1];
								break;
							}
						}
					}
					arr_occur_obj.add("score", score);
				}
			}

			writer.write(arr_occur.toString());
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
