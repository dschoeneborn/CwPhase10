package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.ArrayList;
import java.util.List;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Color;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ColorDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.RoundStage;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SequenceDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SetDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Pile;

/**
 * @author Björn Merschmeier
 */
public class GameValidationBean
{
	/**
	 * @param g The game to check
	 * @param player The player who wants to draw a card
	 * @return
	 * @author Björn Merschmeier
	 */
	public boolean isValidDrawCardFromLiFoStack(Game g, Player player)
	{
		boolean pullCardAllowed = true;
		
		Card cardOnTop = g.getLiFoStack().showCard();
		
		if(cardOnTop.getCardValue() == CardValue.SKIP 
			|| !playerIsCurrentPlayer(g, player)
			|| player.getRoundStage() != RoundStage.PULL)
		{
			pullCardAllowed = false;
		}
		
		return pullCardAllowed;
	}
	
	public boolean isValidPushCardToLiFoStack(Game g, Player player, Card c)
	{
		boolean pushCardToLiFoStackAllowed = false;
		
		if(playerIsCurrentPlayer(g, player)
			&& player.getRoundStage() == RoundStage.PUSH
			&& playerHasCard(c, player)
			&& (!player.hasSkipCard()
				|| (player.hasSkipCard() && c.getCardValue() == CardValue.SKIP)))
		{
			pushCardToLiFoStackAllowed = true;
		}
		
		return pushCardToLiFoStackAllowed;
	}
	

	/**
	 * @param g The game to check
	 * @param player The player who wants to draw a card
	 * @return
	 * @author Björn Merschmeier
	 */
	public boolean isValidDrawCardFromPullStack(Game g, Player player)
	{
		boolean pullCardAllowed = true;
		
		Player currentPlayer = g.getCurrentPlayer();
		
		if(!playerIsCurrentPlayer(g, player)
			|| currentPlayer.getRoundStage() != RoundStage.PULL)
		{
			pullCardAllowed = false;
		}
		
		return pullCardAllowed;
	}
	
	/**
	 * @author Björn Merschmeier
	 * @param g
	 * @param p
	 * @param piles
	 * @return
	 */
	public boolean isValidLayStageToTable(Game g, Player p, ArrayList<Pile> piles)
	{
		boolean layStageDownAllowed = false;
		
		ArrayList<Card> cardsInPiles = new ArrayList<>();
		
		for(Pile pile: piles)
		{
			cardsInPiles.addAll(pile.getCards());
		}
		
		if(playerIsCurrentPlayer(g, p)
			&& !p.hasSkipCard()
			&& p.getRoundStage() == RoundStage.PUT
			&& !p.playerLaidStage()
			&& playerHasCards(cardsInPiles, p))
		{
			switch(p.getPhase())
			{
				case TWO_TRIPLES: layStageDownAllowed = areCardsReadyForPhase1(piles); break;
				case TRIPLE_AND_SEQUENCE_OF_FOUR: layStageDownAllowed = areCardsReadyForPhase2(piles); break;
				case QUADRUPLE_AND_SEQUENCE_OF_FOUR: layStageDownAllowed = areCardsReadyForPhase3(piles); break;
				case SEQUENCE_OF_SEVEN: layStageDownAllowed = areCardsReadyForPhase4(piles); break;
				case SEQUENCE_OF_EIGHT: layStageDownAllowed = areCardsReadyForPhase5(piles); break;
				case SEQUENCE_OF_NINE: layStageDownAllowed = areCardsReadyForPhase6(piles); break;
				case TWO_QUADRUPLES: layStageDownAllowed = areCardsReadyForPhase7(piles); break;
				case SEVEN_OF_ONE_COLOR: layStageDownAllowed = areCardsReadyForPhase8(piles); break;
				case QUINTUPLE_AND_TWIN: layStageDownAllowed = areCardsReadyForPhase9(piles); break;
				case QUINTUPLE_AND_TRIPLE: layStageDownAllowed = areCardsReadyForPhase10(piles); break;
				default: break;
			}
		}
		
		return layStageDownAllowed;
	}
	
	public boolean isValidToAddCard(Game g, Player p, Pile pile, Card c)
	{
		boolean addCardAllowed = false;
		
		if(playerIsCurrentPlayer(g, p)
			&& !p.hasSkipCard()
			&& p.getRoundStage() == RoundStage.PUT
			&& p.getPlayerPile().getSize() >= 1
			&& p.playerLaidStage()
			&& playerHasCard(c, p))
		{
			if(pile instanceof SetDockPile)
			{
				SetDockPile setDockPile = (SetDockPile) pile;
				
				if(setDockPile.getCardValue() == c.getCardValue())
				{
					addCardAllowed = true;
				}
			}
			else if(pile instanceof SequenceDockPile)
			{
				SequenceDockPile sequenceDockPile = (SequenceDockPile) pile;
			
				if((sequenceDockPile.getMinimum().getValue() - 1 == c.getCardValue().getValue()
						|| sequenceDockPile.getMaximum().getValue() + 1 == c.getCardValue().getValue()
						|| c.getCardValue() == CardValue.JOKER)
						&& !pileIsFull(sequenceDockPile))
				{
					addCardAllowed = true;
				}
			}
			else if(pile instanceof ColorDockPile)
			{
				ColorDockPile colorDockPile = (ColorDockPile) pile;
				
				if(colorDockPile.getColor() == c.getColor())
				{
					addCardAllowed = true;
				}
			}
		}
			
		return addCardAllowed;
	}
	
	public boolean isValidLaySkipCard(Player currentPlayer, Player destinationPlayer, Game g)
	{
		boolean canLaySkipCard = false;
		
		Card skipCard = new Card(Color.NONE, CardValue.SKIP);
		
		if(playerHasCard(skipCard, currentPlayer)
			&& currentPlayer.getRoundStage() == RoundStage.PUSH
			&& playerIsCurrentPlayer(g, currentPlayer)
			&& !currentPlayer.hasSkipCard()
			&& !destinationPlayer.hasSkipCard())
		{
			canLaySkipCard = true;
		}
		
		return canLaySkipCard;		
	}
	
	private boolean playerHasCards(List<Card> cards, Player p)
	{
		boolean userHasCards = true;
		
		for(Card card : cards)
		{
			if(!playerHasCard(card, p))
			{
				userHasCards = false;
			}
		}
		
		return userHasCards;
	}
	
	private boolean playerHasCard(Card card, Player p)
	{
		boolean playerHasCard = false;
		
		for(Card c : p.getPlayerPile().getCards())
		{
			if(c.getCardValue() == card.getCardValue()
					&& c.getColor() == card.getColor())
			{
				playerHasCard = true;
				break;
			}
		}
		
		return playerHasCard;
	}

	private boolean pileIsFull(SequenceDockPile sequenceDockPile) {
		return sequenceDockPile.getMinimum() == CardValue.ONE && sequenceDockPile.getMaximum() == CardValue.TWELVE;
	}


	/**
	 * @author Björn Merschmeier
	 * @param piles
	 * @return
	 */
	private boolean areCardsReadyForPhase1(ArrayList<Pile> piles) {
		return (piles.size() == 2
			&& pileIsSet(piles.get(0), 3)
			&& pileIsSet(piles.get(1), 3));
	}

	/**
	 * @author Björn Merschmeier
	 * @param piles
	 * @return
	 */
	private boolean areCardsReadyForPhase2(ArrayList<Pile> piles) {
		return (piles.size() == 2
			&& (
					(pileIsSet(piles.get(0), 3)
							&& pileIsSequence(piles.get(1), 4))
					|| (pileIsSet(piles.get(1), 3)
							&& pileIsSequence(piles.get(0), 4))
			)
		);
	}


	/**
	 * @author Björn Merschmeier
	 * @param piles
	 * @return
	 */
	private boolean areCardsReadyForPhase3(ArrayList<Pile> piles) {
		return (piles.size() == 2
				&& (
						(pileIsSet(piles.get(0), 4)
								&& pileIsSequence(piles.get(1), 4))
						|| (pileIsSet(piles.get(1), 4)
								&& pileIsSequence(piles.get(0), 4))
				)
			);
	}

	/**
	 * @author Björn Merschmeier
	 * @param piles
	 * @return
	 */
	private boolean areCardsReadyForPhase4(ArrayList<Pile> piles) {
		return (piles.size() == 1
				&& pileIsSequence(piles.get(0), 7)
			);
	}

	/**
	 * @author Björn Merschmeier
	 * @param piles
	 * @return
	 */
	private boolean areCardsReadyForPhase5(ArrayList<Pile> piles) {
		return (piles.size() == 1
				&& pileIsSequence(piles.get(0), 8)
			);
	}

	/**
	 * @author Björn Merschmeier
	 * @param piles
	 * @return
	 */
	private boolean areCardsReadyForPhase6(ArrayList<Pile> piles) {
		return (piles.size() == 1
				&& pileIsSequence(piles.get(0), 9)
			);
	}

	/**
	 * @author Björn Merschmeier
	 * @param piles
	 * @return
	 */
	private boolean areCardsReadyForPhase7(ArrayList<Pile> piles) {
		return (piles.size() == 2
				&& pileIsSet(piles.get(0), 4)
				&& pileIsSet(piles.get(1), 4));
	}

	/**
	 * @author Björn Merschmeier
	 * @param piles
	 * @return
	 */
	private boolean areCardsReadyForPhase8(ArrayList<Pile> piles) {
		return (piles.size() == 1
				&& piles.get(0) instanceof SetDockPile
				&& piles.get(0).getSize() == 7);
	}

	/**
	 * @author Björn Merschmeier
	 * @param piles
	 * @return
	 */
	private boolean areCardsReadyForPhase9(ArrayList<Pile> piles) {
		return (piles.size() == 2
				&& (
						(pileIsSet(piles.get(0), 2)
								&& pileIsSet(piles.get(1), 5))
						|| (pileIsSet(piles.get(1), 2)
								&& pileIsSet(piles.get(0), 5))
				)
			);
	}

	/**
	 * @author Björn Merschmeier
	 * @param g The game to check
	 * @param player The player who wants to draw a card
	 * @return
	 */	
	private boolean areCardsReadyForPhase10(ArrayList<Pile> piles) {
		return (piles.size() == 2
				&& (
						(pileIsSet(piles.get(0), 3)
								&& pileIsSet(piles.get(1), 5))
						|| (pileIsSet(piles.get(1), 3)
								&& pileIsSet(piles.get(0), 5))
				)
			);
	}

	/**
	 * @author Björn Merschmeier
	 * @param pile
	 * @param i
	 * @return
	 */
	private boolean pileIsSet(Pile pile, int i) {
		return (pile instanceof SetDockPile && pile.getSize() == i);
	}
	

	/**
	 * @author Björn Merschmeier
	 * @param pile
	 * @param i
	 * @return
	 */
	private boolean pileIsSequence(Pile pile, int i) {
		return (pile instanceof SequenceDockPile && pile.getSize() == i);
	}


	/**
	 * @param g The game to check
	 * @param player The player who wants to draw a card
	 * @return
	 * @author Björn Merschmeier
	 */
	private boolean playerIsCurrentPlayer(Game g, Player p)
	{
		Player currentPlayer = g.getCurrentPlayer();
		
		return (currentPlayer.getId() == p.getId());
	}
}
