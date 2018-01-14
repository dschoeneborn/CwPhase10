/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 */
public class SetDockPile extends DockPile {
	private static final long serialVersionUID = -5890944285337742574L;

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private CardValue cardValue;

	private SetDockPile() {
		super();
	}

	/**
	 * @param cardValue
	 */
	public SetDockPile(CardValue cardValue) {
		this();
		this.cardValue = cardValue;
	}

	public CardValue getCardValue() {
		return cardValue;
	}

	@Override
	public boolean canAddCard(Card card)
	{
		return card.getCardValue().equals(this.cardValue) || card.getCardValue().equals(CardValue.WILD);
	}
}
