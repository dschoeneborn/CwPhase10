package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import javax.swing.JPanel;

/**
 * @author Robin Harbecke
 *
 */
public class CardStackPane extends JPanel {
	public CardStackPane() {
		
	}
	
	public void updateData() {//todo
		this.removeAll();
		CardPane card = new CardPane();
		this.add(card);
	}
}
