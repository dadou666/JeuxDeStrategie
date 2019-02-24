package model;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.xml.bind.JAXBException;

import ihm.swing.Painter;
import ihm.swing.SwingBuilder;

public class Jeux implements Painter {
	EditerStrategie editerStrategie;
	JButton lancer = new JButton("Lancer");
	SwingBuilder sb;
	DessinPlateau dessinPlateau;

	public static void main(String args[]) throws JAXBException {
		SwingBuilder sb = new SwingBuilder();

		new Jeux(sb);
		sb.paint("Jeux");

	}

	public Jeux(SwingBuilder sb) throws JAXBException {
		this.sb = sb;
		editerStrategie = new EditerStrategie(sb, null, false);
		sb.painter.clear();
		sb.painter.add(this);
		lancer.addActionListener(this::lancer);
	}

	public void lancer(ActionEvent ae) {
		try {
			Strategie s = Strategie.charger("/model/strategie.xml");
			Plateau plateau = Plateau.charger();
			plateau.creerJoueur(s, "Ordinateur");
			plateau.creerJoueur(editerStrategie.strategie, "Moi");
			
			dessinPlateau = new DessinPlateau(plateau);
			dessinPlateau.lancerThread();
			sb.paint("Jeux");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void paint() {
		sb.beginY();
		if (dessinPlateau != null) {
			sb.setSize(Plateau.dx * 2, Plateau.dy);
			sb.add(dessinPlateau);

		} else {
			editerStrategie.paint();
			sb.setSize(500, 20);
			sb.add(lancer);
		}
		sb.end();

	}

}
