package model;

import java.awt.Point;

public class EtatAttaquer extends EtatUnite {
	Deplacer deplacer;
	Attaquer attaquer;
	Point cible ;
	Unite uniteCible;
	
	public EtatAttaquer(Attaquer attaquer) {
		this.attaquer = attaquer;
	}

	@Override
	public void executer(Unite unite, Plateau plateau) {
		if (deplacer == null) {
			uniteCible = plateau.adversaire(unite.joueur).chercher(unite);
			this.cible = new Point(uniteCible.position);
			int dx = cible.x-unite.position.x;
			int dy = cible.x-unite.position.y;
			float d=dx*dx+dy*dy;
			d = (float) Math.sqrt(d);

			if (d >  unite.strategie.configUnit.porte) {
				d =d - unite.strategie.configUnit.porte;
				deplacer = new Deplacer(unite,this.cible,d);
				
				
			} else {
				this.tirer(unite, plateau);

				
			}
		}

	}

	@Override
	public void finDeplacer(Unite unite, Plateau plateau) {
		this.tirer(unite, plateau);

	}
	
	public void tirer(Unite unite,Plateau plateau) {
		Projectile projectile = new Projectile();
		projectile.deplacer = new Deplacer(projectile,cible,unite.strategie.configUnit.vitesseTire);
		projectile.cible = this.uniteCible;
		unite.etatUnit = new EtatAttenteFinTire();
		unite.joueur.projectiles.add(projectile);
		
	}

	@Override
	public void liberer(Unite unite, Plateau p) {
		// TODO Auto-generated method stub
		
	}

}
