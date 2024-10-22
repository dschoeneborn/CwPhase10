package de.fh_dortmund.inf.cw.phaseten.gui.playground.player.lay_phase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.player.PlayerCardsPane;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ColorDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SequenceDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SetDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;

/**
 * @author Robin Harbecke
 * @author Björn Merschmeier
 */
public class LayPhaseCardsWrapperPane extends JPanel{
	private static final long serialVersionUID = 5268736642704635817L;

	private static final String equalNumber = "Equal number";
	private static final String cardSequence = "Card sequence";
	private static final String equalColor = "Equal color";
	private static final String empty = "Empty";

	protected TemporaryDockPilePane currentPile;
	protected JComboBox<String> typeComboBox = new JComboBox<>(new String[] {equalNumber,cardSequence,equalColor,empty});

	public LayPhaseCardsWrapperPane(ServiceHandler serviceHandler,PlayerCardsPane playerCardsPane, Class<? extends DockPile> pileType) {
		this.currentPile = new TemporaryDockPilePane(serviceHandler,playerCardsPane, pileType);
		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		this.add(this.typeComboBox);
		this.add(this.currentPile);

		this.typeComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LayPhaseCardsWrapperPane.this.clearPile();
				playerCardsPane.updateData();
			}
		});
	}

	public DockPile getLayDockPile() {
		Collection<Card> cards = this.currentPile.dockPile.getCopyOfCardsList();
		Card realCard = this.getRealCard(cards);
		if(realCard == null) return null;
		DockPile resultDockPile;
		switch ((String)this.typeComboBox.getSelectedItem()) {
		case equalNumber:
			resultDockPile = new SetDockPile(realCard.getCardValue());
			break;
		case equalColor:
			resultDockPile = new ColorDockPile(realCard.getColor());
			break;
		case cardSequence:
			resultDockPile = new SequenceDockPile();
			break;
		default:
			return null;
		}
		for (Card card : cards) {
			if(!resultDockPile.addLast(card)) {
				return null;
			}
		}
		return resultDockPile;
	}

	private Card getRealCard(Collection<Card> cards) {
		for (Card card : cards) {
			if(card.getCardValue() != CardValue.WILD && card.getCardValue() != CardValue.SKIP) {
				return card;
			}
		}
		return null;
	}

	public void clearPile() {
		this.currentPile.getDockPile().clear();
	}

	public Collection<Card> getCardsOnTempPile(){
		return this.currentPile.getDockPile().getCopyOfCardsList();
	}

	public boolean removeCardFromTempPile(Card card) {
		return this.currentPile.getDockPile().removeCard(card);
	}

	public void updateData(Collection<Card> allCards) {
		for (Card card : this.currentPile.dockPile.getCopyOfCardsList()) {
			if(!allCards.contains(card)) {
				this.currentPile.dockPile.removeCard(card);
			}
		}
		this.currentPile.updateData();
		this.revalidate();
		this.repaint();
	}
}
