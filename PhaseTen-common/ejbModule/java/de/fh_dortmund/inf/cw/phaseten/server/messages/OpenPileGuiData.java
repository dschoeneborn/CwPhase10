package de.fh_dortmund.inf.cw.phaseten.server.messages;

import java.util.ArrayList;
import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;

/**
 * @author Tim Prange Created on 2018-01-06 TODO Add JavaDoc
 */
public class OpenPileGuiData {
	private Collection<Card> cards;
	private long id;

	public OpenPileGuiData(Collection<Card> cards, long id) {
		this.cards = cards;
		this.id = id;
	}
	
	public OpenPileGuiData()
	{
		this.cards = new ArrayList<Card>();
	}

	/**
	 * Returns cards
	 *
	 * @return the cards
	 */
	public Collection<Card> getCards() {
		return cards;
	}

	public static OpenPileGuiData from(DockPile pile) {
		return new OpenPileGuiData(pile.getCards(), pile.getId());
	}

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
