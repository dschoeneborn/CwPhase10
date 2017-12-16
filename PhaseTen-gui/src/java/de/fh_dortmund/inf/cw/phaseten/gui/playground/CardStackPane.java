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
		Card card = new Card();
		this.add(card);
	}
}
