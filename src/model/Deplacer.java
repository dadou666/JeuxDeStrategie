package model;

import java.awt.Point;


public class Deplacer {


	public void executer(Element element, Plateau plateau) {
		// TODO Auto-generated method stub
		
	}
	public float distance;
	public float vitesse;
	public float vx;
	public float vy;
	
	public float px;
	public float py;
	public float ix;
	public float iy;
	
	public float destx;
	public float desty;
	

	public Deplacer(Element balle, Point destination, float vitesse, float d) {
		float dx = destination.x - balle.position.x;
		float dy = destination.y - balle.position.y;
		px = balle.position.x;
		py=  balle.position.y;
		ix=px;
		iy=py;
		distance = (float) Math.sqrt(dx * dx + dy * dy);

		if (distance > 0.0f) {
			

			vx = vitesse * (dx / distance);
			vy = vitesse * (dy / distance);
			float x = balle.position.x;
			float y = balle.position.y;
			x += d * dx / distance;
			y += d * dy / distance;

			this.destx=x;
			this.desty=y;
			this.vitesse = vitesse;
			return;
		}
		throw new Error(" distance nulle");

	}

	public Deplacer(Element balle, Point destination, float vitesse) {
		float dx = destination.x - balle.position.x;
		float dy = destination.y - balle.position.y;

		px= balle.position.x;
		py= balle.position.y;
		ix=px;
		iy=py;
		distance = (float) Math.sqrt(dx * dx + dy * dy);
		if (distance >= 0.0f) {

			vx = vitesse * (dx / distance);
			vy = vitesse * (dy / distance);
			this.destx= destination.x;
			this.desty =destination.y;
			this.vitesse = vitesse;
			return;
		}
		throw new Error(" distance nulle");

	}

	public void step(Plateau ecranJeux, Element entite) {
		this.deplacer(ecranJeux, entite);
	}

	public void deplacer(Plateau ecran, Element balle) {
		float dx = ix - px;
		float dy = iy - py;


		px += vx;
		py += vy;
		balle.position.x = (int) px;
		balle.position.y = (int) py;
		float d = (float) Math.sqrt(dx * dx + dy * dy);

		if (d >= distance) {

			balle.position.setLocation(destx, desty);
			balle.finDeplacer(ecran);
		
			return;
		}

	}
}
