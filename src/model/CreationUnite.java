package model;

import java.awt.Point;



public class CreationUnite extends EtatJoueur {
	public int idx;
	public int population;

	@Override
	public void executer(Joueur joueur) {
		StrategieUnit su = joueur.strategieUnits.get(idx);
		if (population > su.configUnit.population) {
			Logger.log(" population "+su.configUnit.population+" "+su.configUnit.niveau+" "+su.configUnit.nom);
			idx++;
			if (idx >= joueur.strategieUnits.size()) {

				joueur.etatJoueur = null;
				return;
			}

			population = 0;

		}
		population++;
	//	Logger.log("Creation unite "+su.configUnit.niveau+ " "+joueur.nom);
		Unite unite = new Unite();
		unite.position = new Point(joueur.depart.x, joueur.depart.y);
		unite.strategie = su;
		unite.joueur = joueur;
		unite.vie = su.configUnit.vie;
		unite.etatUnit = su.actions.get(0).creerEtat();
		joueur.unites.add(unite);
		AttenteCreation attenteCreation = new AttenteCreation();
		attenteCreation.duree = su.configUnit.attente;
		attenteCreation.debut = System.currentTimeMillis();
		attenteCreation.creationUnite = this;
		joueur.etatJoueur = attenteCreation;

	}
}
