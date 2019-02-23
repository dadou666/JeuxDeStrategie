package model;

import java.util.List;

import javax.xml.bind.annotation.XmlType;
@XmlType
public class Attaquer extends Action {


	@Override
	public EtatUnite creerEtat() {

		return new EtatAttaquer(this);
	}
	public String toString() {
		return "Attaquer";
	}
}
