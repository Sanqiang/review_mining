package review_mining.process;

import java.util.ArrayList;

import org.junit.Test;

import edu.pitt.review_mining.process.Process;
import edu.stanford.nlp.trees.Tree;

public class ProcessTest {
	
	@Test
	public void testPreprocessSentence() {
		Process word = new Process();
		String sent =  word.preprocessSentence("came here after a club bc more than one person recommended, I get chicken and rice from this infamous corner.");
		System.out.println(sent);
		assert(true);
	}
	
	//@Test
	public void testDetectPhrase(){
		Process word = new Process();
		boolean is_phrase = word.detectPhrase("big bang");
		System.out.println(is_phrase);
		assert(true);
	}
	
	
	//@Test
	public void testSplitSentence() {
		Process word = new Process();
		Tree tree = word.filterSentence("came here after a club bc more than one person recommended I get chicken and rice from this infamous corner.");
		ArrayList<Tree> trees = word.splitTree(tree);
		for (Tree child_tree : trees) {
			System.out.println(child_tree);
		}
		assert(true);
	}
	
	//@Test
	public void testFilterSentence() {
		Process word = new Process();
		//word.processSentence("red red sauce is pretty good, but I like white meat");
		Tree tree = word.filterSentence("came here after a club bc more than one person recommended I get chicken and rice from this infamous corner.");
		System.out.println(tree);
		assert(true);
	}

	//@Test
	public void testProcessClause() {
		new Process().processClause("red red sauce is pretty good, but I like white meat");
		assert(true);
	}

}