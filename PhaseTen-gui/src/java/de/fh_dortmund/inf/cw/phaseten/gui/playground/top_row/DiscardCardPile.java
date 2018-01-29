package de.fh_dortmund.inf.cw.phaseten.gui.playground.top_row;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.CardPilePane;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop.CardTransfarable;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotYourTurnException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.TakeCardBeforeDiscardingException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserIsSpectatorException;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 * @author Sven Krefeld
 *
 */
public class DiscardCardPile extends CardPilePane {
	private static final long serialVersionUID = 7330764386204801790L;

	protected ServiceHandler serviceHandler;

	public DiscardCardPile(ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
		this.setDropTarget(new DropTarget(this, new CardDropTargetListener()));
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				super.mouseClicked(evt);
				try {
					DiscardCardPile.this.serviceHandler.takeCardFromLiFoStack();
				} catch (MoveNotValidException e) {
					System.out.println("Move not valide");
				} catch (NotLoggedInException e) {
					System.out.println("Not logged in");
				} catch (GameNotInitializedException e) {
					System.out.println("Game not initialized");
				} catch (UserIsSpectatorException e) {
					System.out.println("User is Spectator");
				}
			}
		});
	}

	class CardDropTargetListener implements DropTargetListener {
		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {

		}

		@Override
		public void drop(DropTargetDropEvent dtde) {
			Transferable transfarable = dtde.getTransferable();
			Card card;
			try {
				card = (Card) transfarable.getTransferData(CardTransfarable.cardFlavor);
				DiscardCardPile.this.serviceHandler.layCardToLiFoStack(card.getId());
			} catch (UnsupportedFlavorException | IOException | NotYourTurnException | TakeCardBeforeDiscardingException e) {
			} catch (MoveNotValidException e) {
				System.out.println("Move not valide");
			} catch (NotLoggedInException e) {
				System.out.println("Not logged in");
			} catch (GameNotInitializedException e) {
				System.out.println("Game not initialized");
			} catch (UserIsSpectatorException e) {
				System.out.println("User is spectator");
			}
		}

		@Override
		public void dragOver(DropTargetDragEvent dtde) {

		}

		@Override
		public void dragExit(DropTargetEvent dte) {

		}

		@Override
		public void dragEnter(DropTargetDragEvent dtde) {

		}
	}

}
