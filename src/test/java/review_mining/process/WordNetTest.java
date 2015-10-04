package review_mining.process;

import org.junit.Test;

import edu.pitt.review_mining.process.text.WordNetUtility;

public class WordNetTest {
	//@Test
	public void testMeasureNounSimilarity() {
		WordNetUtility wn = new WordNetUtility();
		int dist = wn.measureNounSimilarity("pizza", "topping");
		System.out.println(dist);
		assert(true);
	}
	
	@Test
	public void testGetWord(){
		WordNetUtility wn = new WordNetUtility();
		System.out.println(wn.canBeNoun("disgusting"));
		assert(true);
	}

}
