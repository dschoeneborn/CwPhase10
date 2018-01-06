package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.AISimplePlayerImpl;
import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.IAIPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ai.CardsToPileAction;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ai.TakeCardAction;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.shared.AIManagementLocal;

/**
 * @author Robin Harbecke
 */
@Stateless
public class AIManagmentBean implements AIManagementLocal {
	private static IAIPlayer aiPlayer = new AISimplePlayerImpl();
	
	/**
	 * First AI action in a single turn. Defines which card the ai wants to take
	 * <br><br>
	 * 
	 * # Example
	 * <br><br>
	 * 
		switch (takeCard(CurrentPlayer.from(...), Game.from(...))) {
			case TakeCardAction.DISCARD_PILE: {
				// Add Card from DiscardPile to the CurrentPlayer<br>
			}
			case TakeCardAction.DRAWER_PILE: {
				// Add Card from DrawerPile to the CurrentPlayer<br>
			}
		}
	 */
	public TakeCardAction takeCard(CurrentPlayer player, Game game) {
		return aiPlayer.takeCard(player, game);
	}
	
	/**
	 * Second AI action in a single turn. Should be called as long as the return class is of instance TakeCardAction.<br>
	 * TakeCardAction returns the DockPile and the cards to add. The cards will not be added to he DockPile by the ai.
	 * <br><br>
	 * 
	 * # Example
	 * <br><br>
	 * 
		List<CardsToPileAction> actions;
		do {
			actions = cardsToPile(CurrentPlayer.from(...), Game.from(...));
			for(CardsToPileAction action : actions) {
				// Add the Cards from action.getCards() to the DockPile from action.getDockPile() if the move is valid
				if(!action.isDockPileAlreadyExisting()) {
					// Add the DockPile to the Game
				}
			}
		} while(actions.size() > 0);
	 */
	public List<CardsToPileAction> cardsToPile(CurrentPlayer player, Game game) {
		return aiPlayer.cardsToPile(player, game);
	}
	
	/**
	 * Third AI action in a single turn. Discards the returned Card to the DiscardPile
	 * <br><br>
	 * 
	 * # Example
	 * <br><br>
	 * 
		Card card = discardCard(CurrentPlayer.from(...), Game.from(...));
		// Remove Card from CurrentPlayer Array 
	 */
	public Card discardCard(CurrentPlayer player, Game game) {
		return aiPlayer.discardCard(player, game);
	}
}
