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

	/**
	 * Konstruktor.
	 * 
	 * @param dockpile
	 * @param cards
	 * @param dockPileAlreadyExisting
	 */
	public CardsToPileAction(DockPile dockpile, Iterable<Card> cards, boolean dockPileAlreadyExisting) {
		super();
		this.dockpile = dockpile;
		this.cards = cards;
		this.dockPileAlreadyExisting = dockPileAlreadyExisting;
	}

	/**
	 * Liefert DockPile
	 * 
	 * @return dockpile
	 */
	public DockPile getDockpile() {
		return dockpile;
	}

	/**
	 * Liefert Karten.
	 * 
	 * @return cards
	 */
	public Iterable<Card> getCards() {
		return cards;
	}

	/**
	 * Liefert dockPileAlreadyExisting
	 * 
	 * @return dockPileAlreadyExisting
	 */
	public boolean isDockPileAlreadyExisting() {
		return dockPileAlreadyExisting;
	}

}
