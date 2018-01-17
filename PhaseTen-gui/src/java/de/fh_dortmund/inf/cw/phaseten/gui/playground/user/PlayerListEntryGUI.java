package de.fh_dortmund.inf.cw.phaseten.gui.playground.user;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.dnd.DropTarget;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop.CardDropTargetListener;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop.ICardDropTarget;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class PlayerListEntryGUI extends JPanel implements UserListEntry, ICardDropTarget{	
	private static final long serialVersionUID = 1356010565441425778L;
	
	protected ServiceHandler serviceHandler;
	protected PlayerGuiData player;
	
	protected JLabel textLabel;
	
	public PlayerListEntryGUI(PlayerGuiData player,ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
		this.player = player;
		this.setLayout(new FlowLayout());
		this.setForeground(new Color(0, 153, 0));
		this.setBackground(new Color(255, 0, 0));
		this.textLabel = new JLabel(this.player.getName() + "(Phase " + this.player.getPhase() + ")");
		this.add(this.textLabel);
		this.setTransferHandler(new TransferHandler("baseCard"));
		this.setDropTarget(new DropTarget(this, new CardDropTargetListener(this)));
		this.revalidate();
		this.repaint();
	}

	@Override
	public void handleCardDrop(Card card) {
		if(card.getCardValue().equals(CardValue.SKIP)){
			//TODO this.serviceHandler.laySkipCardForPlayer(this.player,card.id);
		}
		
	}	
}
