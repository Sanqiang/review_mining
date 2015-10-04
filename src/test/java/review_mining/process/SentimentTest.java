package review_mining.process;

import org.junit.Test;

import edu.pitt.review_mining.utility.SentimentUtility;

public class SentimentTest {
	@Test
	public void testSentiment() {
		String line = "it it delicious.";
		int code = SentimentUtility.getSentiment(line);
		System.out.println(code);
	}
}
