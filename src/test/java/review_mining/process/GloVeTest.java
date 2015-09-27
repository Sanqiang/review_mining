package review_mining.process;

import org.junit.Test;

import edu.pitt.review_mining.process.text.GloVe;
import edu.pitt.review_mining.utility.Config;

public class GloVeTest {
	
	// @Test
	public void testbuildIndex() {
		GloVe gv = new GloVe();
		gv.buildIndex(Config.PATH_WIKI_300D, Config.PATH_INDEX);
		assert(true);
	}

	// @Test
	public void testQueryWord() {
		GloVe gv = new GloVe();
		double[] vector = gv.queryVector(",", Config.PATH_INDEX);
		for (double v : vector) {
			System.out.print(v + " ");
		}
		assert(true);
	}

	@Test
	public void testSim() {
		GloVe gv = new GloVe();
		System.out.println(gv.sim("food", "smell"));
		System.out.println(gv.sim("food", "car"));
		System.out.println(gv.sim("pizza", "time"));
		System.out.println(gv.sim("sauce", "restaurant"));
		System.out.println(gv.sim("meatball", "pizza"));
		System.out.println(gv.sim("restaurant", "pizza"));
		System.out.println(gv.sim("topping", "pizza"));
		System.out.println(gv.sim("computer", "pizza"));
		System.out.println(gv.sim("car", "pizza"));
		assert(true);
	}
}
