package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import javax.swing.JPanel;

/**
 * @author Robin Harbecke
 *
 */
public class CardPile extends JPanel {
	protected CardPane card;
	
	public CardPile() {
		this.card = new CardPane();
		this.add(this.card);
	}
	
	public void updateData(CardPane card) {
		this.removeAll();
		this.card = card;
		this.add(this.card);
	}
}
