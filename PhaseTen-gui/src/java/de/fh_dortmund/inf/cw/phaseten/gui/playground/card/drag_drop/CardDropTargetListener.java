package de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;

/**
 * @author Robin Harbecke
 *
 */
public class CardDropTargetListener implements DropTargetListener {
	ICardDropTarget dropTarget;
	
	public CardDropTargetListener(ICardDropTarget dropTarget) {
		this.dropTarget = dropTarget;					
	}
	
	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {

	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		Transferable transfarable = dtde.getTransferable();
		try {
			Card card = (Card) transfarable.getTransferData(CardTransfarable.cardFlavor);
			this.dropTarget.handleCardDrop(card);
		} catch (UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
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
