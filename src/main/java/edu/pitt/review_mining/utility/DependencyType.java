package edu.pitt.review_mining.utility;

public enum DependencyType {

	AdjectivalModifier, //amod in Stanford
	NounCompoundModifier, //nn in Stanford
	NominalSubject,  //nsubj in Stanford
	DirectObject, //dobj in Stanford
	Conjunction, //conj in Stanford
	Compound,
	//MISCELLANEOUS ONE, FOR UNINTERESTING AND UNMAPPED RELATIONS
	OTHER;
}