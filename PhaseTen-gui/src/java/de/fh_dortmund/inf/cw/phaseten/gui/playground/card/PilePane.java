package de.fh_dortmund.inf.cw.phaseten.gui.playground.card;

import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.dnd.DropTarget;

import javax.swing.JPanel;
import javax.swing.TransferHandler;

import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop.CardDropTargetListener;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop.ICardDropTarget;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;

/**
 * @author Robin Harbecke
 *
 */
public abstract class PilePane extends JPanel implements ICardDropTarget{
	private static final long serialVersionUID = 1900499849050555937L;

	public PilePane() {
		this.setLayout(new FlowLayout());
		this.setTransferHandler(new TransferHandler("baseCard"));
		this.setDropTarget(new DropTarget(this, new CardDropTargetListener(this)));
	}

	@Override
	public abstract void handleCardDrop(Card card, Point point);

	protected abstract Iterable<Card> getCards();

	public void updateData() {
		this.removeAll();
		for (Card card : this.getCards()) {
			this.add(this.getCardPane(card));
		}
		this.revalidate();
		this.repaint();
	}

	protected CardPane getCardPane(Card card) {
		return new CardPane(card);
	}
}