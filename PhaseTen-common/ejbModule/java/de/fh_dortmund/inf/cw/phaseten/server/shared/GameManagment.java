package de.fh_dortmund.inf.cw.phaseten.server.shared;

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
	
	void addToOpenPile() throws NotYourTurnException, CardCannotBeAddedException, PhaseNotCompletedException;
	
	void goOut() throws NotYourTurnException, InvalidCardCompilationException;
	
	void discardCardToDiscardPile() throws NotYourTurnException, TakeCardBeforeDiscardingException;
}
