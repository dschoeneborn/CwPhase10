package de.fh_dortmund.inf.cw.phaseten.server.beans.ai;

import java.util.ArrayList;
import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PlayerPile;

/**
 * @author Robin Harbecke
 */
public class SimPile {
	private Iterable<Card> cards;

	private SimPile(Iterable<Card> cards) {
		this.cards = cards;
	}

	public static SimPile from(Iterable<Card> cards) {
		return new SimPileBuilder()
				.addCards(cards)
				.build();
	}

	public static SimPile from(Iterable<Card> cards, Card card) {
		return new SimPileBuilder()
				.addCards(cards)
				.addCard(card)
				.build();
	}

	public static SimPile from(PlayerPile playerPile) {
		return from(playerPile.getCopyOfCardsList());
	}

	public static SimPile from(PlayerPile playerPile, Card card) {
		return from(playerPile.getCopyOfCardsList(), card);
	}

	public static SimPile from(SimPile simPile) {
		return from(simPile.getCards());
	}

	public static SimPile from(SimPile simPile, Card card) {
		return from(simPile.getCards(), card);
	}

	public SimPile addCard(Card card) {
		return from(this.cards, card);
	}

	public SimPile removeCard(Card card) {
		return new SimPileBuilder()
				.addCards(this.cards)
				.removeCard(card)
				.build();
	}

	public Iterable<Card> getCards(){
		return this.cards;
	}

	public static SimPile empty = new SimPileBuilder().build();

	private static class SimPileBuilder {
		private Collection<Card> cards = new ArrayList<>();

		private SimPileBuilder addCards(Iterable<Card> cards) {
			for(Card _card : cards) {
				this.cards.add(_card);
			}
			return this;
		}

		private SimPileBuilder addCard(Card card) {
			this.cards.add(card);
			return this;
		}

		private SimPileBuilder removeCard(Card card) {
			this.cards.remove(card);
			return this;
		}

		private SimPile build() {
			return new SimPile(this.cards);
		}
	}
}
