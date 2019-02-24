package model;

public class EtatRecolter extends EtatUnite {
	public Deplacer deplacer;
	public Energie energie;

	@Override
	public void executer(Unite unite, Plateau plateau) {

		if (unite.energie.size() >= unite.strategie.configUnit.energieTransporte) {
			unite.actionSuivante();
		}
		if (deplacer == null) {
			for (Energie e : plateau.energies) {
				if (!unite.joueur.reservations.contains(e) && e.estLibre) {
					deplacer = new Deplacer(unite, e.position, unite.strategie.configUnit.vitesse);
					energie = e;
					unite.joueur.reservations.add(e);
					return;
				}

			}
			unite.actionSuivante();

		} else {
			deplacer.deplacer(plateau, unite);
		
		}

	}

	@Override
	public void finDeplacer(Unite unite, Plateau plateau) {
		if (energie.estLibre) {
			unite.energie.add(energie);
			energie.estLibre = false;
			
		}
		unite.joueur.reservations.remove(energie);
		deplacer = null;

	}

	@Override
	public void liberer(Unite unite, Plateau p) {
		unite.joueur.reservations.remove(energie);
		
	}

}
