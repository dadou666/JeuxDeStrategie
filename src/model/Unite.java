package model;


import java.util.ArrayList;
import java.util.List;

public class Unite extends Element {

	public Joueur joueur;

	public StrategieUnit strategie;
	public EtatUnite etatUnit;
	public int vie;
	public int idxAction;
	public List<Energie> energie = new ArrayList<>();
	
	public void executer(Plateau p) {
		if (etatUnit == null) {
			return;
		}
		etatUnit.executer(this, p);
	}
	public void liberer(Plateau p) {
		if (etatUnit == null) {
			return;
		}
		etatUnit.liberer(this, p);
		
	}

	public void finDeplacer(Plateau plateau) {
		if (etatUnit != null) {
			etatUnit.finDeplacer(this, plateau);
		}
	}
	public void actionSuivante() {
		idxAction ++;
		if (idxAction >= strategie.actions.size()) {
			
			idxAction = 0;
		}
		etatUnit = strategie.actions.get(idxAction).creerEtat();
	}
	
}
