package de.fh_dortmund.inf.cw.phaseten.gui.playground.card;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop.ICardDropTarget;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.OpenPileGuiData;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 * @author Sven Krefeld
 */
public class OpenPileDataPane extends PilePane implements ICardDropTarget{
	private static final long serialVersionUID = 7318654738136140956L;

	protected ServiceHandler serviceHandler;
	protected OpenPileGuiData dockPile;

	public OpenPileDataPane(ServiceHandler serviceHandler, OpenPileGuiData dockPile) {
		this.serviceHandler = serviceHandler;
		this.dockPile = dockPile;		
		this.updateData();
	}
	
	@Override
	public void handleCardDrop(Card card) {
		try {
			this.serviceHandler.addToPileOnTable(card.getId(), this.dockPile.getId());
		} catch (MoveNotValidException e) {		
		} catch (NotLoggedInException e) {
		} catch (GameNotInitializedException e) {			
		}
	}

	@Override
	protected Iterable<Card> getCards() {
		return this.dockPile.getCards();
	}

		
}
