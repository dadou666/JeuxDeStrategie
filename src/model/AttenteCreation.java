package model;

public class AttenteCreation extends EtatJoueur {
	public long duree;
	public long debut;
	public CreationUnite creationUnite;
	@Override
	public void executer(Joueur joueur) {
			if (System.currentTimeMillis() - debut >= duree) {
				joueur.etatJoueur = creationUnite;
			}
		

		
	}

}
