package de.fh_dortmund.inf.cw.phaseten.client;

import java.util.Observable;

import javax.jms.MessageListener;

import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagmentRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.PlayerManagmentRemote;

/**
 * @author Marc Mettke
 */
public abstract class ServiceHandler extends Observable 
									 implements PlayerManagmentRemote, 
												LobbyManagmentRemote,
												GameManagmentRemote,
												MessageListener {
}
