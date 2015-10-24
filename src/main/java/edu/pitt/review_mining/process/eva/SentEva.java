package edu.pitt.review_mining.process.eva;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.process.text.Report;
import edu.pitt.review_mining.process.text.Report.ReadObj;
import edu.pitt.review_mining.utility.Config;
import edu.pitt.review_mining.utility.Helper;

public class SentEva {
	@SuppressWarnings("unchecked")
	public static void evaluateMovieReview() throws Exception {
		Config.PATH_TEXT = "r/Dennis+Schwartz.txt";
		Config.PATH_WEIGHT = "r/weight_movie.txt";

		String map_all_obj = "output/movie/model/map_all_movie";
		HashMap<String, Double> map_all = null;
		if (new File(map_all_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_all_obj));
			map_all = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.no_sampling = true;
			Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_all = Report.intepretGraphEdge(graph, "output/movie/result/edge_all_movie.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_all_obj));
			oout_all.writeObject(map_all);
			oout_all.close();
		}

		String map_reduce_obj = "output/movie/model/map_reduce_movie";
		HashMap<String, Double> map_reduce = null;
		if (new File(map_reduce_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_obj));
			map_reduce = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.sampling_by_weight = true;
			readObj.weight_limit = 0.0166040503d;
			readObj.rating_scale = 4;
			readObj.rating_start = 0;
			readObj.ditr_interval = 25;
			Graph graph1 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_reduce = Report.intepretGraphEdge(graph1, "edge_reduce_movie.csv");
			ObjectOutputStream oout_reduce = new ObjectOutputStream(new FileOutputStream(map_reduce_obj));
			oout_reduce.writeObject(map_reduce);
			oout_reduce.close();
		}

		String map_reduce_bylen_obj = "output/movie/model/map_reduce_bylen";
		HashMap<String, Double> map_reduce_bylen = null;
		if (new File(map_reduce_bylen_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_bylen_obj));
			map_reduce_bylen = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.random_by_length = true;
			readObj.len_limit = Report.getReviewLenLimit(Config.PATH_TEXT, .08);
			Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);

			map_reduce_bylen = Report.intepretGraphEdge(graph, "output/movie/result/map_reduce_bylen.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_reduce_bylen_obj));
			oout_all.writeObject(map_reduce_bylen);
			oout_all.close();
		}

		ReadObj readObj = new ReadObj();
		readObj.sample_by_ramdom = true;
		readObj.random_limit = .226;
		Graph graph2 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_sample = Report.intepretGraphEdge(graph2,
				"output/movie/result/edge_reduce_sample_movie.csv");

		System.out.print(Helper.getCosinSim(map_all, map_reduce));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_sample));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_bylen));
	}

	@SuppressWarnings("unchecked")
	public static void evaluateCoconutWater() throws Exception {
		Config.PATH_TEXT = "r/B000CNB4LE_new.txt";
		Config.PATH_WEIGHT = "r/weight_ccwater.txt";

		String map_all_obj = "output/ccwater/model/map_all_ccwater";
		HashMap<String, Double> map_all = null;
		if (new File(map_all_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_all_obj));
			map_all = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.no_sampling = true;
			Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_all = Report.intepretGraphEdge(graph, "output/ccwater/result/edge_all_ccwater.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_all_obj));
			oout_all.writeObject(map_all);
			oout_all.close();
		}

		String map_reduce_obj = "output/ccwater/model/map_reduce_ccwater";
		HashMap<String, Double> map_reduce = null;
		if (new File(map_reduce_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_obj));
			map_reduce = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.sampling_by_weight = true;
			readObj.weight_limit = 0.1075069783d;
			readObj.rating_scale = 5;
			readObj.rating_start = 1;
			readObj.ditr_interval = 20;
			Graph graph1 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_reduce = Report.intepretGraphEdge(graph1, "output/ccwater/result/edge_reduce_ccwater.csv");
			ObjectOutputStream oout_reduce = new ObjectOutputStream(new FileOutputStream(map_reduce_obj));
			oout_reduce.writeObject(map_reduce);
			oout_reduce.close();
		}
		
		String map_reduce_bylen_obj = "output/ccwater/model/map_reduce_bylen";
		HashMap<String, Double> map_reduce_bylen = null;
		if (new File(map_reduce_bylen_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_bylen_obj));
			map_reduce_bylen = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {

			ReadObj readObj = new ReadObj();
			readObj.random_by_length = true;
			readObj.len_limit = Report.getReviewLenLimit(Config.PATH_TEXT, .1);
			Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_reduce_bylen = Report.intepretGraphEdge(graph, "output/ccwater/result/map_reduce_bylen.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_reduce_bylen_obj));
			oout_all.writeObject(map_reduce_bylen);
			oout_all.close();
		}
		ReadObj readObj = new ReadObj();
		readObj.sample_by_ramdom = true;
		readObj.random_limit = .1;
		Graph graph2 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_sample = Report.intepretGraphEdge(graph2, "edge_reduce_sample_ccwater.csv");

		System.out.print(Helper.getCosinSim(map_all, map_reduce));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_sample));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_bylen));
	}
}
