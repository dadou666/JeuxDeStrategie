package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Joueur {
	public Point depart;
	public List<Unite> unites;
	public List<Projectile> projectiles;
	public List<StrategieUnit> strategieUnits = new ArrayList<>();
	public List<Ressource> reservations = new ArrayList<>();

	public EtatJoueur etatJoueur;

	public Joueur(Strategie strategie, Point depart) {
		CreationUnite creationUnite = new CreationUnite();
		creationUnite.idx = 0;
		creationUnite.population = 0;
		this.etatJoueur = creationUnite;
		strategieUnits.add(strategie.strategieUnit.get(Niveau.N1));
		strategieUnits.add(strategie.strategieUnit.get(Niveau.N2));
		strategieUnits.add(strategie.strategieUnit.get(Niveau.N3));
		strategieUnits.add(strategie.strategieUnit.get(Niveau.N4));
		strategieUnits.add(strategie.strategieUnit.get(Niveau.N5));

	}

	public void executer(Plateau p) {
		etatJoueur.executer(this);
		unites.removeIf(unite -> {
			unite.executer(p);
			if (unite.vie <= 0) {
				unite.liberer(p);
				return true;
			}
			return false;
		});
		projectiles.removeIf((Projectile projectile) -> {
			projectile.executer(p, Joueur.this);
			return projectile.deplacer == null;
		});

	}

	public Unite chercher(Unite unite) {
		Unite r = null;
		long d = 0;
		for (Unite u : this.unites) {

			int dx = u.position.x - unite.position.x;
			int dy = u.position.y - unite.position.x;

			long tmp = dx * dx + dy * dy;
			if (r == null) {
				r = u;

				d = tmp;
			} else {

				if (tmp < d) {
					r = u;
				}
			}
		}

		return r;
	}

}
