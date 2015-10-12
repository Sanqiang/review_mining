package edu.pitt.review_mining.process.text;

import edu.pitt.review_mining.process.eva.SentEva;

public class CLI {

	public static void main(String[] args) throws Exception {
		SentEva.evaluateMovieReview();

		// Report.generateReviewReport(Config.PATH_TEXT);
	}
}
