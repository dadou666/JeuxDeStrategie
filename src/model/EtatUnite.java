package model;

public abstract class EtatUnite {
	
	public abstract void executer(Unite unite,Plateau plateau) ;
	abstract public void finDeplacer(Unite unite,Plateau plateau) ;
	public abstract void liberer(Unite unite,Plateau p);
	

}
