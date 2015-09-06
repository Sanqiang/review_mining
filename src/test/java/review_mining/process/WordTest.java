package review_mining.process;

import java.util.ArrayList;

import org.junit.Test;

import edu.pitt.review_mining.process.Word;
import edu.stanford.nlp.trees.Tree;

public class WordTest {
	
	@Test
	public void testDetectPhrase(){
		Word word = new Word();
		boolean is_phrase = word.detectPhrase("big bang");
		System.out.println(is_phrase);
		assert(true);
	}
	
	
	//@Test
	public void testSplitSentence() {
		Word word = new Word();
		Tree tree = word.filterSentence("came here after a club bc more than one person recommended I get chicken and rice from this infamous corner.");
		ArrayList<Tree> trees = word.splitTree(tree);
		for (Tree child_tree : trees) {
			System.out.println(child_tree);
		}
		assert(true);
	}
	
	//@Test
	public void testFilterSentence() {
		Word word = new Word();
		//word.processSentence("red red sauce is pretty good, but I like white meat");
		Tree tree = word.filterSentence("came here after a club bc more than one person recommended I get chicken and rice from this infamous corner.");
		System.out.println(tree);
		assert(true);
	}

	//@Test
	public void testProcessClause() {
		new Word().processClause("red red sauce is pretty good, but I like white meat");
		assert(true);
	}

}