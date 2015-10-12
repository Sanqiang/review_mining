package edu.pitt.review_mining.process.rating;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class KalmanUtility {
	// final static double[] rating_weight = { 1, 1, 1, 1 };

	int _scale = 5;
	double[][] _weight_matrix = null;
	int _num_times;
	int _interval;

	public KalmanUtility(String path, int interval, int scale) {
		try {
			this._interval = interval;
			this._scale = scale;
			BufferedReader reader = new BufferedReader(new FileReader(new File(path)));
			String line = null;
			int col_id = 0;
			while (null != (line = reader.readLine())) {
				String[] weights = line.split(" ");
				if (this._weight_matrix == null) {
					this._weight_matrix = new double[scale][weights.length];
				}
				for (int row_id = 0; row_id < weights.length; row_id++) {
					this._weight_matrix[col_id][row_id] = Double.parseDouble(weights[row_id]);
				}
				col_id++;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public double getWeight(int review_idx, int rating) {
		int row_idx = getRowIdx(review_idx);
		if (row_idx >= this._weight_matrix[0].length || rating - 1 >= this._weight_matrix.length) {
			System.out.println(review_idx);
			System.out.println(this._weight_matrix[0].length);
		}
		return this._weight_matrix[rating
				- 1][row_idx] /** rating_weight[rating] */
		;
	}

	private int getRowIdx(int review_idx) {
		return (review_idx - 1) / this._interval;
	}

}
