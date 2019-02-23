package model;

import javax.swing.JLabel;

import ihm.swing.SwingBuilder;

public class ZoneAffichage {
	public JLabel nom = new JLabel();
	public JLabel valeur = new JLabel();
	public ZoneAffichage(String nom) {
		this.nom.setText(nom);
	}

	public void ajouter(int width, int height, SwingBuilder sb) {
		sb.beginX();
		sb.setSize(width, height);
		sb.add(nom);
		sb.setSize(width, height);
		sb.add(valeur);
		sb.end();

	}

}
