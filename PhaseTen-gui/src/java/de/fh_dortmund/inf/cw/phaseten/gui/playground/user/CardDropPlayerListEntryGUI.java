package de.fh_dortmund.inf.cw.phaseten.gui.playground.user;

import java.awt.Point;
import java.awt.dnd.DropTarget;

import javax.swing.TransferHandler;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop.CardDropTargetListener;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop.ICardDropTarget;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;

/**
 * @author Robin Harbecke
 */
public class CardDropPlayerListEntryGUI extends PlayerListEntryGUI implements ICardDropTarget{
	private static final long serialVersionUID = 1356010565441425778L;

	protected ServiceHandler serviceHandler;

	public CardDropPlayerListEntryGUI(PlayerGuiData player, boolean isCurrentPlayer, ServiceHandler serviceHandler) {
		super(player, isCurrentPlayer);
		this.serviceHandler = serviceHandler;
		this.setTransferHandler(new TransferHandler("baseCard"));
		this.setDropTarget(new DropTarget(this, new CardDropTargetListener(this)));
	}

	@Override
	public void handleCardDrop(Card card, Point p) {
		if(card.getCardValue().equals(CardValue.SKIP)){
			try {
				this.serviceHandler.laySkipCardForPlayer(this.player.getId(),card.getId());
			} catch (MoveNotValidException e) {
				System.out.println("Move not valide");
			} catch (NotLoggedInException e) {
				System.out.println("Not logged in");
			} catch (PlayerDoesNotExistsException e) {
				System.out.println("Player does not exist");
			} catch (GameNotInitializedException e) {
				System.out.println("Game not initialized");
			}
		}

	}
}
