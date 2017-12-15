package de.fh_dortmund.inf.cw.phaseten.server.shared;

import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.CardAlreadyTakenInThisTurnException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.CardCannotBeAddedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.InvalidCardCompilationException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotYourTurnException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PhaseNotCompletedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.TakeCardBeforeDiscardingException;

/**
 * @author Marc Mettke
 */
public interface GameManagment {
	void takeCardFromDrawPile() throws NotYourTurnException, CardAlreadyTakenInThisTurnException;
	
	void takeCardFromDiscardPile() throws NotYourTurnException, CardAlreadyTakenInThisTurnException;
	
	void addToOpenPile(Card card, DockPile dockPile) throws NotYourTurnException, CardCannotBeAddedException, PhaseNotCompletedException;
	
	void goOut(Collection<Card> cards) throws NotYourTurnException, InvalidCardCompilationException;
	
	void discardCardToDiscardPile(Card card) throws NotYourTurnException, TakeCardBeforeDiscardingException;
}
