package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.GuiFrame;

/**
 * @author Robin Harbecke
 *
 */
public class PlaygroundWindow extends GuiFrame{
	protected TopRowPane topRowPane;
	protected PublicCardStackPane publicCardStackPane = new PublicCardStackPane();
	protected PlayerCardsPane playerCardsPane = new PlayerCardsPane();
	protected StatusPanel statusPanel = new StatusPanel();
	
	public PlaygroundWindow(ServiceHandler serviceHandler) {
		super("Phaseten",serviceHandler);
		this.topRowPane = new TopRowPane(serviceHandler);
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
