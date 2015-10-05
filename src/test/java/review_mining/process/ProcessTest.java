package review_mining.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.junit.Test;

import edu.pitt.review_mining.graph.Edge;
import edu.pitt.review_mining.graph.Graph;
import edu.pitt.review_mining.graph.Node;
import edu.pitt.review_mining.process.text.CLI;
import edu.pitt.review_mining.process.text.ProcessUtility;
import edu.pitt.review_mining.process.text.Report;
import edu.pitt.review_mining.utility.Helper;
import edu.pitt.review_mining.utility.PartOfSpeech;
import edu.stanford.nlp.trees.Tree;

public class ProcessTest {
	// @Test
	public void testRuleAddCount() {
		ProcessUtility pu = new ProcessUtility();
		pu.processReviews(
				"I can't believe 53rd and 6th halal has its own review thingamajig. A part of me doesn't want to rave about how good it is because I don't want the line getting any longer than it already is. But I won't be selfish. Go for the combo and their hot sauce is HOT."
						.toLowerCase(),
				0, 0);
		pu.processReviews("food is delicious.", 1, 0);
		pu.processReviews("food is delicious.", 2, 0);
		Graph graph = pu.getGraph();
		Report.intepretGraph(graph);
		System.out.println(graph);
		assert(true);
	}

	// test 4+ types of rules
	@Test
	public void testRule() {
		ProcessUtility pu = new ProcessUtility();
		// SingleAmod, //red food is good.
		// AmodSubj, //chicken is delicious food.
		// SingleSubj, //food is delicious.
		// ConjAndComp; //The chicken and rice with white sauce is delicious.
		// chicken rice is delicious.
		// test following by tune comment !
		// Graph graph = pu.generateDependencyGraph("red food is good.", 0,0);
		// Graph graph = pu.generateDependencyGraph("chicken is delicious
		// food.", 0,0);
		// Graph graph = pu.generateDependencyGraph("food is delicious.", 0,0);
		// Graph graph = pu.generateDependencyGraph("the chicken and rice with
		// white sauce is delicious.", 0, 0, 0);
		pu.processReviews(
				"it tastes nothing like coconut water",
				0, 0);
		for (Node node : pu.getGraph().getNodes()) {
			// if (node.getPOS() == PartOfSpeech.NOUN) {
			System.out.println();
			System.out.print(node.getLemma() + ":");
			for (Edge edge : node.getOutcomingEdges()) {
				Node another_node = edge.getOtherNode(node);
				System.out.print(edge.getDependencyType().name() + ":");
				System.out.print(another_node.getLemma() + "\t");
			}
			// }
		}
		assert(true);
	}

	// @Test
	public void testProcessReview() {
		String review = "This restaurant deserves all the success and lines out its door. One walks in and is instantly greeted by a giant chalkboard of food items listed divided into  to meats, salads, sandwiches, and sides. I decisively decided on their steak sandwich with filet and then a 6oz meat plate of flank (and it comes with a small side salad, toast, and tiny portion of grilled veggies). Depending on the cuts of meat prices vary. Strip steak is the cheapest to filet being the priciest. Ordered both meats to be cooked medium rare. ";
		ProcessUtility process = new ProcessUtility();
		process.processReviews(review, 0, 0);
		assert(true);

	}

	// @Test
	public void testSegSentence() {
		ProcessUtility process = new ProcessUtility();
		ArrayList<String> sents = process.segSentence(
				"CA$H ONLY!!! Wait in line, pick what you want, pay the man, and eat to your heart's desire. This easily counts as two meals which is such a steal. Not to mention it is very yummy whether you are super hungry or not. The combo meal of chicken, lamb, rice, and veggies may not be the most photogenic for Instagram, but it definitely fits the bill to be the winner of a superlative named Super Cheap & Extra Good.");
		for (String sent : sents) {
			System.out.println(sent);
		}
	}

	// @Test
	public void testProcessSentence() {
		ProcessUtility word = new ProcessUtility();
		word.processSentence(
				"CA$H ONLY!!! Wait in line, pick what you want, pay the man, and eat to your heart's desire. This easily counts as two meals which is such a steal. Not to mention it is very yummy whether you are super hungry or not. The combo meal of chicken, lamb, rice, and veggies may not be the most photogenic for Instagram, but it definitely fits the bill to be the winner of a superlative named Super Cheap & Extra Good.",
				0, 0, 0);
		assert(true);
	}

	// @Test
	public void testPreprocessSentence() {
		ProcessUtility word = new ProcessUtility();
		System.out.println(word.preprocessSentence("i like blue cheese."));
		assert(true);
	}

	// @Test
	public void testDetectPhrase() {
		ProcessUtility word = new ProcessUtility();
		System.out.println(word.detectPhrase("new york pizza", false, true));
		System.out.println(word.detectPhrase("blue cheese", false, true));
		System.out.println(word.detectPhrase("bLUe cheese", false, true));
		assert(true);
	}

	// @Test
	// public void testSplitSentence() {
	// ProcessUtility word = new ProcessUtility();
	// Tree tree = word.filterSentence(
	// "came here after a club bc more than one person recommended I get chicken
	// and rice from this infamous corner.");
	// ArrayList<Tree> trees = word.splitTree(tree);
	// for (Tree child_tree : trees) {
	// System.out.println(child_tree);
	// }
	// assert(true);
	// }

	// @Test
	public void testFilterSentence() {
		ProcessUtility word = new ProcessUtility();
		// word.processSentence("red red sauce is pretty good, but I like white
		// meat");
		String sent = "I order the rice and chicken in first time";// "Go for
																	// the combo
																	// and their
																	// hot sauce
																	// is HOT.";
		sent = word.filterSentence(sent);
		System.out.println(sent);
		assert(true);
	}

	// @Test
	public void testGenerateDependencyGraph() {
		new ProcessUtility().generateDependencyGraph("red red sauce is pretty good, but I like white meat", 0, 0, 0);
		assert(true);
	}

}