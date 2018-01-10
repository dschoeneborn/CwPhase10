package de.fh_dortmund.inf.cw.phaseten.gui;

import java.util.Observable;
import java.util.Observer;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.lobby.LobbyWindow;
import de.fh_dortmund.inf.cw.phaseten.gui.login.LoginWindow;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.PlaygroundWindow;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public class GuiManager implements Observer {
	protected GuiFrame currentGuiFrame; 
	protected ServiceHandler serviceHandler;

	public GuiManager(ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
		this.showLoginGui();
		this.serviceHandler.addObserver(this);
	}
	
	protected void closeCurrentGui() {
		if(this.currentGuiFrame != null)currentGuiFrame.setVisible(false);
		this.currentGuiFrame = null;
	}
	
	public void showPlaygoundGui() {
		this.closeCurrentGui();
		this.currentGuiFrame = new PlaygroundWindow(this.serviceHandler, this);
	}
	
	public void showLobbyGui() {
		this.closeCurrentGui();
		this.currentGuiFrame = new LobbyWindow(this.serviceHandler, this);
	}
	
	public void showLoginGui() {
		this.closeCurrentGui();
		this.currentGuiFrame = new LoginWindow(this.serviceHandler, this);	
	}

	@Override
	public void update(Observable observable, Object data)
	{
		if(this.currentGuiFrame != null 
			&& currentGuiFrame instanceof GuiObserver)
		{
			((GuiObserver)this.currentGuiFrame).updated(data);
		}
	}
	
	public void dispose()
	{
		try
		{
			this.serviceHandler.logout();
		}
		catch (NotLoggedInException e)
		{
			//Do nothing cause user is already logged out
		}
	}
}
