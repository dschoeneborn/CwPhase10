package de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop;

import java.awt.Cursor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;

import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.CardPane;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class DragableCardPane extends CardPane implements DragGestureListener {
	private static final long serialVersionUID = -8652026530502078151L;

	public DragableCardPane(Card baseCard) {
		super(baseCard);
	}

	@Override
	protected void init() {
		super.init();
		DragSource ds = new DragSource();
		ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, this);
	}

	public void dragGestureRecognized(DragGestureEvent event) {
		Cursor cursor = null;
		if (event.getDragAction() == DnDConstants.ACTION_COPY) {
			cursor = DragSource.DefaultCopyDrop;
		}

		event.startDrag(cursor, new CardTransfarable(this.getBaseCard()));
	}
}
