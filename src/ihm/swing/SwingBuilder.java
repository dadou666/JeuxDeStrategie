package ihm.swing;

import java.awt.Color;
import java.awt.Insets;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import ihm.Component;
import ihm.UIBuilder;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class SwingBuilder extends UIBuilder<JComponent> {
	public JFrame frame;
	public Point size;
	public List<Painter> painter = new ArrayList<>();

	public SwingBuilder(JFrame frame) {
		this.frame = frame;
	}

	public SwingBuilder() {

	}

	public void paint(String titre) {

		for (Painter p : painter) {
			p.paint();
		}
		open(titre);

	}

	static public void setLookAndFeel() {

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					UIManager.put("nimbusBase", Color.DARK_GRAY);
					UIManager.put("nimbusBlueGrey", Color.LIGHT_GRAY);
					UIManager.put("control", Color.LIGHT_GRAY);
				}
			}
		} catch (Exception e) {

		}
	}

	public void open(String title) {
		if (composite.parent != null) {
			return;
		}
		composite.computePosition();
		JFrame tmp = frame;
		frame = new JFrame();
		frame.pack();
		Insets i = frame.getInsets();
		if (tmp != null) {
			frame = tmp;
		} else {
			frame = new JFrame();
		}
		frame.setResizable(false);
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (size != null) {
			frame.setSize(size.x + i.left + i.right, size.y + i.top + i.bottom);
		} else {
			frame.setSize(composite.width + i.left + i.right, composite.height + i.top + i.bottom);
		}
		frame.setLayout(null);
		frame.getContentPane().removeAll();
		composite.build(this);
		// frame.pack();
		frame.setVisible(true);
		composite = null;

	}

	public void openIn(String title, JFrame oldFrame) {
		if (composite.parent != null) {
			return;
		}
		composite.computePosition();

		frame = new JFrame();
		frame.pack();
		Insets i = frame.getInsets();
		frame = oldFrame;
		frame.setResizable(false);
		frame.setTitle(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (size != null) {
			frame.setSize(size.x + i.left + i.right, size.y + i.top + i.bottom);
		} else {
			frame.setSize(composite.width + i.left + i.right, composite.height + i.top + i.bottom);
		}
		frame.setLayout(null);
		frame.getContentPane().removeAll();

		composite.build(this);
		composite = null;
		// frame.pack();
		frame.setVisible(true);

	}

	public void reopen(String title) {

		this.openIn(title, frame);
	}

	@Override
	public void buildFrom(Component<JComponent> component) {
		// TODO Auto-generated method stub
		component.component.setSize(component.width, component.height);
		component.component.setLocation(component.x, component.y);
		frame.getContentPane().add(component.component);
	}

}
