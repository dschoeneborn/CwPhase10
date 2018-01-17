package de.fh_dortmund.inf.cw.phaseten.gui.playground.user;

import java.awt.dnd.DropTarget;

import javax.swing.TransferHandler;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop.CardDropTargetListener;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop.ICardDropTarget;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;

/**
 * @author Robin Harbecke 
 */
public class CardDropPlayerListEntryGUI extends PlayerListEntryGUI implements ICardDropTarget{	
	private static final long serialVersionUID = 1356010565441425778L;
	
	protected ServiceHandler serviceHandler;	
	
	public CardDropPlayerListEntryGUI(PlayerGuiData player,ServiceHandler serviceHandler) {
		super(player);		
		this.serviceHandler = serviceHandler;			
		this.setTransferHandler(new TransferHandler("baseCard"));
		this.setDropTarget(new DropTarget(this, new CardDropTargetListener(this)));		
	}

	@Override
	public void handleCardDrop(Card card) {
		if(card.getCardValue().equals(CardValue.SKIP)){
			//TODO this.serviceHandler.laySkipCardForPlayer(this.player,card.id);
		}
		
	}	
}
