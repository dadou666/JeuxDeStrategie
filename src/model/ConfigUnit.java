package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType( XmlAccessType.FIELD)
public class ConfigUnit  {
	public String nom;
	public Niveau niveau;
	public int energieTransporte;
	public int vie;
	public int puissance;
	public int vitesse;
	public int porte;
	public int vitesseTire;
	public int population;
	public long attente;
	public int taille;
	public int tailleProjectile;
	public String toString() {
		return nom+"["+niveau+"]";
	}
	public List<Action> actions() {
		List<Action> actions = new ArrayList<>();
		actions.add(new Attaquer());
		actions.add(new RecolterEnergie());
		return actions;
		
		
	}

}
