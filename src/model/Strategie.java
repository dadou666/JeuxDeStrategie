package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Strategie {
	public Map<Niveau,StrategieUnit> strategieUnit= new HashMap<>();
	public static Strategie charger(String chemin) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Strategie.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		InputStream inStream;
		try {
			inStream = new FileInputStream(chemin);
		} catch (FileNotFoundException e) {
			return null;
		}
		return (Strategie) jaxbUnmarshaller.unmarshal(inStream);
		
	}
	

}
