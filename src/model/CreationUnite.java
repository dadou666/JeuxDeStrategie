package model;

import java.awt.Point;

public class CreationUnite extends EtatJoueur {
	public int idx;
	public int population;
	@Override
	public void executer(Joueur joueur) {
		StrategieUnit su = joueur.strategieUnits.get(idx);
		if (population > su.configUnit.population) {
			AttenteCreation attenteCreation = new AttenteCreation();
			attenteCreation.duree = su.configUnit.attente;
			attenteCreation.debut = System.currentTimeMillis();
			idx++;
			if (idx >= joueur.strategieUnits.size()) {
				
				joueur.etatJoueur = null;
				return;
			}
			
			population = 0;
			
			attenteCreation.creationUnite = this;
			joueur.etatJoueur =attenteCreation;
			
		}
		population++;
		Unite unite = new Unite();
		unite.position = new Point(joueur.depart.x,joueur.depart.y);
		unite.strategie = su;
		unite.etatUnit =su.actions.get(0).creerEtat();
		joueur.unites.add(unite);
		
	}
}
