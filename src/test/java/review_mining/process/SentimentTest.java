package review_mining.process;

import org.junit.Test;

import edu.pitt.review_mining.utility.SentimentUtility;

public class SentimentTest {
	@Test
	public void testSentiment() {
		String line = "This isn't even close to the same taste. It's disgusting.";
		int code = SentimentUtility.getSentiment(line);
		System.out.println(code);
	}
}
