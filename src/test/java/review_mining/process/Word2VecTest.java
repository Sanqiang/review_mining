package review_mining.process;

import org.junit.Test;

import edu.pitt.review_mining.process.Word2VecUtility;

@Deprecated
public class Word2VecTest {

	@Test
	public void testBuildWord2VecModel() {
		// new Word2VecUtility().generatePureDataset();
		new Word2VecUtility().build();
		// new Word2VecUtility().test();
	}
}
