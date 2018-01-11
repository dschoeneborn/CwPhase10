package de.fh_dortmund.inf.cw.phaseten.gui.playground.player;

import java.awt.FlowLayout;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.CardPane;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.DragableCardPane;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;

/**
 * @author Robin Harbecke
 */
public class PlayerCardsPane extends JPanel{	
	private static final long serialVersionUID = -4081504045974992274L;
	
	protected JScrollPane scrollPane;
	protected JPanel cardList = new JPanel();
	
	public PlayerCardsPane() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));		
		this.cardList.setLayout(new FlowLayout());
		this.scrollPane = new JScrollPane(this.cardList);
		this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.add(this.scrollPane);			
	}	
	
	public void updateData(Collection<Card> collection)
	{
		this.cardList.removeAll();
		for (Card card : collection) {
			CardPane cardPane = new DragableCardPane(card);
			this.cardList.add(cardPane);
		}
	}	

}
