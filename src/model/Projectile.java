package model;

import java.awt.Point;

public class Projectile extends Element {
	
	public Deplacer deplacer;
	public Unite unite;
	public Unite cible;
	public void executer(Plateau plateau,Joueur joueur) {
		deplacer.deplacer(plateau, this);
		
	}

	public void finDeplacer(Plateau plateau) {
		int dx = position.x-cible.position.x;
		int dy = position.y-cible.position.y;
		int d = dx*dx+dy*dy;
		if (d < cible.strategie.configUnit.taille+unite.strategie.configUnit.tailleProjectile) {
			
			cible.vie = cible.vie - unite.strategie.configUnit.puissance;
		
		}
		deplacer = null;
		
		unite.actionSuivante();

	}

}
