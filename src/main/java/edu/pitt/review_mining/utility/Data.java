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
			BufferedReader reader = new BufferedReader(new FileReader(new File("C:\\git\\user_dedup.json.gz2")));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while (null != (line = reader.readLine())) {
				JSONTokener tokener = new JSONTokener(line);
				JSONObject obj = new JSONObject(tokener);
				String asin = obj.getString("asin");
				if (product_id.equals(asin)) {
					String reviewText = obj.getString("reviewText");
					long unixReviewTime = obj.getLong("unixReviewTime");
					int rating = (int) (obj.getDouble("overall"));
					sb.append(rating).append("\t").append(unixReviewTime).append("\t").append(reviewText).append("\t")
							.append(unixReviewTime).append("\n");
				}
			}
			reader.close();

			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("C:\\git\\" + product_id + ".txt")));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		processAmazon("B000CNB4LE");
	}
}
