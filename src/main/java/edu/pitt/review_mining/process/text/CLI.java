package edu.pitt.review_mining.process.text;

import edu.pitt.review_mining.process.eva.SentEva;

public class CLI {

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 1000; i++) {
			SentEva.evaluateB00EOE0WKQ();
			//SentEva.evaluateMovieReview();
			//SentEva.evaluateCoconutWater();
			System.out.println();
		}
		
		// Report.generateReviewReport(Config.PATH_TEXT);
	}
}
