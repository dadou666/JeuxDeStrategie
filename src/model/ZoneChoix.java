package model;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ihm.swing.SwingBuilder;

public class ZoneChoix<T> {
	JLabel label=new JLabel();
	JComboBox<T> comboBox =new JComboBox();
	ZoneChoix(String nom) {
		label.setText(nom);
	}
	public void ajouter(int width,int height,SwingBuilder sb) {
		sb.beginX();
		    sb.setSize(width, height);
			sb.add(label);
		    sb.setSize(width, height);
			sb.add(comboBox);
		sb.end();
		
	}
}
