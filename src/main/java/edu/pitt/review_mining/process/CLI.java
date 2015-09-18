package edu.pitt.review_mining.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CLI {
	public void readData(String path) {
		Process process = new Process();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(path)));
			String review = null;
			while ((review = reader.readLine()) != null) {
				process.processReviews(review);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}

	}
}
