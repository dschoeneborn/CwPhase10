/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.shared;

import java.util.List;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ai.CardsToPileAction;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ai.TakeCardAction;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;

/**
 * @author Robin Harbecke
 */
public interface AIManagement {
	public TakeCardAction takeCard(CurrentPlayer player, Game game);
	
	public List<CardsToPileAction> cardsToPile(CurrentPlayer player, Game game);
	
	public Card discardCard(CurrentPlayer player, Game game);
}
