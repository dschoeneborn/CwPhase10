package de.fh_dortmund.inf.cw.phaseten.server.entities.ai;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;

/**
 * Class defines the cards which it wants added to the given DockPile
 * 
 * @author Robin Harbecke
 */
public class CardsToPileAction extends EmptyCardsToPileAction {
	private DockPile dockpile;
	private Iterable<Card> cards;
	private boolean dockPileAlreadyExisting;
	
	public CardsToPileAction(DockPile dockpile, Iterable<Card> cards, boolean dockPileAlreadyExisting) {
		super();
		this.dockpile = dockpile;
		this.cards = cards;
		this.dockPileAlreadyExisting = dockPileAlreadyExisting;
	}

	public DockPile getDockpile() {
		return dockpile;
	}
	
	public Iterable<Card> getCards() {
		return cards;
	}

	public boolean isDockPileAlreadyExisting() {
		return dockPileAlreadyExisting;
	}
	
}
