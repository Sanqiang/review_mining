package edu.pitt.review_mining.process;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import edu.pitt.review_mining.utility.Config;

public class GloVe {
	public GloVe() {
	}

	public void buildIndex(String data_file, String path_index, int dimension) {
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(data_file)), "UTF-8"));
			RandomAccessFile rafw = new RandomAccessFile(path_index + "word", "rw"),
					rafp = new RandomAccessFile(path_index + "pos", "rw");
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] items = line.split(" ");
				String word = items[0];
				rafw.writeUTF(word);
				for (int i = 0; i < dimension; i++) {
					double feature = Double.parseDouble(items[i + 1]);
					rafp.writeDouble(feature);
				}
			}
			reader.close();
			rafp.close();
			rafw.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void buildIndex(String data_file, String path_index) {
		buildIndex(data_file, path_index, 300);
	}

	public double[] queryVector(String query_word, String path_index) {
		return queryVector(query_word, path_index, 300);
	}

	public double[] queryVector(String query_word, String path_index, int dimension) {
		double[] vector = new double[dimension];
		try {
			RandomAccessFile rafw = new RandomAccessFile(path_index + "word", "rw"),
					rafp = new RandomAccessFile(path_index + "pos", "rw");
			String word = null;
			int pos = 0;

			while (rafw.getFilePointer() < rafw.length()) {
				word = rafw.readUTF();
				if (word.equals(query_word)) {
					rafp.seek(pos * dimension * 8); // size of double is 4
					for (int i = 0; i < vector.length; i++) {
						vector[i] = rafp.readDouble();
					}
					break;
				}
				++pos;
			}
			rafw.close();
			rafp.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return vector;
	}

	public double sim(String word1, String word2) {
		double sim_score = 0d, norm1 = 0d, norm2 = 0d;
		double[] vector1 = queryVector(word1,Config.PATH_INDEX), vector2 = queryVector(word2,Config.PATH_INDEX);
		for (int i = 0; i < vector1.length; i++) {
			norm1 += vector1[i] * vector1[i];
			norm2 += vector2[i] * vector2[i];
			sim_score += vector1[i] * vector2[i];
		}
		sim_score = sim_score / (Math.sqrt(norm1) * Math.sqrt(norm2));
		return sim_score;
	}

}
