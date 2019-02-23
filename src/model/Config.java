package model;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Config {
	public List<ConfigUnit> configUnits = new ArrayList<>();

	public static Config charger() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		InputStream inStream = Config.class.getResourceAsStream("/model/config.xml");

		if (inStream == null) {
			return new Config();
		}

		return (Config) jaxbUnmarshaller.unmarshal(inStream);

	}

	public Strategie creerStrategie() {
		Strategie s = new Strategie();
		for (ConfigUnit cu : configUnits) {
			if (s.strategieUnit.get(cu.niveau) == null) {
				StrategieUnit su = new StrategieUnit();
				su.configUnit = cu;
				s.strategieUnit.put(cu.niveau, su);
			}
			if (s.strategieUnit.size() == Niveau.values().length) {
				return s;
			}

		}
		return null;
	}

	public void modifier(Strategie s) {
		int n = 0;
		for (ConfigUnit cu : configUnits) {
			StrategieUnit su = s.strategieUnit.get(cu.niveau);
			if (su.configUnit.nom.equals(cu.nom)) {
				su.configUnit = cu;
				n++;
			}
			if (n == Niveau.values().length) {
				return;
			}

		}

	}

}
