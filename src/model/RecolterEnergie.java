package model;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class RecolterEnergie extends Action {

	@Override
	public EtatUnite creerEtat() {

		return new EtatRecolter();
	}
	public String toString() {
		return "RecolterEnergie";
	}

}
