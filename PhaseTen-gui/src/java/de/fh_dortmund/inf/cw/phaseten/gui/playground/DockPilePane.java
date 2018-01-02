package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import java.awt.FlowLayout;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.TransferHandler;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.CardCannotBeAddedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotYourTurnException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PhaseNotCompletedException;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class DockPilePane extends JPanel {
	private static final long serialVersionUID = 7318654738136140956L;
	
	protected ServiceHandler serviceHandler;
	protected DockPile dockPile;

	public DockPilePane(ServiceHandler serviceHandler, DockPile dockPile) {
		this.serviceHandler = serviceHandler;
		this.dockPile = dockPile;
		this.setLayout(new FlowLayout());
		this.setTransferHandler(new TransferHandler("baseCard"));
		this.setDropTarget(new DropTarget(this, new CardDropTargetListener()));
		
		this.updateData();
	}

	protected void updateData() {
		this.removeAll();
		for (Card card : this.dockPile.getCards()) {
			CardPane cardGui = new CardPane(card);
			this.add(cardGui);
		}		
	}

	class CardDropTargetListener implements DropTargetListener {
		@Override
		public void dropActionChanged(DropTargetDragEvent dtde) {

		}

		@Override
		public void drop(DropTargetDropEvent dtde) {
			Transferable transfarable = dtde.getTransferable();
			try {
				Card card = (Card) transfarable.getTransferData(CardTransfarable.cardFlavor);
				DockPilePane.this.serviceHandler.addToOpenPile(card, DockPilePane.this.dockPile);
			} catch (UnsupportedFlavorException | IOException | NotYourTurnException | CardCannotBeAddedException
					| PhaseNotCompletedException e) {
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

}
