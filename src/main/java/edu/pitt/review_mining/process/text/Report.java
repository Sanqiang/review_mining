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
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import com.eclipsesource.json.JsonArray;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

import edu.pitt.review_mining.graph.Edge;
import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.graph.Node;
import edu.pitt.review_mining.process.rating.KalmanUtility;
import edu.pitt.review_mining.utility.Config;
import edu.pitt.review_mining.utility.DependencyType;
import edu.pitt.review_mining.utility.Helper;
import edu.pitt.review_mining.utility.Stemmer;

public class Report {

	public static Graph readData(String path_data, String path_weigth,boolean weight_cut, double limit, boolean random_sample,
			int ditr_interval, int rating_scale, boolean len_cut, int len_limit, int rating_start) {
		KalmanUtility ku = new KalmanUtility(path_weigth, ditr_interval, rating_scale, rating_start);
		ProcessUtility process = new ProcessUtility();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(path_data)));
			String line = null;
			int review_idx = 0;
			int process_idx = 0;
			int process_words = 0;
			int total_words = 0;
			while ((line = reader.readLine()) != null) {
				String[] items = line.split("\t");
				if (items.length == 3) {
					int rating = Integer.parseInt(items[0]);
					long time = Long.parseLong(items[1]);
					String review = items[2];
					int n_words = review.split(" ").length;
					double review_weight = ku.getWeight(review_idx, rating);
					if ((review_weight >= limit && weight_cut) || (random_sample && Math.random() < limit)
							|| (len_cut && review.length() >= len_limit)) {
						process.processReviews(review, review_idx, review_weight, rating,time);
						++process_idx;
						process_words += n_words;
					}
					++review_idx;
					total_words += n_words;
				} else {
					System.err.println(line);
				}
			}
			reader.close();
			double portion_item = (double) process_idx / review_idx;
			System.out.println("Processed Item: " + portion_item);
			double portion_words = (double) process_words / total_words;
			System.out.println("Processed Words: " + portion_words);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			System.out.println("finish !");
		}
		return process.getGraph();
	}

	public static Graph readData(String path_data, String path_weigth,boolean weight_cut, double limit, boolean random_sample,
			int ditr_interval, int rating_scale, int rating_start) {
		return readData(path_data, path_weigth,weight_cut, limit, random_sample, ditr_interval, rating_scale, false,
				Integer.MAX_VALUE, rating_start);
	}

	public static void generateReviewReport(String path, int rating_scale) {
		KalmanUtility ku = new KalmanUtility(Config.PATH_WEIGHT, 20, rating_scale, 1);
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("result.txt")));
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			String line = null;
			int review_idx = 0;
			while ((line = reader.readLine()) != null) {
				String[] items = line.split("\t");
				if (items.length == 3) {
					int rating = Integer.parseInt(items[0]);
					String review = items[2];
					double review_weight = ku.getWeight(review_idx, rating);
					sb.append(review_weight).append("\t").append(review).append("\n");
					++review_idx;
				} else {
					System.err.println(line);
				}
			}
			reader.close();
			writer.write(sb.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// node level report
	@Deprecated
	public static void intepretGraphNode(Graph graph) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("result.txt")));
			Collection<Node> nodes = graph.getNodes();
			ArrayList<JsonObject> arr = new ArrayList<>();
			for (Node node : nodes) {
				// if (node.getPOS() == PartOfSpeech.NOUN) {
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
						} else if (r_weight2 > r_weight1) {
							return -1;
						} else {
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
				// }
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
					} else if (r_weight2 > r_weight1) {
						return -1;
					} else {
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

	// edge level report
	public static HashMap<String, Double> intepretGraphEdge(Graph graph, String output) {
		try {
			WordNetUtility word_net = new WordNetUtility();
			SentiWordNetUtility sent_word_net = new SentiWordNetUtility(Config.PATH_WN_SENT);
			TreeMap<String, ArrayList<JsonObject>> map_obj = new TreeMap<>();

			Collection<Edge> edges = graph.getEdges();
			for (Edge edge : edges) {
				Node dep_node = edge.getDep();
				Node gov_node = edge.getGov();

				// make sure dep is aspect and gov is opinion
				if (word_net.isFirstNoun(gov_node.getLemma(), dep_node.getLemma()) > 0) {
					Node temp = dep_node;
					dep_node = gov_node;
					gov_node = temp;
				}
				String dep_lemma = Stemmer.getStem(dep_node.getLemma());
				String gov_lemma = gov_node.getLemma();
				double gov_sentiment = sent_word_net.extract(gov_lemma, gov_node.getPOS());
				DependencyType type = edge.getDependencyType();
				if (type == DependencyType.ConjAndComp || type == DependencyType.XComplement
						|| type == DependencyType.SingleNmod) {
					// TODO
				} else {
					JsonObject obj = new JsonObject();
					obj/* .add("dep", dep_lemma) */.add("gov", gov_lemma).add("sent", gov_sentiment)
							.add("rel", type.name()).add("freq", edge.getCount())
							.add("r_weight", edge.getReviewWeight()).add("clause", edge.getSentence());
					if (!map_obj.containsKey(dep_lemma)) {
						map_obj.put(dep_lemma, new ArrayList<>());
					}
					map_obj.get(dep_lemma).add(obj);
				}
			}

			SortedSet<Map.Entry<String, ArrayList<JsonObject>>> map_obj2 = entriesSortedByValues(map_obj);

			StringBuilder sb = new StringBuilder();
			JsonArray arr_output = new JsonArray();
			HashMap<String, Double> map = new HashMap<>();
			for (Map.Entry<String, ArrayList<JsonObject>> map_obj_entry : map_obj2) {
				String dep = map_obj_entry.getKey();
				double sent = 0;
				double sent_pos = 0;
				double sent_neg = 0;
				int sent_pos_n = 0;
				int sent_neg_n = 0;
				double total_r_weight = 0;

				JsonObject obj_output = new JsonObject();
				JsonArray arr_output_temp = new JsonArray();
				for (JsonObject obj : map_obj_entry.getValue()) {
					double obj_sent = obj.get("sent").asDouble();
					int obj_freq = obj.get("freq").asInt();
					if (obj_sent > 0) {
						sent_pos += obj_sent * obj_freq;
						sent_pos_n++;
					} else if (obj_sent < 0) {
						sent_neg += obj_sent * obj_freq;
						sent_neg_n++;
					}
					total_r_weight += obj.get("r_weight").asDouble();
					sent += obj_sent * obj_freq;
					arr_output_temp.add(obj);

				}
				if (sent_pos_n + sent_neg_n > 0) {
					total_r_weight /= (sent_pos_n + sent_neg_n);
				}
				sb.append(dep).append(",").append(sent).append("\n");
				// return hashmap with word-sent
				map.put(dep, sent);
				obj_output.add("dep", dep).add("sent", sent).add("list", arr_output_temp).add("sent_pos", sent_pos)
						.add("sent_neg", sent_neg).add("sent_pos_n", sent_pos_n).add("sent_neg_n", sent_neg_n)
						.add("total_r_weight", total_r_weight);
				arr_output.add(obj_output);
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(output)));
			writer.write(sb.toString());
			writer.close();
			writer = new BufferedWriter(new FileWriter(new File(output + "_json")));
			writer.write(arr_output.toString());
			writer.close();

			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
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

	public static int getReviewLenLimit(String path_data, double percentage) {
		Queue<Integer> qi = new PriorityQueue<Integer>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(path_data)));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] items = line.split("\t");
				if (items.length == 3) {
					String review = items[2];
					int review_len = review.length();
					qi.add(review_len);
				} else {
					System.err.println(line);
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		double origin_size = qi.size();
		while (qi.size() > 0) {
			int cur_len = qi.poll();
			if (percentage > qi.size() / origin_size) {
				return cur_len;
			}
		}
		return 0;
	}

	private static SortedSet<Map.Entry<String, ArrayList<JsonObject>>> entriesSortedByValues(
			Map<String, ArrayList<JsonObject>> map) {
		SortedSet<Map.Entry<String, ArrayList<JsonObject>>> sortedEntries = new TreeSet<Map.Entry<String, ArrayList<JsonObject>>>(
				new Comparator<Map.Entry<String, ArrayList<JsonObject>>>() {
					@Override
					public int compare(Map.Entry<String, ArrayList<JsonObject>> e1,
							Map.Entry<String, ArrayList<JsonObject>> e2) {
						int freq1 = 0, freq2 = 0;
						for (JsonObject obj : e1.getValue()) {
							freq1 += obj.get("freq").asInt();
						}
						for (JsonObject obj : e2.getValue()) {
							freq2 += obj.get("freq").asInt();
						}
						return freq2 - freq1;
					}
				});
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}
}
