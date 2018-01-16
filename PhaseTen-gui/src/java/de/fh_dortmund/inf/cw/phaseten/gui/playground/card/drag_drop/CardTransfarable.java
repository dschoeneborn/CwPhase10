package de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;

/**
 * @author Robin Harbecke
 *
 */
public class CardTransfarable implements Transferable {
	public static final DataFlavor cardFlavor = new DataFlavor(Card.class, "Card Object");

	protected static final DataFlavor[] supportedFlavors = { cardFlavor };

	private Card card;

	public CardTransfarable(Card card) {
		this.card = card;
	}

	public DataFlavor[] getTransferDataFlavors() {
		return supportedFlavors;
	}

	public boolean isDataFlavorSupported(DataFlavor flavor) {
		if (flavor.equals(cardFlavor))
			return true;
		return false;
	}

	public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
		if (flavor.equals(cardFlavor))
			return this.card;
		else
			throw new UnsupportedFlavorException(flavor);
	}
}
