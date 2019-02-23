package model;

import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBException;

import ihm.swing.SwingBuilder;

public class DessinPlateau extends JComponent {
	Plateau plateau;
	
	public static void main(String args[]) throws JAXBException, ClassNotFoundException, IOException {
		Plateau plateau = Plateau.charger();
		SwingBuilder sb= new SwingBuilder();
		sb.painter.add(()->{
			sb.beginX();
			sb.setSize(Plateau.dx*2, Plateau.dy);
			sb.add(new DessinPlateau(plateau));
			sb.end();
		});
		sb.paint("Test");
		
		
		
	}

	public DessinPlateau(Plateau plateau) {
		this.plateau = plateau;

	}

	public void loop()
			throws InterruptedException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		while (true) {
			Thread.sleep(100);
			plateau.executer();
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					DessinPlateau.this.repaint();

				}

			});
		}

	}

	public void paint(Graphics g) {
		if (plateau.joueur1 != null) {
			for (Unite unite : plateau.joueur1.unites) {
				g.setColor(Color.RED);
				afficher(g, unite);

			}
		}
		if (plateau.joueur2 != null) {
			for (Unite unite : plateau.joueur2.unites) {
				g.setColor(Color.BLACK);
				afficher(g, unite);
			}
		}
		if (plateau.joueur1 != null) {
			for (Projectile p : plateau.joueur1.projectiles) {
				g.setColor(Color.BLUE);
				afficher(g, p);
			}
		}
		if (plateau.joueur2 != null) {
			for (Projectile p : plateau.joueur2.projectiles) {
				g.setColor(Color.BLUE);
				afficher(g, p);
			}
		}
		
		for (Energie e : plateau.energies) {
			g.setColor(Color.GREEN);
			afficher(g, e);
		}

	}

	public void afficher(Graphics g, Unite unite) {
		int taille = unite.strategie.configUnit.taille;
		g.fillOval(unite.position.x - taille, unite.position.y - taille, 2 * taille, 2 * taille);
	}

	public void afficher(Graphics g, Projectile projectile) {
		int taille = projectile.unite.strategie.configUnit.tailleProjectile;
		g.fillOval(projectile.position.x - taille, projectile.position.y - taille, 2 * taille, 2 * taille);

	}

	public void afficher(Graphics g, Energie e) {
		int taille = Plateau.ressourceSize;
		g.fillOval(e.position.x - taille, e.position.y - taille, 2 * taille, 2 * taille);

	}

}
