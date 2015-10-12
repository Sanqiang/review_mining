package edu.pitt.review_mining.process.eva;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.process.text.Report;
import edu.pitt.review_mining.utility.Config;
import edu.pitt.review_mining.utility.Helper;

public class SentEva {
	@SuppressWarnings("unchecked")
	public static void evaluateMovieReview() throws Exception {
		String map_all_obj = "map_all";
		HashMap<String, Double> map_all = null;
		if (new File(map_all_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_all_obj));
			map_all = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			Graph graph = Report.readData(Config.PATH_TEXT, 10, false);
			map_all = Report.intepretGraphEdge(graph, "edge_all.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_all_obj));
			oout_all.writeObject(map_all);
			oout_all.close();
		}

		String map_reduce_obj = "map_reduce";
		HashMap<String, Double> map_reduce = null;
		if (new File(map_reduce_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_obj));
			map_reduce = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			Graph graph1 = Report.readData(Config.PATH_TEXT, 0.0166040503, false);
			map_reduce = Report.intepretGraphEdge(graph1, "edge_reduce.csv");
			ObjectOutputStream oout_reduce = new ObjectOutputStream(new FileOutputStream(map_reduce_obj));
			oout_reduce.writeObject(map_reduce);
			oout_reduce.close();
		}

		Graph graph2 = Report.readData(Config.PATH_TEXT, 0.226, true);
		HashMap<String, Double> map_reduce_sample = Report.intepretGraphEdge(graph2, "edge_reduce_sample.csv");

		System.out.print(Helper.getCosinSim(map_all, map_reduce));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_sample));
	}
}
