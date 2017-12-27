/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.ejb.Stateful;

import de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote;

/**
 * @author Dennis Schöneborn
 *
 */

@Stateful
public class UserSessionBean implements UserSessionRemote, UserSessionLocal {

}
