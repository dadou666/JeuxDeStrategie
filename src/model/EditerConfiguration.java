package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.junit.jupiter.params.shadow.com.univocity.parsers.common.DefaultConversionProcessor;

import ihm.swing.Painter;
import ihm.swing.SwingBuilder;

public class EditerConfiguration implements Painter, ActionListener, ListSelectionListener {
	Config config;

	JButton ajouter = new JButton("Ajouter");
	JButton supprimer = new JButton("Supprimer");

	ZoneSaisie nom = new ZoneSaisie("nom");
	ZoneSaisie vie = new ZoneSaisie("vie");
	ZoneSaisie puissance = new ZoneSaisie("puissance");
	ZoneSaisie population = new ZoneSaisie("population");
	ZoneSaisie vitesse = new ZoneSaisie("vitesse");
	ZoneSaisie vitesseTire = new ZoneSaisie("vitesseTire");
	ZoneSaisie taille = new ZoneSaisie("taille");
	ZoneSaisie tailleProjectile = new ZoneSaisie("tailleProjectile");
	ZoneSaisie porte = new ZoneSaisie("porte");
	ZoneSaisie attente = new ZoneSaisie("attente");
	ZoneSaisie energieTransporte = new ZoneSaisie("energieTransporte");
	ZoneChoix<Niveau> niveau = new ZoneChoix("niveau");

	public ZoneSaisie[] zoneSaisies() {

		return new ZoneSaisie[] { nom, vie, puissance, population, vitesse, vitesseTire, taille, tailleProjectile,
				porte, attente, energieTransporte

		};
	}

	JList<ConfigUnit> configUnits = new JList<>();
	DefaultListModel<ConfigUnit> modelConfigUnits = new DefaultListModel<>();
	
	SwingBuilder sb;

	static public void main(String args[]) throws JAXBException {
		SwingBuilder sb = new SwingBuilder();
	
		Config config = Config.charger();
	
		new EditerConfiguration(sb, config);
		sb.paint("Editeur configuration");

	}

	public EditerConfiguration(SwingBuilder sb, Config config) {
		this.sb = sb;
		this.config = config;

		ajouter.addActionListener(this);
		supprimer.addActionListener(this);
		this.configUnits.setModel(this.modelConfigUnits);
		for(ConfigUnit cu:config.configUnits) {
		this.modelConfigUnits.addElement(cu);
		}
		this.vider();
		if (this.modelConfigUnits.size()> 0) {
			this.initialiserConfigUnit(this.modelConfigUnits.elementAt(0));
		}

		DefaultComboBoxModel<Niveau> model = new DefaultComboBoxModel<>();
		model.addElement(Niveau.N1);
		model.addElement(Niveau.N2);
		model.addElement(Niveau.N3);
		model.addElement(Niveau.N4);
		model.addElement(Niveau.N5);

		this.niveau.comboBox.setModel(model);
		this.configUnits.addListSelectionListener(this);
		sb.painter.add(this);

	}

	@Override
	public void paint() {
		sb.beginY();

		sb.beginX();
		sb.setSize(280, 500);
		sb.add(configUnits);
		sb.beginY();
		sb.space(3);
		sb.beginX();
		sb.setSize(210, 30);
		sb.add(ajouter);
		sb.setSize(210, 30);
		sb.add(supprimer);
		sb.end();
		sb.space(3);

		for (ZoneSaisie sz : this.zoneSaisies()) {

			sz.ajouter(210, 30, sb);

		}
		niveau.ajouter(210, 30, sb);

		sb.end();
		sb.end();
		sb.end();

	}

	public ConfigUnit creerConfigUnit() {
		ConfigUnit configUnit = new ConfigUnit();
		configUnit.attente = Integer.parseInt(attente.textField.getText());
		configUnit.vie = Integer.parseInt(vie.textField.getText());
		configUnit.puissance = Integer.parseInt(puissance.textField.getText());
		configUnit.energieTransporte = Integer.parseInt(energieTransporte.textField.getText());
		configUnit.vitesse = Integer.parseInt(vitesse.textField.getText());
		configUnit.vitesseTire = Integer.parseInt(vitesseTire.textField.getText());
		configUnit.porte = Integer.parseInt(porte.textField.getText());
		configUnit.population = Integer.parseInt(population.textField.getText());
		configUnit.taille = Integer.parseInt(taille.textField.getText());
		configUnit.tailleProjectile = Integer.parseInt(tailleProjectile.textField.getText());
		configUnit.nom = nom.textField.getText();
		configUnit.niveau = (Niveau) niveau.comboBox.getSelectedItem();

		return configUnit;

	}

	public void initialiserConfigUnit(ConfigUnit configUnit) {
		attente.textField.setText(String.valueOf(configUnit.attente));
		vie.textField.setText(String.valueOf(configUnit.vie));
		puissance.textField.setText(String.valueOf(configUnit.puissance));
		energieTransporte.textField.setText(String.valueOf(configUnit.energieTransporte));
		vitesse.textField.setText(String.valueOf(configUnit.vitesse));

		vitesseTire.textField.setText(String.valueOf(configUnit.vitesseTire));
		porte.textField.setText(String.valueOf(configUnit.porte));
		population.textField.setText(String.valueOf(configUnit.population));
		taille.textField.setText(String.valueOf(configUnit.taille));
		tailleProjectile.textField.setText(String.valueOf(configUnit.tailleProjectile));
		nom.textField.setText(String.valueOf(configUnit.nom));
		niveau.comboBox.setSelectedItem(configUnit.niveau);

	}

	public void sauvegarder() throws FileNotFoundException, JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Config.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		Config config = new Config();
		for(int idx=0;idx < this.modelConfigUnits.size();idx++) {
		config.configUnits.add(this.modelConfigUnits.get(idx));
		
		}
		OutputStream os = new FileOutputStream("F:\\GitHub\\JeuxDeStrategie\\src\\model\\config.xml");
		jaxbMarshaller.marshal(config, os);

	}

	public void ajouter(ConfigUnit cu) throws FileNotFoundException, JAXBException {
		for (int i = 0; i < this.modelConfigUnits.size(); i++) {
			if (this.modelConfigUnits.get(i).nom.equals(cu.nom)) {
				this.modelConfigUnits.set(i, cu);

				this.sauvegarder();
				

				return;
			}

		}
		this.modelConfigUnits.addElement(cu);
	

		this.sauvegarder();
	}

	

	public void vider() {
		for (ZoneSaisie sz : this.zoneSaisies()) {
			if (sz != nom) {
				sz.textField.setText("0");
			} else {
				sz.textField.setText("");
			}
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == ajouter) {
			try {
				this.ajouter(this.creerConfigUnit());
			} catch (NumberFormatException ex) {

			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (e.getSource() == supprimer) {
			int idx = this.configUnits.getSelectedIndex();
			if (idx >= 0) {
				this.modelConfigUnits.removeElementAt(idx);
			}
			try {
				this.sauvegarder();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JAXBException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

		sb.paint("Editeur configuration");

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getSource() == this.configUnits) {
			int idx = this.configUnits.getSelectedIndex();
			if (idx >= 0) {
				this.initialiserConfigUnit(this.modelConfigUnits.get(idx));
			}

		}
	}

}
