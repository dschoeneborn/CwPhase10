/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.shared;

import java.util.List;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ai.CardsToPileAction;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ai.TakeCardAction;

/**
 * @author Robin Harbecke
 */
public interface AIManagement {
	public TakeCardAction takeCard(Player player, Game game);
	
	public List<CardsToPileAction> cardsToPile(Player player, Game game);
	
	public Card discardCard(Player player, Game game);
}
