package review_mining.process;

import org.junit.Test;

import edu.pitt.review_mining.process.WordNet;

public class WordNetTest {
	@Test
	public void testMeasureNounSimilarity() {
		WordNet wn = new WordNet();
		int dist = wn.measureNounSimilarity("pizza", "topping");
		System.out.println(dist);
		assert(true);
	}

}
