package de.fh_dortmund.inf.cw.phaseten.server.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;

/**
 * @author Tim Prange
 */
public class OpenPileGuiData implements Serializable {
	private static final long serialVersionUID = 2259240094135145302L;

	private Collection<Card> cards;
	private long id;

	public OpenPileGuiData(Collection<Card> cards, long id) {
		this.cards = cards;
		this.id = id;
	}

	public OpenPileGuiData()
	{
		this.cards = new ArrayList<>();
	}

	/**
	 * Returns cards
	 *
	 * @return the cards
	 */
	public Collection<Card> getCards() {
		return cards;
	}

	/**
	 * Generates a OpenPile-object for the gui from the persisted dockpile-object of the server
	 * @param pile
	 * @return
	 */
	public static OpenPileGuiData from(DockPile pile) {
		return new OpenPileGuiData(pile.getCopyOfCardsList(), pile.getId());
	}

	/**
	 * Does the same like the other from-method, but with a collection of piles
	 * @param piles
	 * @return
	 */
	public static Collection<OpenPileGuiData> from(Collection<DockPile> piles) {
		Collection<OpenPileGuiData> _piles = new ArrayList<>();

		for (DockPile dockPile : piles) {
			_piles.add(OpenPileGuiData.from(dockPile));
		}

		return _piles;
	}

	/**
	 * Returns id
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}
}
