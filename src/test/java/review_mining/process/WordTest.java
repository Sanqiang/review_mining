package review_mining.process;

import org.junit.Test;

import edu.pitt.review_mining.process.Word;

public class WordTest {

	@Test
	public void testDocProcess() {
		new Word().processDocument("there is good restaurant.");
		new Word().processDocument("I am doctor.");
		new Word().processDocument("The food is really good.");
		new Word().processDocument("The food tasts good.");
		new Word().processDocument("I like the delicous food.");
		assert(true);
	}

}