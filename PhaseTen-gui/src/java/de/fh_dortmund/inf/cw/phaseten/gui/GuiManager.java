package de.fh_dortmund.inf.cw.phaseten.gui;

import java.util.Observable;
import java.util.Observer;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.lobby.LobbyWindow;
import de.fh_dortmund.inf.cw.phaseten.gui.login.LoginWindow;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.PlaygroundWindow;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class GuiManager implements Observer {
	protected GuiFrame currentGuiFrame; 
	protected ServiceHandler serviceHandler;

	public GuiManager(ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
		this.showPlaygoundGui();
		this.serviceHandler.addObserver(this);
	}
	
	protected void closeCurrentGui() {
		if(this.currentGuiFrame != null)currentGuiFrame.setVisible(false);
		this.currentGuiFrame = null;
	}
	
	public void showPlaygoundGui() {
		this.closeCurrentGui();
		this.currentGuiFrame = new PlaygroundWindow(this.serviceHandler);
	}
	
	public void showLobbyGui() {
		this.closeCurrentGui();
		this.currentGuiFrame = new LobbyWindow(this.serviceHandler);
	}
	
	public void showLoginGui() {
		this.closeCurrentGui();
		this.currentGuiFrame = new LoginWindow(this.serviceHandler);	
	}

	@Override
	public void update(Observable observable, Object data) {
		if( data instanceof Game ) {
			if( this.currentGuiFrame instanceof LoginWindow ||
				this.currentGuiFrame instanceof LobbyWindow ) {
				this.showPlaygoundGui();
			}
			if( this.currentGuiFrame != null ) {
				this.currentGuiFrame.gameDataUpdated((Game) data);
			}		
		} else if( data instanceof Lobby ) {
			if( this.currentGuiFrame != null ) {
				this.currentGuiFrame.lobbyDataUpdated((Lobby) data);
			}			
		} else if( data instanceof CurrentPlayer ) {
			if( this.currentGuiFrame instanceof LoginWindow ) {
				this.showLobbyGui();
			}
			if( this.currentGuiFrame != null ) {
				this.currentGuiFrame.currentPlayerDataUpdated((CurrentPlayer) data);
			}
		}
	}	
}
