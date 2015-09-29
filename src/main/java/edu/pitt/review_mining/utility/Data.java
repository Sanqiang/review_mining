package edu.pitt.review_mining.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;
import org.json.JSONTokener;

public class Data {
	static void processAmazon(String product_id) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\git\\complete.json")));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while (null != (line = reader.readLine())) {
				JSONTokener tokener = new JSONTokener(line);
				JSONObject obj = new JSONObject(tokener);
				String asin = obj.getString("asin");
				//System.out.println(line);
				if (product_id.equals(asin)) {
					System.out.println(line);
					String reviewText = obj.getString("reviewText");
					long unixReviewTime = obj.getLong("unixReviewTime");
					int rating = (int) (obj.getDouble("overall"));
					sb.append(rating).append("\t").append(unixReviewTime).append("\t").append(reviewText).append("\n");
				}
			}
			reader.close();

			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("C:\\git\\" + product_id + "_plain.txt")));
			writer.write(sb.toString());
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	static void processAmazon2() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\git\\B000CNB4LE.txt")));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while (null != (line = reader.readLine())) {
				String[] items = line.split("\t");
				sb.append(items[0]).append("\t").append(items[1]).append("\n");
			}
			reader.close();

			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("C:\\git\\B000CNB4LE__simple_plain.txt")));
			writer.write(sb.toString());
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		processAmazon2();
	}
}
