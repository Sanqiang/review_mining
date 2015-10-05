package edu.pitt.review_mining.process.text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import edu.pitt.review_mining.utility.Config;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.IndexWordSet;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.relationship.Relationship;
import net.didion.jwnl.data.relationship.RelationshipFinder;
import net.didion.jwnl.data.relationship.RelationshipList;
import net.didion.jwnl.dictionary.Dictionary;

public class WordNetUtility {

	public WordNetUtility() {
		try {
			JWNL.initialize(new FileInputStream(Config.PATH_JWNL_CONFIG));
		} catch (FileNotFoundException | JWNLException e) {
			e.printStackTrace();
		}
	}

	public boolean canBeNoun(String word) {
		try {
			IndexWordSet idxWord = Dictionary.getInstance().lookupAllIndexWords(word);
			IndexWord[] words = idxWord.getIndexWordArray();
			for (int i = 0; i < words.length; i++) {
				if (words[i].getPOS() == POS.NOUN) {
					return true;
				}
			}
		} catch (JWNLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public int measureNounSimilarity(String word1, String word2) {
		try {
			IndexWord iword1 = Dictionary.getInstance().lookupIndexWord(POS.NOUN, word1);
			IndexWord iword2 = Dictionary.getInstance().lookupIndexWord(POS.NOUN, word2);

			int min_depth = Integer.MAX_VALUE;
			for (int i = 1; i <= iword1.getSenseCount(); i++) {
				for (int j = 1; j <= iword2.getSenseCount(); j++) {
					RelationshipList list = RelationshipFinder.getInstance().findRelationships(iword1.getSense(i),
							iword2.getSense(j), PointerType.HYPERNYM);
					int depth = ((Relationship) list.get(0)).getDepth();
					if (min_depth > depth) {
						min_depth = depth;
					}
				}
			}
			return min_depth;
		} catch (JWNLException e) {
			e.printStackTrace();
			return -1;
		}

	}
}
