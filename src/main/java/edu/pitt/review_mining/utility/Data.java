package edu.pitt.review_mining.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
				// System.out.println(line);
				if (product_id.equals(asin)) {
					System.out.println(line);
					String reviewText = obj.getString("reviewText");
					long unixReviewTime = obj.getLong("unixReviewTime");
					int rating = (int) (obj.getDouble("overall"));
					sb.append(rating).append("\t").append(unixReviewTime).append("\t").append(reviewText).append("\n");
				}
			}
			reader.close();

			BufferedWriter writer = new BufferedWriter(
					new FileWriter(new File("C:\\git\\" + product_id + "_plain.txt")));
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
			ArrayList<ReviewObj> list = new ArrayList<>();
			while (null != (line = reader.readLine())) {
				String[] items = line.split("\t");
				if (items.length != 3) {
					System.err.println(line);
					continue;
				}
				ReviewObj obj = new ReviewObj();
				obj.text = items[2];
				obj.time = Long.parseLong(items[1]);
				obj.rating = Integer.parseInt(items[0]);
				list.add(obj);
			}
			reader.close();

			Collections.sort(list,new Comparator<ReviewObj>() {

				@Override
				public int compare(ReviewObj o1, ReviewObj o2) {
					long time1 = o1.time;
					long time2  = o2.time;
					return (int) (time2-time1);
				}
			});
			
			StringBuilder sb = new StringBuilder();
			BufferedWriter writer = new BufferedWriter(
					new FileWriter(new File("C:/git/review_mining/r/B000CNB4L_new.txt")));
			for (ReviewObj reviewObj : list) {
				sb.append(reviewObj);
			}
			writer.write(sb.toString());
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// http://www.cs.cornell.edu/people/pabo/movie-review-data
	static void processMovie() {
		final String path = "C:/git/movie/";
		HashMap<Long, ReviewObj> movies = new HashMap<>();
		try {

			BufferedReader reader_id = new BufferedReader(
					new FileReader(new File(path + "scale_data/scaledata/Dennis+Schwartz/id.Dennis+Schwartz"))),
					reader_rating = new BufferedReader(new FileReader(
							new File(path + "scale_data/scaledata/Dennis+Schwartz/label.4class.Dennis+Schwartz"))),
					reader_text = new BufferedReader(new FileReader(
							new File(path + "scale_data/scaledata/Dennis+Schwartz/subj.Dennis+Schwartz")));

			while (true) {
				String id_line = reader_id.readLine();
				String rating_line = reader_rating.readLine();
				String text_line = reader_text.readLine();
				if (id_line == null && rating_line == null && text_line == null) {
					break;
				}
				long id = Long.valueOf(id_line);
				int rating = (int) (Double.parseDouble(rating_line) * 1);
				ReviewObj obj = new ReviewObj();
				obj.id = id;
				obj.rating = rating;
				obj.text = text_line;
				movies.put(id, obj);
			}

			SimpleDateFormat sdf = new SimpleDateFormat("M/d/yy");
			for (long id : movies.keySet()) {
				reader_text = new BufferedReader(new FileReader(new File(
						path + "scale_whole_review/scale_whole_review/Dennis+Schwartz/txt.parag/" + id + ".txt")));
				while (true) {
					String line_text = reader_text.readLine();
					if (line_text == null) {
						break;
					}
					if (line_text.startsWith("REVIEWED ON")) {
						String line_date = line_text.substring(12);
						try {
							long date = sdf.parse(line_date).getTime();
							movies.get(id).time = date;
						} catch (java.text.ParseException e) {
							System.out.println(id);
							e.printStackTrace();
						}
					}
				}
			}

			ArrayList<ReviewObj> movie_col = new ArrayList<>(movies.values());
			Collections.sort(movie_col, new Comparator<ReviewObj>() {
				@Override
				public int compare(ReviewObj o1, ReviewObj o2) {
					long date1 = o1.time;
					long date2 = o2.time;
					if (date2 > date1) {
						return 1;
					} else if (date2 < date1) {
						return -1;
					} else {
						return 0;
					}
				}
			});

			StringBuilder sb = new StringBuilder();
			for (ReviewObj movieObj : movie_col) {
				sb.append(movieObj);
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(new File("Dennis+Schwartz.txt")));
			writer.write(sb.toString());
			writer.close();
			
			reader_id.close();
			reader_rating.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		processAmazon2();
		// processMovie();
	}
}

class ReviewObj {
	public long id;
	public String text;
	public int rating;
	public long time;

	@Override
	public String toString() {
		return rating + "\t" + time + "\t" + text + "\n";
		// return rating + "\t" + time;
	}

}