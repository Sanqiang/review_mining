package edu.pitt.review_mining.process.text;

import edu.pitt.review_mining.process.eva.SentEva;

public class CLI {

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 1000; i++) {
			SentEva.evaluateB000067RC4();
			//SentEva.evaluateB007ZXK08C(); //fail
			//SentEva.evaluateB0036FTF4S();
			//SentEva.evaluateB0064CL1T2();
			//SentEva.evaluateB001FA1O0O();
			//SentEva.evaluateB00EOE0WKQ();
			//SentEva.evaluateMovieReview();
			//SentEva.evaluateCoconutWater();
			System.out.println();
		}
		
		// Report.generateReviewReport(Config.PATH_TEXT);
	}
}
