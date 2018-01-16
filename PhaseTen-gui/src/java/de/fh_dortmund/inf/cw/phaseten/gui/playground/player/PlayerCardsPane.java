package de.fh_dortmund.inf.cw.phaseten.gui.playground.player;

import java.util.Collection;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.player.lay_phase.LayPhaseRowPane;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;

/**
 * @author Robin Harbecke
 *
 */
public class PlayerCardsPane extends JPanel {	
	private static final long serialVersionUID = -6715084044323813223L;
	
	protected Collection<Card> tempCollection;
	protected LayPhaseRowPane layPhaseRowPane;
	protected PlayerHandCardsPane playerHandCardsPane;
	
	public PlayerCardsPane(ServiceHandler serviceHandler){
		super();
		this.layPhaseRowPane = new LayPhaseRowPane(serviceHandler, this);
		this.playerHandCardsPane = new PlayerHandCardsPane(this);
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));		
		this.add(this.layPhaseRowPane);
		this.add(this.playerHandCardsPane);
	}
	
	public void updateData(Collection<Card> collection)
	{
		this.tempCollection = collection;		
		this.updateData();		
	}
	
	public void updateData() {
		this.playerHandCardsPane.updateData(this.tempCollection);
		this.layPhaseRowPane.updateData(this.tempCollection);
		this.revalidate();
		this.repaint();		
	}	
	
	public List<Card> getCardsOnTempPile() {
		return this.layPhaseRowPane.getCardsOnTempPile();
	}
	
	public boolean removeCardFromTempPile(Card card) {
		return this.layPhaseRowPane.removeCardFromTempPile(card);
	}
}
