package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

/**
 * @author Robin Harbecke
 *
 */
public class PlaygroundWindow extends JFrame {
	protected TopRowPane topRowPane = new TopRowPane();
	protected PublicCardStackPane publicCardStackPane = new PublicCardStackPane();
	protected PlayerCardsPane playerCardsPane = new PlayerCardsPane();
	protected StatusPanel statusPanel = new StatusPanel();
	
	public PlaygroundWindow() {
		super("Phaseten");
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.add(this.topRowPane);
		this.add(Box.createVerticalGlue());
		this.add(this.publicCardStackPane);
		this.add(this.playerCardsPane);
		this.add(this.statusPanel);
		this.setResizable(false);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
	}
	
	public void updateData() {
		this.topRowPane.updateData();
		this.publicCardStackPane.updateData();
		this.playerCardsPane.updateData();
		this.statusPanel.updateData();
		this.pack();
	}

}
