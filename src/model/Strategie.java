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
	public Map<Niveau, StrategieUnit> strategieUnit = new HashMap<>();

	public static Strategie chargerRessource(String chemin) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Strategie.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		InputStream inStream = Strategie.class.getResourceAsStream(chemin);
		if (inStream == null) {
			return null;
		}

		return (Strategie) jaxbUnmarshaller.unmarshal(inStream);

	}
	public static Strategie chargerFichier(String chemin) throws JAXBException, FileNotFoundException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Strategie.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		InputStream inStream =new FileInputStream(chemin);


		return (Strategie) jaxbUnmarshaller.unmarshal(inStream);

	}


}
