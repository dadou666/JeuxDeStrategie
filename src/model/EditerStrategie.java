package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import ihm.swing.Painter;
import ihm.swing.SwingBuilder;

public class EditerStrategie implements Painter {
	JButton niveaux[];
	Niveau niveau;
	SwingBuilder sb;

	Map<Niveau, DefaultComboBoxModel<ConfigUnit>> map = new HashMap<>();
	Strategie strategie;
	StrategieUnit strategieUnit;
	DefaultListModel<Action> modelListAction = new DefaultListModel<>();
	JList<Action> listAction = new JList<>();
	boolean sauvegarder;

	ZoneChoix<ConfigUnit> configUnit = new ZoneChoix("configs");
	ZoneChoix<Action> actions = new ZoneChoix("actions");
	ZoneAffichage nom = new ZoneAffichage("nom");
	ZoneAffichage vie = new ZoneAffichage("vie");
	ZoneAffichage puissance = new ZoneAffichage("puissance");
	ZoneAffichage population = new ZoneAffichage("population");
	ZoneAffichage vitesse = new ZoneAffichage("vitesse");
	ZoneAffichage vitesseTire = new ZoneAffichage("vitesseTire");
	ZoneAffichage taille = new ZoneAffichage("taille");
	ZoneAffichage tailleProjectile = new ZoneAffichage("tailleProjectile");
	ZoneAffichage porte = new ZoneAffichage("porte");
	ZoneAffichage attente = new ZoneAffichage("attente");
	ZoneAffichage energieTransporte = new ZoneAffichage("energieTransporte");
	JButton ajouterAction = new JButton("Ajouter action");
	JButton supprimerAction = new JButton("Supprimer action");

	ZoneAffichage[] zoneAffichages() {
		return new ZoneAffichage[] { nom, vie, puissance, population, vitesse, vitesseTire, taille, tailleProjectile,
				porte, attente, energieTransporte

		};
	}

	public static void main(String args[]) throws JAXBException {
		SwingBuilder sb = new SwingBuilder();
		new EditerStrategie(sb, Strategie.charger("F:/GitHub/JeuxDeStrategie/src/model/strategie.xml"),true);
		sb.paint("Editer strategie");
	}
	public void sauvegarder()  {
		if (!sauvegarder) {
			return;
		}
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(Strategie.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			
			OutputStream os = new FileOutputStream("F:\\GitHub\\JeuxDeStrategie\\src\\model\\strategie.xml");
			jaxbMarshaller.marshal(this.strategie, os);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public void initialiser(ConfigUnit config) {
		StrategieUnit su = strategie.strategieUnit.get(config.niveau);
		su.configUnit = config;
		this.modelListAction.clear();
		for (Action action : su.actions) {
			this.modelListAction.addElement(action);
		}
		DefaultComboBoxModel<Action> modelActions = new DefaultComboBoxModel<>();
		for (Action action : config.actions()) {
			modelActions.addElement(action);
		}
		this.listAction.setModel(this.modelListAction);

		actions.comboBox.setModel(modelActions);
		nom.valeur.setText(config.nom);
		vie.valeur.setText(String.valueOf(config.vie));
		puissance.valeur.setText(String.valueOf(config.puissance));
		population.valeur.setText(String.valueOf(config.population));
		vitesse.valeur.setText(String.valueOf(config.vitesse));
		vitesseTire.valeur.setText(String.valueOf(config.vitesseTire));
		taille.valeur.setText(String.valueOf(config.taille));
		tailleProjectile.valeur.setText(String.valueOf(config.tailleProjectile));
		porte.valeur.setText(String.valueOf(config.porte));
		attente.valeur.setText(String.valueOf(config.attente));
		energieTransporte.valeur.setText(String.valueOf(config.energieTransporte));
		this.sauvegarder();

	}

	EditerStrategie(SwingBuilder sb, Strategie s,boolean sauvegarder) throws JAXBException {
		this.sauvegarder = sauvegarder;
		Config config = Config.charger();
		if (s == null ) {
			s = config.creerStrategie();
		} else {
			config.modifier(s);

		}
		this.strategie = s;
			sb.painter.add(this);
		this.sb = sb;
		int i = 0;
		for (Niveau n : Niveau.values()) {
			map.put(n, new DefaultComboBoxModel<>());
		}
		for (ConfigUnit cu : config.configUnits) {
			map.get(cu.niveau).addElement(cu);
			strategieUnit = this.strategie.strategieUnit.get(cu.niveau);
			if (strategieUnit == null) {
				strategieUnit = new StrategieUnit();

				strategieUnit.configUnit = cu;

				this.strategie.strategieUnit.put(cu.niveau, strategieUnit);
			}
		}

		niveaux = new JButton[Niveau.values().length];
		Niveau tmp[] = Niveau.values();
		for (Niveau n : Niveau.values()) {

			JButton b = new JButton(n.name());
			niveaux[i] = b;
			niveaux[i].addActionListener((e) -> {

				initialiser(b, n);

			});
			i++;
		}
		niveaux[0].setText("[" + niveaux[0].getText() + "]");
		initialiser(niveaux[0], tmp[0]);
		configUnit.comboBox.setModel(map.get(tmp[0]));
		configUnit.comboBox.addActionListener((e) -> {
			ConfigUnit cu = (ConfigUnit) configUnit.comboBox.getSelectedItem();
			if (cu == null) {
				return;
			}

			initialiser(cu);

		});
		ajouterAction.addActionListener((e) -> {
			ajouterAction();
		});
		supprimerAction.addActionListener((e) -> {
			supprimerAction();
		});

	}

	public void ajouterAction() {
		Action action = (Action) this.actions.comboBox.getSelectedItem();
		int selIndex = this.listAction.getSelectedIndex();
		if (selIndex < 0) {
			this.modelListAction.addElement(action);
		} else {
			this.modelListAction.add(selIndex, action);
		}
		this.modifierStrategieUnit();
	

	}
	public void  modifierStrategieUnit() {
		strategieUnit.actions.clear();
		for(int i=0;i < this.modelListAction.size();i++) {
			
			strategieUnit.actions.add(this.modelListAction.getElementAt(i));
			
		}
		this.strategie.strategieUnit.put(strategieUnit.configUnit.niveau, strategieUnit);
		this.sauvegarder();
	}

	public void supprimerAction() {
		Action action = this.listAction.getSelectedValue();
		if (action != null) {
			this.modelListAction.removeElement(action);
		}
		this.modifierStrategieUnit();
	

	}

	public void initialiser(JButton button, Niveau niveau) {
		int i = 0;
		for (Niveau n : Niveau.values()) {
			niveaux[i].setText(n.name());
			i++;
		}
		this.niveau = niveau;
		button.setText("[" + niveau.name() + "]");
		DefaultComboBoxModel<ConfigUnit> tmp = map.get(niveau);
		strategieUnit = strategie.strategieUnit.get(niveau);
		configUnit.comboBox.setModel(tmp);

		this.initialiser(strategieUnit.configUnit);

	}

	@Override
	public void paint() {
		sb.beginY();
		sb.beginX();
		sb.setSize(100, 20);

		for (JButton button : niveaux) {
			sb.add(button);

		}
		sb.end();
		sb.beginX();
		sb.beginY();
		configUnit.ajouter(150, 20, sb);
		actions.ajouter(150, 20, sb);
		for (ZoneAffichage zf : this.zoneAffichages()) {
			zf.ajouter(150, 20, sb);
		}
		sb.end();
		sb.beginY();
		sb.setSize(200, 20);
		sb.add(ajouterAction);
		sb.add(supprimerAction);
		sb.setSize(200, 40 + this.zoneAffichages().length * 20);
		sb.add(listAction);
		sb.end();
		sb.end();
		sb.end();
	}

}
