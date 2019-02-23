package model;

import javax.swing.JLabel;
import javax.swing.JTextField;

import ihm.swing.SwingBuilder;

public class ZoneSaisie {
	JLabel label=new JLabel();
	JTextField textField =new JTextField();
	ZoneSaisie(String nom) {
		label.setText(nom);
	}
	public void ajouter(int width,int height,SwingBuilder sb) {
		sb.beginX();
		    sb.setSize(width, height);
			sb.add(label);
		    sb.setSize(width, height);
			sb.add(textField);
		sb.end();
		
	}
}
