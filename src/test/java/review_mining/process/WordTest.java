package review_mining.process;

import org.junit.Test;

import edu.pitt.review_mining.process.Word;

public class WordTest {

	@Test
	public void testDocProcess() {
		new Word().processDocument("I am student.");
		assert(true);
	}

}
