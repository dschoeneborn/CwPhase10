package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import javax.swing.JPanel;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class CardPile extends JPanel {
	private static final long serialVersionUID = -6744980761177786388L;
	
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
