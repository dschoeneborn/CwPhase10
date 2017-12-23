package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.ArrayList;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ColorDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.RoundStage;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SequenceDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SetDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Stage;
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
		Player currentPlayer = g.getCurrentPlayer();
		
		if(cardOnTop.getCardValue() == CardValue.SKIP 
			|| !playerIsCurrentPlayer(g, player)
			|| currentPlayer.getRoundStage() != RoundStage.PULL)
		{
			pullCardAllowed = false;
		}
		
		return pullCardAllowed;
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
		
		Player currentPlayer = g.getCurrentPlayer();
		
		if(playerIsCurrentPlayer(g, p)
			&& currentPlayer.getRoundStage() == RoundStage.PUT
			&& !currentPlayer.playerLaidStage())
		{
			switch(currentPlayer.getPhase())
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
		
		Player currentPlayer = g.getCurrentPlayer();
		
		if(playerIsCurrentPlayer(g, p)
			&& currentPlayer.getRoundStage() == RoundStage.PUT
			&& currentPlayer.getPlayerPile().getSize() >= 1
			&& currentPlayer.playerLaidStage())
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
			&& PileIsSet(piles.get(0), 3)
			&& PileIsSet(piles.get(1), 3));
	}

	/**
	 * @author Björn Merschmeier
	 * @param piles
	 * @return
	 */
	private boolean areCardsReadyForPhase2(ArrayList<Pile> piles) {
		return (piles.size() == 2
			&& (
					(PileIsSet(piles.get(0), 3)
							&& PileIsSequence(piles.get(1), 4))
					|| (PileIsSet(piles.get(1), 3)
							&& PileIsSequence(piles.get(0), 4))
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
						(PileIsSet(piles.get(0), 4)
								&& PileIsSequence(piles.get(1), 4))
						|| (PileIsSet(piles.get(1), 4)
								&& PileIsSequence(piles.get(0), 4))
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
				&& PileIsSequence(piles.get(0), 7)
			);
	}

	/**
	 * @author Björn Merschmeier
	 * @param piles
	 * @return
	 */
	private boolean areCardsReadyForPhase5(ArrayList<Pile> piles) {
		return (piles.size() == 1
				&& PileIsSequence(piles.get(0), 8)
			);
	}

	/**
	 * @author Björn Merschmeier
	 * @param piles
	 * @return
	 */
	private boolean areCardsReadyForPhase6(ArrayList<Pile> piles) {
		return (piles.size() == 1
				&& PileIsSequence(piles.get(0), 9)
			);
	}

	/**
	 * @author Björn Merschmeier
	 * @param piles
	 * @return
	 */
	private boolean areCardsReadyForPhase7(ArrayList<Pile> piles) {
		return (piles.size() == 2
				&& PileIsSet(piles.get(0), 4)
				&& PileIsSet(piles.get(1), 4));
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
						(PileIsSet(piles.get(0), 2)
								&& PileIsSet(piles.get(1), 5))
						|| (PileIsSet(piles.get(1), 2)
								&& PileIsSet(piles.get(0), 5))
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
						(PileIsSet(piles.get(0), 3)
								&& PileIsSet(piles.get(1), 5))
						|| (PileIsSet(piles.get(1), 3)
								&& PileIsSet(piles.get(0), 5))
				)
			);
	}

	/**
	 * @author Björn Merschmeier
	 * @param pile
	 * @param i
	 * @return
	 */
	private boolean PileIsSet(Pile pile, int i) {
		return (pile instanceof SetDockPile && pile.getSize() == i);
	}
	

	/**
	 * @author Björn Merschmeier
	 * @param pile
	 * @param i
	 * @return
	 */
	private boolean PileIsSequence(Pile pile, int i) {
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
