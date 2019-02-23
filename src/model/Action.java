package model;

import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({Attaquer.class, RecolterEnergie.class})
public abstract class Action {
	public abstract  EtatUnite creerEtat();

	

}
