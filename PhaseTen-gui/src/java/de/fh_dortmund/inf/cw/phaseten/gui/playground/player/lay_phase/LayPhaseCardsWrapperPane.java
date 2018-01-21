package de.fh_dortmund.inf.cw.phaseten.gui.playground.player.lay_phase;

import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.player.PlayerCardsPane;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;

/**
 * @author Robin Harbecke
 * @author Björn Merschmeier
 */
public class LayPhaseCardsWrapperPane extends JPanel{
	private static final long serialVersionUID = 5268736642704635817L;

	protected TemporaryDockPilePane currentPile;

	public LayPhaseCardsWrapperPane(ServiceHandler serviceHandler,PlayerCardsPane playerCardsPane, Class<? extends DockPile> pileType) {
		this.currentPile = new TemporaryDockPilePane(serviceHandler,playerCardsPane, pileType);
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(this.currentPile);
		this.repaint();
	}

	public Collection<Card> getCardsOnTempPile(){
		return this.currentPile.getDockPile().getCopyOfCardsList();
	}

	public boolean removeCardFromTempPile(Card card) {
		return this.currentPile.getDockPile().removeCard(card);
	}

	public void updateData(Collection<Card> allCards, Class<? extends DockPile> pileType) {
		if(this.currentPile.dockPile.getCopyOfCardsList().size() == 0)
		{
			try {
				this.currentPile.dockPile = this.currentPile.dockPile.getClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		if(!this.currentPile.dockPile.getClass().equals(pileType))
		{
			try {
				this.currentPile.dockPile = pileType.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		for (Card card : this.currentPile.dockPile.getCopyOfCardsList()) {
			if(!allCards.contains(card)) {
				this.currentPile.dockPile.removeCard(card);
			}
		}
		this.currentPile.updateData();
		this.revalidate();
		this.repaint();
	}

	public DockPile getCurrentPile() {
		return this.currentPile.getDockPile();
	}
}
