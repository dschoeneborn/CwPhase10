package de.fh_dortmund.inf.cw.phaseten.gui.playground.card;

import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;

/**
 * @author Robin Harbecke
 */
public abstract class CardPilePane extends JPanel {
	private static final long serialVersionUID = -6744980761177786388L;
	
	protected CardPane cardPane;
	
	public CardPilePane() {
		this.cardPane = new CardPane();
		this.add(this.cardPane);		
		this.setPreferredSize(CardPane.cardSize);
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		this.updateData(null);
	}
	
	public void updateData(Card card) {
		this.removeAll();		
		if(card == null)
		{
			this.cardPane = new CardPane();
			this.add(this.cardPane);
		}
		else
		{
			this.cardPane = new CardPane(card);
			this.add(this.cardPane);
		}
		this.revalidate();
		this.repaint();
	}
}
