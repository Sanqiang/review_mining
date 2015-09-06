package review_mining.process;

import org.junit.Test;

import edu.pitt.review_mining.process.Word;

public class WordTest {
	
	@Test
	public void testProcessSentence() {
		Word word = new Word();
		//word.processSentence("red red sauce is pretty good, but I like white meat");
		word.filterSentence("came here after a club bc more than one person recommended I get chicken and rice from this infamous corner.");
		assert(true);
	}

	//@Test
	public void testProcessClause() {
		new Word().processClause("red red sauce is pretty good, but I like white meat");
		assert(true);
	}

}