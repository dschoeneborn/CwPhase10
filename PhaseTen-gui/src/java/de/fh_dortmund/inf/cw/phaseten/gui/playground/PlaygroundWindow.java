package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.GuiFrame;
import de.fh_dortmund.inf.cw.phaseten.gui.elements.StatusPanel;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class PlaygroundWindow extends GuiFrame {
	private static final long serialVersionUID = -8685207683648562278L;
	
	protected TopRowPane topRowPane;
	protected PublicCardStackPane publicCardStackPane;
	protected PlayerCardsPane playerCardsPane = new PlayerCardsPane();
	protected StatusPanel statusPanel = new StatusPanel();
	
	public PlaygroundWindow(ServiceHandler serviceHandler) {
		super("Phaseten | Game",serviceHandler);
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
	}	

	@Override
	public void gameDataUpdated(Game game) {
		this.topRowPane.gameDataUpdated(game);
		this.publicCardStackPane.gameDataUpdated(game);
		this.pack();
	}

	@Override
	public void currentPlayerDataUpdated(CurrentPlayer currentPlayer) {
		this.playerCardsPane.updateData(currentPlayer.getPlayerPile());
		this.statusPanel.updateData(currentPlayer);
		this.pack();
	}

	@Override
	public void lobbyDataUpdated(Lobby lobby) {
		
	}
}
