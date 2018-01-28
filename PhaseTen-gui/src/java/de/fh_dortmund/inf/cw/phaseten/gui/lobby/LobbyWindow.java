package de.fh_dortmund.inf.cw.phaseten.gui.lobby;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.GuiManager;
import de.fh_dortmund.inf.cw.phaseten.gui.GuiObserver;
import de.fh_dortmund.inf.cw.phaseten.gui.GuiWindow;
import de.fh_dortmund.inf.cw.phaseten.gui.elements.StatusPanel;
import de.fh_dortmund.inf.cw.phaseten.gui.elements.UserList;
import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.InsufficientCoinSupplyException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayersException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.GameGuiData;

/**
 * @author Marc Mettke
 * @author Sven Krefeld
 * @author Björn Merschmeier
 */
public class LobbyWindow extends GuiWindow implements ActionListener, GuiObserver {
	private static final long serialVersionUID = -3411026015858719190L;

	private static final String ACTIONCOMMAND_ADD_AI = "addai";
	private static final String ACTIONCOMMAND_SPECTATE = "spectate";
	private static final String ACTIONCOMMAND_PLAY = "play";
	private static final String ACTIONCOMMAND_DISCONNECT = "disconnect";
	private static final String ACTIONCOMMAND_START = "start";

	private static final String WINDOW_NAME = "Phaseten | Lobby";
	private static final String ADD_AI = "Add AI";
	private static final String SPECTATE = "Spectate";
	private static final String JOIN = "Join Game";
	private static final String DISCONNECT = "Disconnect";
	private static final String START = "Start Game";

	private static final String NO_FREE_SLOT_AVAILABLE = "No free slot available";
	private static final String PLAYER_DOES_NOT_EXISTS = "Player does not exits";
	private static final String PLAYER_NOT_LOGGED_IN = "Player is not logged in";
	private static final String NOT_ENOUGH_PLAYER = "Not enough player to start a new game";
	private static final String NOT_ENOUGH_COINS = "Not enough coins to start game. 50 are required";

	private ServiceHandler serviceHandler;

	protected UserList userList;
	protected StatusPanel statusPanel = new StatusPanel();
	private JButton addAiButton;
	private JButton spectatorButton;
	private JButton startGameButton;
	private JLabel errorLabel = new JLabel();

	public LobbyWindow(ServiceHandler serviceHandler, GuiManager guiManager) {
		super(WINDOW_NAME, serviceHandler, guiManager);
		this.serviceHandler = serviceHandler;
		this.userList = new UserList();
		this.setContentPane(this.setUI());
		this.setResizable(false);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.updated(null);
	}

	/**
	 * @author Sven Krefeld
	 */
	private Container setUI() {
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		addAiButton = new JButton(ADD_AI);
		addAiButton.setActionCommand(ACTIONCOMMAND_ADD_AI);
		addAiButton.addActionListener(this);
		spectatorButton = new JButton(SPECTATE);
		spectatorButton.setActionCommand(ACTIONCOMMAND_SPECTATE);
		spectatorButton.addActionListener(this);
		startGameButton = new JButton(JOIN);
		startGameButton.setActionCommand(ACTIONCOMMAND_PLAY);
		startGameButton.addActionListener(this);

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER).addComponent(this.statusPanel)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addComponent(addAiButton)								
								.addComponent(spectatorButton)
								.addComponent(startGameButton).addComponent(errorLabel))
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
								.addComponent(this.userList))));

		layout.linkSize(SwingConstants.HORIZONTAL, userList, statusPanel);

		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(this.userList)
				.addComponent(addAiButton).addComponent(spectatorButton).addComponent(startGameButton).addComponent(errorLabel)
				.addComponent(this.statusPanel));

		return panel;
	}

	@Override
	public void updated(Object o) {
		if (o instanceof GameGuiData) {
			getGuiManager().showPlaygoundGui();
		}

		try {
			User u;
			u = serviceHandler.getUser();

			statusPanel.updateData(u, null);
		} catch (NotLoggedInException e) {
			new RuntimeException("Lobby is shown, but user is not logged in. This error should not happen");
		}

		userList.updateData(serviceHandler.getLobbyPlayers(), serviceHandler.getLobbySpectators());
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case ACTIONCOMMAND_SPECTATE:
			joinAsSpectator();
			break;
		case ACTIONCOMMAND_PLAY:
			joinAsPlayer();
			break;
		case ACTIONCOMMAND_DISCONNECT:
			disconnectFromLobby();
			break;
		case ACTIONCOMMAND_START:
			startGame();
			break;
		case ACTIONCOMMAND_ADD_AI:
			addAI();
		default:
			break;
		}
	}

	private void addAI() {
		try {
			serviceHandler.addAI();
		} catch (NoFreeSlotException exception) {
			errorLabel.setText(NO_FREE_SLOT_AVAILABLE);
			errorLabel.setForeground(Color.RED);
			startGameButton.setEnabled(false);
		}
	}

	/**
	 * @author Björn Merschmeier
	 * @author Sven Krefeld
	 */
	private void joinAsSpectator() {
		try {
			serviceHandler.enterLobbyAsSpectator();

			startGameButton.setText(DISCONNECT);
			startGameButton.setActionCommand(ACTIONCOMMAND_DISCONNECT);
		} catch (NotLoggedInException e1) {
			errorLabel.setText(PLAYER_NOT_LOGGED_IN);
			errorLabel.setForeground(Color.RED);
			this.getGuiManager().showLoginGui();
		}
		spectatorButton.setEnabled(false);
		startGameButton.setEnabled(false);
	}

	/**
	 * @author Björn Merschmeier
	 * @author Sven Krefeld
	 */
	private void joinAsPlayer() {
		try {
			serviceHandler.enterLobbyAsPlayer();
			spectatorButton.setEnabled(false);

			startGameButton.setText(START);
			startGameButton.setActionCommand(ACTIONCOMMAND_START);
		} catch (NoFreeSlotException exception) {
			errorLabel.setText(NO_FREE_SLOT_AVAILABLE);
			errorLabel.setForeground(Color.RED);
			startGameButton.setEnabled(false);
		} catch (PlayerDoesNotExistsException e1) {
			errorLabel.setText(PLAYER_DOES_NOT_EXISTS);
			errorLabel.setForeground(Color.RED);
			this.getGuiManager().showLoginGui();
		} catch (NotLoggedInException e2) {
			errorLabel.setText(PLAYER_NOT_LOGGED_IN);
			errorLabel.setForeground(Color.RED);
			this.getGuiManager().showLoginGui();
		} catch (InsufficientCoinSupplyException e) {
			errorLabel.setText(NOT_ENOUGH_COINS);
			errorLabel.setForeground(Color.RED);
		}
	}

	/**
	 * @author Björn Merschmeier
	 * @author Sven Krefeld
	 */
	private void disconnectFromLobby() {
		try {
			serviceHandler.exitLobby();
		} catch (NotLoggedInException e) {
			// User is not logged in at this time
			errorLabel.setText(PLAYER_NOT_LOGGED_IN);
			errorLabel.setForeground(Color.RED);
			this.getGuiManager().showLoginGui();
		}

		spectatorButton = new JButton(SPECTATE);
		spectatorButton.setActionCommand(ACTIONCOMMAND_SPECTATE);
		startGameButton = new JButton(JOIN);
		startGameButton.setActionCommand(ACTIONCOMMAND_PLAY);
	}

	/**
	 * @author Björn Merschmeier
	 * @author Sven Krefeld
	 */
	private void startGame() {
		try {
			serviceHandler.startGame();
		} catch (NotEnoughPlayersException exception) {
			errorLabel.setText(NOT_ENOUGH_PLAYER);
			errorLabel.setForeground(Color.RED);
		} catch (PlayerDoesNotExistsException e1) {
			errorLabel.setText(PLAYER_DOES_NOT_EXISTS);
			errorLabel.setForeground(Color.RED);
			this.getGuiManager().showLoginGui();
		} catch (NotLoggedInException e2) {
			errorLabel.setText(PLAYER_NOT_LOGGED_IN);
			errorLabel.setForeground(Color.RED);
			this.getGuiManager().showLoginGui();
		}
	}
}
