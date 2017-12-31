package de.fh_dortmund.inf.cw.phaseten.server.shared;

import java.util.ArrayList;

import javax.ejb.Local;
import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Pile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;

/**
 * 
 * @author Björn Merschmeier
 *
 */
@Local
public interface GameValidationLocal extends GameValidation
{
}
