package model;

import java.awt.Point;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlTransient;

public abstract class Ressource implements Serializable {
	
	public Point position;
	
	public boolean estLibre=true;
	

}
