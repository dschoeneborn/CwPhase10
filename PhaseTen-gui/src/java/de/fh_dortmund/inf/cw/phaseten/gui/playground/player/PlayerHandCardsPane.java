package de.fh_dortmund.inf.cw.phaseten.gui.playground.player;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.CardPane;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.PilePane;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop.DragableCardPane;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;

/**
 * @author Robin Harbecke
 */
public class PlayerHandCardsPane extends JPanel{
	private static final long serialVersionUID = -4081504045974992274L;


	protected PlayerCardsPane playerCardsPane;
	protected Iterable<Card> allCards = null;

	protected PlayerHandCardPilePane playerHandCardPilePane;

	protected JScrollPane scrollPane;

	public PlayerHandCardsPane(PlayerCardsPane playerCardsPane) {
		this.playerCardsPane = playerCardsPane;
		this.playerHandCardPilePane = new PlayerHandCardPilePane();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.scrollPane = new JScrollPane(this.playerHandCardPilePane);
		this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.add(this.scrollPane);
		this.setPreferredSize(new Dimension(CardPane.cardSize.width*11+200, CardPane.cardSize.height+30));
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
	}

	public void updateData(Iterable<Card> allCards)
	{
		this.allCards = allCards;
		this.playerHandCardPilePane.updateData();
		this.revalidate();
		this.repaint();
	}

	class PlayerHandCardPilePane extends PilePane{
		private static final long serialVersionUID = -6908148771021421825L;

		@Override
		public void handleCardDrop(Card card, Point p) {
			PlayerHandCardsPane.this.playerCardsPane.removeCardFromTempPile(card);
			PlayerHandCardsPane.this.playerCardsPane.updateData();
		}

		@Override
		protected Iterable<Card> getCards() {
			List<Card> result = new ArrayList<>();
			List<Card> tempPileCards = PlayerHandCardsPane.this.playerCardsPane.getCardsOnTempPile();
			for (Card card : PlayerHandCardsPane.this.allCards) {
				if(tempPileCards.contains(card)) {
					tempPileCards.remove(card);
				}else {
					result.add(card);
				}
			}
			return result;
		}

		@Override
		protected CardPane getCardPane(Card card) {
			return new DragableCardPane(card);
		}
	}

}
