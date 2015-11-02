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
	static String base = "C:/git/review_mining/";

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

		readObj = new ReadObj();
		readObj.sampling_by_jump = true;
		readObj.len_jump = 5;
		Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byjump = Report.intepretGraphEdge(graph,
				"output/movie/result/map_reduce_byjump.csv");

		readObj = new ReadObj();
		readObj.sampling_by_rating = true;
		readObj.weight_rating = Report.getWeightByRating(Config.PATH_TEXT);
		readObj.random_limit = .2;
		Graph graph3 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byrating = Report.intepretGraphEdge(graph3,
				"output/movie/result/map_reduce_byrating.csv");

		System.out.print(Helper.getCosinSim(map_all, map_reduce));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_sample));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_bylen));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byjump));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byrating));
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

		readObj = new ReadObj();
		readObj.sampling_by_jump = true;
		readObj.len_jump = 10;
		Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byjump = Report.intepretGraphEdge(graph,
				"output/ccwater/result/map_reduce_byjump.csv");

		readObj = new ReadObj();
		readObj.sampling_by_rating = true;
		readObj.weight_rating = Report.getWeightByRating(Config.PATH_TEXT);
		readObj.random_limit = .1;
		Graph graph3 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byrating = Report.intepretGraphEdge(graph3,
				"output/ccwater/result/map_reduce_byrating.csv");

		System.out.print(Helper.getCosinSim(map_all, map_reduce));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_sample));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_bylen));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byjump));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byrating));
	}

	@SuppressWarnings("unchecked")
	public static void evaluateB00EOE0WKQ() throws Exception {
		Config.PATH_TEXT = "r/B00EOE0WKQ_new.txt";
		Config.PATH_WEIGHT = "r/weight_B00EOE0WKQ.txt";

		String map_all_obj = "output/B00EOE0WKQ/model/map_all_movie";
		HashMap<String, Double> map_all = null;
		if (new File(map_all_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_all_obj));
			map_all = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.no_sampling = true;
			Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_all = Report.intepretGraphEdge(graph, "output/B00EOE0WKQ/result/edge_all_movie.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_all_obj));
			oout_all.writeObject(map_all);
			oout_all.close();
		}

		String map_reduce_obj = "output/B00EOE0WKQ/model/map_reduce_movie";
		HashMap<String, Double> map_reduce = null;
		if (new File(map_reduce_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_obj));
			map_reduce = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.sampling_by_weight = true;
			readObj.weight_limit = 1.459538e-03;
			readObj.rating_scale = 5;
			readObj.rating_start = 1;
			readObj.ditr_interval = 100;
			Graph graph1 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_reduce = Report.intepretGraphEdge(graph1, "edge_reduce_movie.csv");
			ObjectOutputStream oout_reduce = new ObjectOutputStream(new FileOutputStream(map_reduce_obj));
			oout_reduce.writeObject(map_reduce);
			oout_reduce.close();
		}

		String map_reduce_bylen_obj = "output/B00EOE0WKQ/model/map_reduce_bylen";
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

			map_reduce_bylen = Report.intepretGraphEdge(graph, "output/B00EOE0WKQ/result/map_reduce_bylen.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_reduce_bylen_obj));
			oout_all.writeObject(map_reduce_bylen);
			oout_all.close();
		}

		ReadObj readObj = new ReadObj();
		readObj.sample_by_ramdom = true;
		readObj.random_limit = .226;
		Graph graph2 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_sample = Report.intepretGraphEdge(graph2,
				"output/B00EOE0WKQ/result/edge_reduce_sample_movie.csv");

		readObj = new ReadObj();
		readObj.sampling_by_jump = true;
		readObj.len_jump = 5;
		Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byjump = Report.intepretGraphEdge(graph,
				"output/B00EOE0WKQ/result/map_reduce_byjump.csv");

		readObj = new ReadObj();
		readObj.sampling_by_rating = true;
		readObj.weight_rating = Report.getWeightByRating(Config.PATH_TEXT);
		readObj.random_limit = .2;
		Graph graph3 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byrating = Report.intepretGraphEdge(graph3,
				"output/B00EOE0WKQ/result/map_reduce_byrating.csv");

		System.out.print(Helper.getCosinSim(map_all, map_reduce));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_sample));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_bylen));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byjump));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byrating));
	}

	@SuppressWarnings("unchecked")
	public static void evaluateB001FA1O0O() throws Exception {
		Config.PATH_TEXT = base + "r/B001FA1O0O_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_B001FA1O0O.txt";

		String map_all_obj = base + "output/B001FA1O0O/model/map_all_movie";
		HashMap<String, Double> map_all = null;
		if (new File(map_all_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_all_obj));
			map_all = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.no_sampling = true;
			Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_all = Report.intepretGraphEdge(graph, base + "output/B001FA1O0O/result/edge_all_movie.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_all_obj));
			oout_all.writeObject(map_all);
			oout_all.close();
		}

		String map_reduce_obj = base + "output/B001FA1O0O/model/map_reduce_movie";
		HashMap<String, Double> map_reduce = null;
		if (new File(map_reduce_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_obj));
			map_reduce = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.sampling_by_weight = true;
			readObj.weight_limit = 7.172511e-02;
			readObj.rating_scale = 5;
			readObj.rating_start = 1;
			readObj.ditr_interval = 50;
			Graph graph1 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_reduce = Report.intepretGraphEdge(graph1, "edge_reduce_movie.csv");
			ObjectOutputStream oout_reduce = new ObjectOutputStream(new FileOutputStream(map_reduce_obj));
			oout_reduce.writeObject(map_reduce);
			oout_reduce.close();
		}

		String map_reduce_bylen_obj = base + "output/B001FA1O0O/model/map_reduce_bylen";
		HashMap<String, Double> map_reduce_bylen = null;
		if (new File(map_reduce_bylen_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_bylen_obj));
			map_reduce_bylen = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.random_by_length = true;
			readObj.len_limit = Report.getReviewLenLimit(Config.PATH_TEXT, .01);
			Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);

			map_reduce_bylen = Report.intepretGraphEdge(graph, base + "output/B001FA1O0O/result/map_reduce_bylen.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_reduce_bylen_obj));
			oout_all.writeObject(map_reduce_bylen);
			oout_all.close();
		}

		ReadObj readObj = new ReadObj();
		readObj.sample_by_ramdom = true;
		readObj.random_limit = .1;
		Graph graph2 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_sample = Report.intepretGraphEdge(graph2,
				base + "output/B001FA1O0O/result/edge_reduce_sample.csv");

		readObj = new ReadObj();
		readObj.sampling_by_jump = true;
		readObj.len_jump = 10;
		Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byjump = Report.intepretGraphEdge(graph,
				base + "output/B001FA1O0O/result/map_reduce_byjump.csv");

		readObj = new ReadObj();
		readObj.sampling_by_rating = true;
		readObj.weight_rating = Report.getWeightByRating(Config.PATH_TEXT);
		readObj.random_limit = .1;
		Graph graph3 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byrating = Report.intepretGraphEdge(graph3,
				base + "output/B001FA1O0O/result/map_reduce_byrating.csv");

		System.out.print(Helper.getCosinSim(map_all, map_reduce));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_sample));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_bylen));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byjump));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byrating));
	}

	@SuppressWarnings("unchecked")
	public static void evaluateB0064CL1T2() throws Exception {
		Config.PATH_TEXT = base + "r/B0064CL1T2_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_B0064CL1T2.txt";

		String map_all_obj = base + "output/B0064CL1T2/model/map_all_movie";
		HashMap<String, Double> map_all = null;
		if (new File(map_all_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_all_obj));
			map_all = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.no_sampling = true;
			Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_all = Report.intepretGraphEdge(graph, base + "output/B0064CL1T2/result/edge_all_movie.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_all_obj));
			oout_all.writeObject(map_all);
			oout_all.close();
		}

		String map_reduce_obj = base + "output/B0064CL1T2/model/map_reduce_movie";
		HashMap<String, Double> map_reduce = null;
		if (new File(map_reduce_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_obj));
			map_reduce = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.sampling_by_weight = true;
			readObj.weight_limit = 0.0039434937;
			readObj.rating_scale = 5;
			readObj.rating_start = 1;
			readObj.ditr_interval = 50;
			Graph graph1 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_reduce = Report.intepretGraphEdge(graph1, "edge_reduce_movie.csv");
			ObjectOutputStream oout_reduce = new ObjectOutputStream(new FileOutputStream(map_reduce_obj));
			oout_reduce.writeObject(map_reduce);
			oout_reduce.close();
		}

		String map_reduce_bylen_obj = base + "output/B0064CL1T2/model/map_reduce_bylen";
		HashMap<String, Double> map_reduce_bylen = null;
		if (new File(map_reduce_bylen_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_bylen_obj));
			map_reduce_bylen = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.random_by_length = true;
			readObj.len_limit = Report.getReviewLenLimit(Config.PATH_TEXT, .03);
			Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);

			map_reduce_bylen = Report.intepretGraphEdge(graph, base + "output/B0064CL1T2/result/map_reduce_bylen.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_reduce_bylen_obj));
			oout_all.writeObject(map_reduce_bylen);
			oout_all.close();
		}

		ReadObj readObj = new ReadObj();
		readObj.sample_by_ramdom = true;
		readObj.random_limit = .05;
		Graph graph2 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_sample = Report.intepretGraphEdge(graph2,
				base + "output/B0064CL1T2/result/edge_reduce_sample.csv");

		readObj = new ReadObj();
		readObj.sampling_by_jump = true;
		readObj.len_jump = 20;
		Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byjump = Report.intepretGraphEdge(graph,
				base + "output/B0064CL1T2/result/map_reduce_byjump.csv");

		readObj = new ReadObj();
		readObj.sampling_by_rating = true;
		readObj.weight_rating = Report.getWeightByRating(Config.PATH_TEXT);
		readObj.random_limit = .05;
		Graph graph3 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byrating = Report.intepretGraphEdge(graph3,
				base + "output/B0064CL1T2/result/map_reduce_byrating.csv");

		System.out.print(Helper.getCosinSim(map_all, map_reduce));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_sample));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_bylen));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byjump));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byrating));
	}

	@SuppressWarnings("unchecked")
	public static void evaluateB0036FTF4S() throws Exception {
		String id = "B0036FTF4S";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";

		String map_all_obj = base + "output/" + id + "/model/map_all_movie";
		HashMap<String, Double> map_all = null;
		if (new File(map_all_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_all_obj));
			map_all = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.no_sampling = true;
			Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_all = Report.intepretGraphEdge(graph, base + "output/" + id + "/result/edge_all_movie.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_all_obj));
			oout_all.writeObject(map_all);
			oout_all.close();
		}

		String map_reduce_obj = base + "output/" + id + "/model/map_reduce_movie";
		HashMap<String, Double> map_reduce = null;
		if (new File(map_reduce_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_obj));
			map_reduce = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.sampling_by_weight = true;
			readObj.weight_limit = 1.521746e-02;
			readObj.rating_scale = 5;
			readObj.rating_start = 1;
			readObj.ditr_interval = 20;
			Graph graph1 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_reduce = Report.intepretGraphEdge(graph1, "edge_reduce_movie.csv");
			ObjectOutputStream oout_reduce = new ObjectOutputStream(new FileOutputStream(map_reduce_obj));
			oout_reduce.writeObject(map_reduce);
			oout_reduce.close();
		}

		String map_reduce_bylen_obj = base + "output/" + id + "/model/map_reduce_bylen";
		HashMap<String, Double> map_reduce_bylen = null;
		if (new File(map_reduce_bylen_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_bylen_obj));
			map_reduce_bylen = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.random_by_length = true;
			readObj.len_limit = Report.getReviewLenLimit(Config.PATH_TEXT, .01);
			Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);

			map_reduce_bylen = Report.intepretGraphEdge(graph, base + "output/" + id + "/result/map_reduce_bylen.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_reduce_bylen_obj));
			oout_all.writeObject(map_reduce_bylen);
			oout_all.close();
		}

		ReadObj readObj = new ReadObj();
		readObj.sample_by_ramdom = true;
		readObj.random_limit = .1;
		Graph graph2 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_sample = Report.intepretGraphEdge(graph2,
				base + "output/" + id + "/result/edge_reduce_sample.csv");

		readObj = new ReadObj();
		readObj.sampling_by_jump = true;
		readObj.len_jump = 10;
		Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byjump = Report.intepretGraphEdge(graph,
				base + "output/" + id + "/result/map_reduce_byjump.csv");

		readObj = new ReadObj();
		readObj.sampling_by_rating = true;
		readObj.weight_rating = Report.getWeightByRating(Config.PATH_TEXT);
		readObj.random_limit = .1;
		Graph graph3 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byrating = Report.intepretGraphEdge(graph3,
				base + "output/" + id + "/result/map_reduce_byrating.csv");

		System.out.print(Helper.getCosinSim(map_all, map_reduce));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_sample));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_bylen));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byjump));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byrating));
	}

	@SuppressWarnings("unchecked")
	public static void evaluateB007ZXK08C() throws Exception {
		String id = "B007ZXK08C";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";

		String map_all_obj = base + "output/" + id + "/model/map_all_movie";
		HashMap<String, Double> map_all = null;
		if (new File(map_all_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_all_obj));
			map_all = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.no_sampling = true;
			Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_all = Report.intepretGraphEdge(graph, base + "output/" + id + "/result/edge_all_movie.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_all_obj));
			oout_all.writeObject(map_all);
			oout_all.close();
		}

		String map_reduce_obj = base + "output/" + id + "/model/map_reduce_movie";
		HashMap<String, Double> map_reduce = null;
		if (new File(map_reduce_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_obj));
			map_reduce = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.sampling_by_weight = true;
			readObj.weight_limit = 0.0113716779;
			readObj.rating_scale = 5;
			readObj.rating_start = 1;
			readObj.ditr_interval = 20;
			Graph graph1 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_reduce = Report.intepretGraphEdge(graph1, "edge_reduce_movie.csv");
			ObjectOutputStream oout_reduce = new ObjectOutputStream(new FileOutputStream(map_reduce_obj));
			oout_reduce.writeObject(map_reduce);
			oout_reduce.close();
		}

		String map_reduce_bylen_obj = base + "output/" + id + "/model/map_reduce_bylen";
		HashMap<String, Double> map_reduce_bylen = null;
		if (new File(map_reduce_bylen_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_bylen_obj));
			map_reduce_bylen = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.random_by_length = true;
			readObj.len_limit = Report.getReviewLenLimit(Config.PATH_TEXT, .02);
			Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);

			map_reduce_bylen = Report.intepretGraphEdge(graph, base + "output/" + id + "/result/map_reduce_bylen.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_reduce_bylen_obj));
			oout_all.writeObject(map_reduce_bylen);
			oout_all.close();
		}

		ReadObj readObj = new ReadObj();
		readObj.sample_by_ramdom = true;
		readObj.random_limit = .2;
		Graph graph2 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_sample = Report.intepretGraphEdge(graph2,
				base + "output/" + id + "/result/edge_reduce_sample.csv");

		readObj = new ReadObj();
		readObj.sampling_by_jump = true;
		readObj.len_jump = 5;
		Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byjump = Report.intepretGraphEdge(graph,
				base + "output/" + id + "/result/map_reduce_byjump.csv");

		readObj = new ReadObj();
		readObj.sampling_by_rating = true;
		readObj.weight_rating = Report.getWeightByRating(Config.PATH_TEXT);
		readObj.random_limit = .2;
		Graph graph3 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byrating = Report.intepretGraphEdge(graph3,
				base + "output/" + id + "/result/map_reduce_byrating.csv");

		System.out.print(Helper.getCosinSim(map_all, map_reduce));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_sample));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_bylen));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byjump));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byrating));
	}

	@SuppressWarnings("unchecked")
	public static void evaluateB000067RC4() throws Exception {
		String id = "B000067RC4";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";

		String map_all_obj = base + "output/" + id + "/model/map_all_movie";
		HashMap<String, Double> map_all = null;
		if (new File(map_all_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_all_obj));
			map_all = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.no_sampling = true;
			Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_all = Report.intepretGraphEdge(graph, base + "output/" + id + "/result/edge_all_movie.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_all_obj));
			oout_all.writeObject(map_all);
			oout_all.close();
		}

		String map_reduce_obj = base + "output/" + id + "/model/map_reduce_movie";
		HashMap<String, Double> map_reduce = null;
		if (new File(map_reduce_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_obj));
			map_reduce = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.sampling_by_weight = true;
			readObj.weight_limit =9.005628e-02;
			readObj.rating_scale = 5;
			readObj.rating_start = 1;
			readObj.ditr_interval = 20;
			Graph graph1 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
			map_reduce = Report.intepretGraphEdge(graph1, "edge_reduce_movie.csv");
			ObjectOutputStream oout_reduce = new ObjectOutputStream(new FileOutputStream(map_reduce_obj));
			oout_reduce.writeObject(map_reduce);
			oout_reduce.close();
		}

		String map_reduce_bylen_obj = base + "output/" + id + "/model/map_reduce_bylen";
		HashMap<String, Double> map_reduce_bylen = null;
		if (new File(map_reduce_bylen_obj).exists()) {
			ObjectInputStream oin = new ObjectInputStream(new FileInputStream(map_reduce_bylen_obj));
			map_reduce_bylen = (HashMap<String, Double>) oin.readObject();
			oin.close();
		} else {
			ReadObj readObj = new ReadObj();
			readObj.random_by_length = true;
			readObj.len_limit = Report.getReviewLenLimit(Config.PATH_TEXT, .02);
			Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);

			map_reduce_bylen = Report.intepretGraphEdge(graph, base + "output/" + id + "/result/map_reduce_bylen.csv");
			ObjectOutputStream oout_all = new ObjectOutputStream(new FileOutputStream(map_reduce_bylen_obj));
			oout_all.writeObject(map_reduce_bylen);
			oout_all.close();
		}

		ReadObj readObj = new ReadObj();
		readObj.sample_by_ramdom = true;
		readObj.random_limit = .2;
		Graph graph2 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_sample = Report.intepretGraphEdge(graph2,
				base + "output/" + id + "/result/edge_reduce_sample.csv");

		readObj = new ReadObj();
		readObj.sampling_by_jump = true;
		readObj.len_jump = 5;
		Graph graph = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byjump = Report.intepretGraphEdge(graph,
				base + "output/" + id + "/result/map_reduce_byjump.csv");

		readObj = new ReadObj();
		readObj.sampling_by_rating = true;
		readObj.weight_rating = Report.getWeightByRating(Config.PATH_TEXT);
		readObj.random_limit = .2;
		Graph graph3 = Report.readData(Config.PATH_TEXT, Config.PATH_WEIGHT, readObj);
		HashMap<String, Double> map_reduce_byrating = Report.intepretGraphEdge(graph3,
				base + "output/" + id + "/result/map_reduce_byrating.csv");

		System.out.print(Helper.getCosinSim(map_all, map_reduce));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_sample));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_bylen));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byjump));
		System.out.print("-");
		System.out.print(Helper.getCosinSim(map_all, map_reduce_byrating));
	}
}
