package edu.pitt.review_mining.utility;

public enum DependencyType {
	///local type
	AdjectivalModifier, //amod in Stanford
	NounCompoundModifier, //nn in Stanford
	NominalSubject,  //nsubj in Stanford
	DirectObject, //dobj in Stanford
	Conjunction, //conj in Stanford
	Complement,
	Dependent,
	NounModifier,
	//MISCELLANEOUS ONE, FOR UNINTERESTING AND UNMAPPED RELATIONS
	OtherLocalType,
	
	///global type
	XComplement, //it tastes good.
	SingleNmod, //It literally taste like plastic.
	SingleAmod, //red food is good.
	AmodSubj, //chicken is delicious food.
	SingleSubj, //food is delicious.
	ConjAndComp; //The chicken and rice with white sauce is delicious. chicken rice is delicious.
	
	
}