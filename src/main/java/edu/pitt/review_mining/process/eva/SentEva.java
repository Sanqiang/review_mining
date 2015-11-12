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
			readObj.weight_limit = 9.005628e-02;
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
	public static void evaluateB002CVTT3Y() throws Exception {
		String id = "B002CVTT3Y";
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
			readObj.weight_limit =0.1240508577;
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

	public static void evaluateB0006VJ6TO() throws Exception {
		String id = "B0006VJ6TO";
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
			readObj.weight_limit =  0.1127779916;//0.1692160615;
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

	public static void evaluateB004XZHY34() throws Exception {
		String id = "B004XZHY34";
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
			readObj.weight_limit =0.0254007640;
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

	public static void evaluateB000056J7K() throws Exception {
		String id = "B000056J7K";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit =0.0185350650;
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

	public static void evaluateB003EUPCZG() throws Exception {
		String id = "B003EUPCZG";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit = 0.0166846339;
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
	
	public static void evaluate148234873X() throws Exception {
		String id = "148234873X";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit =  0.0273001023 ;
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

	public static void evaluateB00A35WYBA() throws Exception {
		String id = "B00A35WYBA";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit =  0.0214502467 ;
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
	

	public static void evaluate0399158820() throws Exception {
		String id = "0399158820";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit =  1.713409e-02 ;
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
	
	public static void evaluate0312626681() throws Exception {
		String id = "0312626681";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit = 1.979467e-02;
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

	public static void evaluateB008476HBM() throws Exception {
		String id = "B008476HBM";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit = 1.804473e-02;
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

	public static void evaluateB0055ECOUA() throws Exception {
		String id = "B0055ECOUA";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit = 0.0166931024;
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

	public static void evaluateB00074H6RO() throws Exception {
		String id = "B00074H6RO";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit = 0.0145663962;
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

	public static void evaluateB001BB2LMM() throws Exception {
		String id = "B001BB2LMM";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit =0.0293778544;
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

	public static void evaluateB000092YPR() throws Exception {
		String id = "B000092YPR";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit =0.0271277093 ;
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

	public static void evaluateB00974L3GU() throws Exception {
		String id = "B00974L3GU";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit = 2.444617e-02;
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

	public static void evaluateB003MUD35O() throws Exception {
		String id = "B003MUD35O";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit =1.259764e-02;
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

	public static void evaluate0778314340() throws Exception {
		String id = "0778314340";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit = 0.0255531289;
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

	public static void evaluate0441008534() throws Exception {
		String id = "0441008534";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit = 2.096014e-02;
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
	
	public static void evaluateB004XFYN9M() throws Exception {
		String id = "B004XFYN9M";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit = 0.0301025294;
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
	
	public static void evaluate0316211079() throws Exception {
		String id = "0316211079";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit = 0.0161004635;
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

	public static void evaluateB0037NKDSG() throws Exception {
		String id = "B0037NKDSG";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit = 2.389679e-02;
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

	public static void evaluateB000XV7ORI() throws Exception {
		String id = "B000XV7ORI";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit = 0.0194125127;
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

	public static void evaluateB008D4XEUI() throws Exception {
		String id = "B008D4XEUI";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit =  0.026422798;
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

	public static void evaluateB008HY8XTG() throws Exception {
		String id = "B008HY8XTG";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit =  1.959416e-02;
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

	public static void evaluateB00F8K9MZQ() throws Exception {
		String id = "B00F8K9MZQ";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit =  2.386010e-02;
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

	public static void evaluateB001JKTTVQ() throws Exception {
		String id = "B001JKTTVQ";
		Config.PATH_TEXT = base + "r/" + id + "_new.txt";
		Config.PATH_WEIGHT = base + "r/weight_" + id + ".txt";
		new File(base + "output/" + id ).mkdir();
		new File(base + "output/" + id + "/model/").mkdir();
		new File(base + "output/" + id + "/result").mkdir();
		
		
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
			readObj.weight_limit =  0.0243813928;
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
