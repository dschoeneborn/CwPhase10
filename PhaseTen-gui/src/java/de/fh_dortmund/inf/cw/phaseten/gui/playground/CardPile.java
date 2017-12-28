package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import javax.swing.JPanel;

/**
 * @author Robin Harbecke
 *
 */
public class CardPile extends JPanel {
	protected Card card;
	
	public CardPile() {
		this.card = new Card();
		this.add(this.card);
	}
	
	public void updateData(Card card) {
		this.removeAll();
		this.card = card;
		this.add(this.card);
	}
}
