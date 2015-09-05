package review_mining.process;

import org.junit.Test;

import edu.pitt.review_mining.process.Word;

public class WordTest {
	
	@Test
	public void testProcessSentence() {
		new Word().processSentence("red red sauce is pretty good, but I like white meat");
		assert(true);
	}

	@Test
	public void testProcessClause() {
		new Word().processClause("red red sauce is pretty good, but I like white meat");
		assert(true);
	}

}