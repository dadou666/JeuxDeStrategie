package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Joueur {
	public String nom;
	public Point depart;
	public List<Unite> unites = new ArrayList<>();
	public List<Projectile> projectiles = new ArrayList<>();
	public List<StrategieUnit> strategieUnits = new ArrayList<>();
	public List<Ressource> reservations = new ArrayList<>();

	public EtatJoueur etatJoueur;

	public int energie() {
		int energie = 0;
		for (Unite unite : unites) {
			energie += unite.energie.size();
		}
		return energie;
	}

	public boolean estMort() {
		if (unites.size() > 0) {
			return false;
		}

		return etatJoueur == null;
	}

	public Joueur(String nom, Strategie strategie, Point depart) {
		this.nom = nom;
		this.depart = depart;
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
		if (etatJoueur != null) {
			etatJoueur.executer(this);
		}
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
