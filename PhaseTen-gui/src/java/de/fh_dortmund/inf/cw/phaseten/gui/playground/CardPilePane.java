package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;

/**
 * @author Robin Harbecke
 */
public abstract class CardPilePane extends JPanel {
	private static final long serialVersionUID = -6744980761177786388L;
	
	protected CardPane card;
	
	public CardPilePane() {
		this.card = new CardPane();
		this.add(this.card);
	}
	
	public void updateData(Card card) {
		this.removeAll();
		this.card = new CardPane(card);
		this.add(this.card);
	}
}
