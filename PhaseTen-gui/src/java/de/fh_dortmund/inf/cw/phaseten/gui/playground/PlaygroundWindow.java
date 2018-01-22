package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.GuiManager;
import de.fh_dortmund.inf.cw.phaseten.gui.GuiObserver;
import de.fh_dortmund.inf.cw.phaseten.gui.GuiWindow;
import de.fh_dortmund.inf.cw.phaseten.gui.elements.StatusPanel;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.player.PlayerCardsPane;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.public_cards.PublicCardStackPane;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.top_row.TopRowPane;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.GameGuiData;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public class PlaygroundWindow extends GuiWindow implements GuiObserver {
	private static final long serialVersionUID = -8685207683648562278L;

	protected TopRowPane topRowPane;
	protected PublicCardStackPane publicCardStackPane;
	protected PlayerCardsPane playerCardsPane;
	protected StatusPanel statusPanel = new StatusPanel();

	public PlaygroundWindow(ServiceHandler serviceHandler, GuiManager guiManager) {
		super("Phaseten | Game", serviceHandler, guiManager);
		this.playerCardsPane =  new PlayerCardsPane(serviceHandler);
		this.publicCardStackPane = new PublicCardStackPane(this.serviceHandler);
		this.topRowPane = new TopRowPane(this.serviceHandler);
		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.add(this.topRowPane);
		this.add(Box.createVerticalGlue());
		this.add(this.publicCardStackPane);
		this.add(this.playerCardsPane);
		this.add(this.statusPanel);
		this.setResizable(false);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		try {
			serviceHandler.requestGameMessage();
		}
		catch (PlayerDoesNotExistsException | NotLoggedInException | GameNotInitializedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updated(Object object) {
		if (object instanceof GameGuiData) {
			GameGuiData game = (GameGuiData) object;

			if(game.isFinished()) {
				getGuiManager().showLobbyGui();
			}

			this.topRowPane.gameDataUpdated(game);
			this.publicCardStackPane.gameDataUpdated(game);
			this.pack();
		}

		try {
			this.playerCardsPane.updateData(serviceHandler.getCards());
			this.statusPanel.updateData(serviceHandler.getUser());
			this.pack();
		}
		catch (NotLoggedInException e) {
			throw new RuntimeException(
					"User tried to get Cards while not logged in in Game-Screen. This error should not happen!");
		}
	}
}
