package edu.pitt.review_mining.process;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.embeddings.wordvectors.WordVectors;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.LineSentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.sentenceiterator.SentencePreProcessor;
import org.deeplearning4j.text.tokenization.tokenizer.TokenPreProcess;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.EndingPreProcessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.json.JSONObject;
import org.tartarus.snowball.ext.PorterStemmer;

import edu.pitt.review_mining.utility.Config;

@Deprecated
public class Word2VecUtility {

	@Deprecated
	public void generatePureDataset() {
		try {
			int cached = 10000;
			BufferedReader reader = new BufferedReader(new FileReader(new File(Config.PATH_AMAZON_REVIEW)));
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(Config.PATH_AMAZON_REVIEW + "2")));
			String line;
			while ((line = reader.readLine()) != null) {
				JSONObject obj = new JSONObject(line);
				String text = obj.getString("reviewText");
				writer.write(text);
				if (cached-- < 0) {
					writer.flush();
					cached = 10000;
				}
			}
			writer.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	ArrayList<String> loadData() {
		ArrayList<String> sentences = new ArrayList<>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(Config.PATH_AMAZON_REVIEW)));
			String line;
			while ((line = reader.readLine()) != null) {
				JSONObject obj = new JSONObject(line);
				sentences.add(obj.getString("reviewText"));
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sentences;
	}

	public void build() {
		System.err.println("Loading Data.");
		SentenceIterator iter = null;
		try {
			// ClassPathResource resource = new
			// ClassPathResource("complete.txt");
			iter = new LineSentenceIterator(new File("complete.txt"));
			System.err.println("Processing Sentences.");
			iter.setPreProcessor(new SentencePreProcessor() {
				private static final long serialVersionUID = 1L;

				@Override
				public String preProcess(String sentence) {
					return sentence.toLowerCase();
				}
			});
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		System.err.println("Processing Tokenizing.");
		final EndingPreProcessor preProcessor = new EndingPreProcessor();
		TokenizerFactory tokenizer = new DefaultTokenizerFactory();
		tokenizer.setTokenPreProcessor(new TokenPreProcess() {
			@Override
			public String preProcess(String token) {
				if (!Character.isLetter(token.charAt(0))) {
					// agressive non-character word
					return "<NON_WORD>";
				}
				token = token.toLowerCase();
				PorterStemmer stemmer = new PorterStemmer();
				stemmer.setCurrent(token); // set string you need to stem
				stemmer.stem(); // stem the word
				token = stemmer.getCurrent();// get the stemmed word
				return token;
			}
		});

		int batchSize = 1000;
		int iterations = 30;
		int layerSize = 300;
		System.err.println("Building Model.");
		Word2Vec vec = new Word2Vec.Builder().batchSize(batchSize) // # words
																	// per
																	// minibatch.
				.sampling(1e-5) // negative sampling. drops words out
				.minWordFrequency(5) //
				.useAdaGrad(false) //
				.layerSize(layerSize) // word feature vector size
				.iterations(iterations) // # iterations to train
				.learningRate(0.025) //
				.minLearningRate(1e-2) // learning rate decays wrt # words.
										// floor learning
				.negativeSample(10) // sample size 10 words
				.iterate(iter) //
				.tokenizerFactory(tokenizer).build();
		try {
			vec.fit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.err.println("Save Model.");
		try {
			WordVectorSerializer.writeWordVectors(vec, Config.PATH_WORD2VEC_MODEL);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void test() {
		try {
			WordVectors vec = WordVectorSerializer.loadTxtVectors(new File(Config.PATH_WORD2VEC_MODEL));// (Config.PATH_WORD2VEC_MODEL,true);
			Collection<String> lst = vec.wordsNearest("year", 10);
			System.out.println(lst);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
