package review_mining.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.junit.Test;

import edu.pitt.review_mining.graph.Node;
import edu.pitt.review_mining.process.Process;
import edu.stanford.nlp.trees.Tree;

public class ProcessTest {

	@Test
	public void testProcessReview() {
		String review = "On my way to Pittsburgh one of the places that I was really looking forward to was Gaucho. We came here on a Saturday, standing in the line with a 12 pack as the smell of their wood fired grill was taking over the whole street. Apparently there is always a line here. We waited for 45 minutes (and yes it is worth it). As I was standing in the line, I couldn't help myself not hearing how locals exalt this place. I ordered their filet sandwich and the rosemary braised beef sandwich (for to go). The winner was the filet, but with a small difference. The first bite that I took from my filet sandwich, was overwhelmingly tasty. The meat was cooked to perfection, and the roasted peppers in the sandwich had this unbelievable smoky flavor which added a lot to the sandwich. The sandwiches here are served on ciabatta which makes them even more delicious. ";
		Process process = new Process();
		ArrayList<Node> candidates_nodes = process.processReviews(review);
		Collections.sort(candidates_nodes, new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				o1.setScore(Double.isNaN(o1.getScore()) ? 0 : o1.getScore());
				o2.setScore(Double.isNaN(o2.getScore()) ? 0 : o2.getScore());
				if (o2.getScore() >= o1.getScore()) {
					return 1;
				} else {
					return -1;
				}
			}
		});
		for (Node node : candidates_nodes)

		{
			System.out.println(node.getLemma() + " : " + node.getScore());
		}
		assert(true);

	}

	// @Test
	public void testSegSentence() {
		Process process = new Process();
		ArrayList<String> sents = process.segSentence(
				"CA$H ONLY!!! Wait in line, pick what you want, pay the man, and eat to your heart's desire. This easily counts as two meals which is such a steal. Not to mention it is very yummy whether you are super hungry or not. The combo meal of chicken, lamb, rice, and veggies may not be the most photogenic for Instagram, but it definitely fits the bill to be the winner of a superlative named Super Cheap & Extra Good.");
		for (String sent : sents) {
			System.out.println(sent);
		}
	}

	// @Test
	public void testProcessSentence() {
		Process word = new Process();
		word.processSentence(
				"CA$H ONLY!!! Wait in line, pick what you want, pay the man, and eat to your heart's desire. This easily counts as two meals which is such a steal. Not to mention it is very yummy whether you are super hungry or not. The combo meal of chicken, lamb, rice, and veggies may not be the most photogenic for Instagram, but it definitely fits the bill to be the winner of a superlative named Super Cheap & Extra Good.");
		assert(true);
	}

	// @Test
	public void testPreprocessSentence() {
		Process word = new Process();
		System.out.println(word.preprocessSentence("i like blue cheese."));
		assert(true);
	}

	// @Test
	public void testDetectPhrase() {
		Process word = new Process();
		System.out.println(word.detectPhrase("new york pizza", false, true));
		System.out.println(word.detectPhrase("blue cheese", false, true));
		System.out.println(word.detectPhrase("bLUe cheese", false, true));
		assert(true);
	}

	// @Test
	public void testSplitSentence() {
		Process word = new Process();
		Tree tree = word.filterSentence(
				"came here after a club bc more than one person recommended I get chicken and rice from this infamous corner.");
		ArrayList<Tree> trees = word.splitTree(tree);
		for (Tree child_tree : trees) {
			System.out.println(child_tree);
		}
		assert(true);
	}

	// @Test
	public void testFilterSentence() {
		Process word = new Process();
		// word.processSentence("red red sauce is pretty good, but I like white
		// meat");
		Tree tree = word.filterSentence(
				"came here after a club bc more than one person recommended I get chicken and rice from this infamous corner.");
		System.out.println(tree);
		assert(true);
	}

	// @Test
	public void testProcessClause() {
		new Process().generateDependencyGraph("red red sauce is pretty good, but I like white meat");
		assert(true);
	}

}