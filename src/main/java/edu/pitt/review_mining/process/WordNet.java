package edu.pitt.review_mining.process;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;

public class WordNet {

	public WordNet() {
		try {
			JWNL.initialize(new FileInputStream("jwnl_property.xml"));
		} catch (FileNotFoundException | JWNLException e) {
			e.printStackTrace();
		}
	}
}
