package edu.pitt.review_mining.process.text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.hadoop.mapred.gethistory_jsp;

import com.hazelcast.config.Config;

import edu.pitt.review_mining.utility.PartOfSpeech;
import scala.annotation.meta.field;

public class Temp {
	static void data() {
		try {
			ProcessUtility utility = new ProcessUtility();
			BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\git\\yelp_Union Grill_plain.txt")));
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] items = line.split("\t");
				if (items.length == 3) {
					int rating = Integer.parseInt(items[0]);
					long time = Long.parseLong(items[1]);
					String review = items[2];
					utility.processSentence(review, 1, 1, 1, 1, time);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void matrix() throws Exception {
		HashMap<String, String> aspect_mapper = new HashMap<>();
		aspect_mapper.put("atmospher", "atmospher");
		aspect_mapper.put("food", "food");
		aspect_mapper.put("place", "place");
		aspect_mapper.put("burger", "food");
		aspect_mapper.put("servic", "servic");
		aspect_mapper.put("sandwich","food");
		aspect_mapper.put("meal", "food");
		
		StringBuilder sb = new StringBuilder();
		SentiWordNetUtility sentiWordNetUtility = new SentiWordNetUtility(
				edu.pitt.review_mining.utility.Config.PATH_WN_SENT);
		BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\git\\review_mining\\sl.txt")));
		String line = null;
		int reviewer_idx = 0;
		HashMap<String, Integer> aspect_to_idx = new HashMap<>();
		while ((line = reader.readLine()) != null) {
			++reviewer_idx;
			String[] items = line.split("\t");
			if (items.length != 2) {
				continue;
			}

			String[] pairs = items[1].split("\\|\\|");
			for (String pair : pairs) {
				String[] asp_opin = pair.split("::");
				String aspect = asp_opin[0];
				String opinion = asp_opin[1];
				double sem = sentiWordNetUtility.extract(opinion, PartOfSpeech.ADJECTIVE);
				String sem_tag = null;
				if (sem > 0) {
					sem_tag = "POS";
				} else if (sem < 0) {
					sem_tag = "NEG";
				}
				if (sem_tag == null && !aspect_to_idx.containsKey(aspect)) {
					continue;
				}
				String aspect_key = aspect_mapper.get(aspect) + "_" + sem_tag;
				if (!aspect_to_idx.containsKey(aspect_key)) {
					aspect_to_idx.put(aspect_key, aspect_to_idx.size() + 1);
				}
				int aspect_idx = aspect_to_idx.get(aspect_key);
				sb.append(aspect_idx).append(" ").append(reviewer_idx).append("\n");
			}
		}

		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("output_temp_sm.txt")));
		writer.write(sb.toString());
		writer.close();

		writer = new BufferedWriter(new FileWriter(new File("output_temp_sm_aspect.txt")));
		for (String aspect : aspect_to_idx.keySet()) {
			writer.write(aspect + "_");
			writer.write(aspect_to_idx.get(aspect).toString());
			writer.flush();
			writer.write("\n");
		}
	}

	public static void main(String[] args) throws Exception {
		//data();
		matrix();
	}
}
