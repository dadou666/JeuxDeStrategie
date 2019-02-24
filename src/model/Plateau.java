package model;

import java.awt.Color;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

public class Plateau implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3881699749515774977L;
	public static int dx = 300;
	public static int dy = 400;
	public static int ressourceSize = 5;
	public static int nombreEnergie = 100;
	public static long duree = 120000;

	public long temp = 0;

	public boolean creerJoueur(Strategie s, String nom) {
		if (joueur1 == null) {
			Point depart = new Point(0, dy / 2);
			joueur1 = new Joueur(nom, s, depart);
			return true;

		}
		if (joueur2 == null) {
			Point depart = new Point(2 * dx, dy / 2);
			joueur2 = new Joueur(nom, s, depart);
			return true;

		}
		return false;

	}

	public static Plateau charger() throws JAXBException, IOException, ClassNotFoundException {
		InputStream is = Plateau.class.getResourceAsStream("/model/plateau.bin");
		if (is == null) {
			Plateau r = creer();

			OutputStream os;
			try {
				os = new FileOutputStream("F:\\GitHub\\JeuxDeStrategie\\src\\model\\plateau.bin");
				ObjectOutputStream out = new ObjectOutputStream(os);
				out.writeObject(r);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return r;
		}
		ObjectInputStream ois = new ObjectInputStream(is);

		return (Plateau) ois.readObject();

	}

	public static Plateau creer() {

		int d = 10;
		int nx = dx / d;
		int ny = dy / d;
		Plateau r = new Plateau();
		List<Point> points = new ArrayList<>();
		for (int ix = 0; ix <= nx - 1; ix++) {

			for (int iy = 0; iy <= ny - 1; iy++) {
				points.add(new Point(ix, iy));

			}
		}
		int n = nombreEnergie;
		while (n > 0) {
			Random random = new Random();
			Point p = points.get(random.nextInt(points.size() - 1));
			n--;
			Point posSymetrique = new Point(2 * dx - 1 - p.x * d, p.y * d);
			Point pos = new Point(p.x * d, p.y * d);
			Energie e = new Energie();
			e.position = posSymetrique;

			Energie f = new Energie();
			f.position = pos;
			r.energies.add(e);
			r.energies.add(f);
		}
		return r;

	}

	transient public Joueur joueur1;
	transient public Joueur joueur2;

	public List<Energie> energies = new ArrayList<>();

	public Joueur adversaire(Joueur joueur) {
		if (joueur == joueur1) {
			return joueur2;
		}
		return joueur1;
	}

	public void demarer(Joueur joueur1, Joueur joueur2) {
		this.joueur1 = joueur1;
		this.joueur2 = joueur2;
		this.temp = System.currentTimeMillis();

	}

	public Joueur vainqueur() {
		if (System.currentTimeMillis() - temp > duree) {
			if (joueur1.energie() > joueur2.energie()) {
				return joueur1;
			}
			return joueur2;

		}
		if (joueur1.estMort()) {
			return joueur2;
		}
		if (joueur2.estMort()) {
			return joueur1;
		}
		return null;
	}

	public void executer() {
		joueur1.executer(this);
		joueur2.executer(this);
	}
}
