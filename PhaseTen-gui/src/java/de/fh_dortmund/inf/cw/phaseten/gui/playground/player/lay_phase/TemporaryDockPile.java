package de.fh_dortmund.inf.cw.phaseten.gui.playground.player.lay_phase;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;

/**
 * @author Robin Harbecke
 *
 */
public class TemporaryDockPile extends DockPile{
	private static final long serialVersionUID = -164211083057026174L;

	public void clear()
	{
		super.clearCards();
	}

	@Override
	public boolean containsCard(Card card)
	{
		return super.containsCard(card);
	}

	@Override
	public boolean removeCard(Card card)
	{
		return super.removeCard(card);
	}

	@Override
	public boolean canAddCard(Card card)
	{
		return true;
	}

}
