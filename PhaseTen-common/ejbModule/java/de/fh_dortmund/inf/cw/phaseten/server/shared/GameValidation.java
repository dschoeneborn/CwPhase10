package de.fh_dortmund.inf.cw.phaseten.server.shared;

import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Pile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;

/**
 * 
 * @author Björn Merschmeier
 *
 */
public interface GameValidation {

	boolean isValidDrawCardFromLiFoStack(Game g, Player player);
	boolean isValidPushCardToLiFoStack(Game g, Player player, Card c);
	boolean isValidDrawCardFromPullStack(Game game, Player player);
	boolean isValidLayStageToTable(Game g, Player p, Collection<DockPile> piles);
	boolean isValidToAddCard(Game g, Player p, Pile pile, Card c);
	boolean isValidLaySkipCard(Player currentPlayer, Player destinationPlayer, Game g);
}
