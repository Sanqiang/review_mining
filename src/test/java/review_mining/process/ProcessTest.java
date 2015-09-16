package review_mining.process;

import java.util.ArrayList;

import org.junit.Test;

import edu.pitt.review_mining.process.Process;
import edu.stanford.nlp.trees.Tree;

public class ProcessTest {

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
	public void testProcess() {
		Process word = new Process();
		word.process(
				"CA$H ONLY!!! Wait in line, pick what you want, pay the man, and eat to your heart's desire. This easily counts as two meals which is such a steal. Not to mention it is very yummy whether you are super hungry or not. The combo meal of chicken, lamb, rice, and veggies may not be the most photogenic for Instagram, but it definitely fits the bill to be the winner of a superlative named Super Cheap & Extra Good.");
		assert(true);
	}

	// @Test
	public void testPreprocessSentence() {
		Process word = new Process();
		System.out.println(word.preprocessSentence("i like blue cheese."));
		assert(true);
	}

	@Test
	public void testDetectPhrase() {
		Process word = new Process();
		System.out.println(word.detectPhrase("new york pizza", false, true));
		System.out.println(word.detectPhrase("blue cheese", false, true));
		System.out.println(word.detectPhrase("Blue cheese", false, false));
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