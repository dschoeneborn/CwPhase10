package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotYourTurnException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.TakeCardBeforeDiscardingException;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 *
 */
public class DiscardCardPile extends CardPile {
	private static final long serialVersionUID = 7330764386204801790L;
	
	protected ServiceHandler serviceHandler;
	
	public DiscardCardPile(ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
		this.setDropTarget(new DropTarget(this, new CardDropTargetListener()));
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
				DiscardCardPile.this.serviceHandler.discardCardToDiscardPile(card);
			} catch (UnsupportedFlavorException | IOException | NotYourTurnException | TakeCardBeforeDiscardingException e) {				
			}						
			return;
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
