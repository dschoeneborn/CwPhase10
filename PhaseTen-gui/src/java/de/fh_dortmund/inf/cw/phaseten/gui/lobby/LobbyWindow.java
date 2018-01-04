package de.fh_dortmund.inf.cw.phaseten.gui.lobby;

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
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;

/**
 * @author Marc Mettke
 * @author Sven Krefeld
 * @author Björn Merschmeier
 */
public class LobbyWindow extends GuiWindow implements ActionListener, GuiObserver {
	private static final long serialVersionUID = -3411026015858719190L;
	
	public static final String ACTIONCOMMAND_SPECTATE = "spectate";
	public static final String ACTIONCOMMAND_PLAY = "play";
	public static final String ACTIONCOMMAND_DISCONNECT = "disconnect";
	public static final String ACTIONCOMMAND_START = "start";
	
	public static final String WINDOW_NAME = "Phaseten | Lobby";
	public static final String SPECTATE = "Zuschauen";
	public static final String JOIN = "Beitreten";
	public static final String DISCONNECT = "Verlassen";
	public static final String START = "Starten";

	private ServiceHandler serviceHandler;

	protected ButtonPane buttonPane = new ButtonPane();
	protected UserList userList = new UserList();
	protected StatusPanel statusPanel = new StatusPanel();
	private JButton spectatorButton;
	private JButton startGameButton;

	public LobbyWindow(ServiceHandler serviceHandler, GuiManager guiManager) {
		super(WINDOW_NAME, serviceHandler, guiManager);
		this.serviceHandler = serviceHandler;
		this.setContentPane(this.setUI());
		this.setResizable(false);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void gameDataUpdated(Game game) {
		//TODO - BM - 04.01.2018 - was ist hier zu tun?
	}

	@Override
	public void currentPlayerDataUpdated(CurrentPlayer currentPlayer) {
		statusPanel.updateData(currentPlayer);
	}

	@Override
	public void lobbyDataUpdated(Lobby lobby) {
		userList.updateData(lobby.getPlayers(), lobby.getSpectators());
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case ACTIONCOMMAND_SPECTATE: joinAsSpectator(); break;
			case ACTIONCOMMAND_PLAY: joinAsPlayer(); break;
			case ACTIONCOMMAND_DISCONNECT: disconnectFromLobby(); break;
			case ACTIONCOMMAND_START: startGame(); break;
			default: break;
		}
	}

	/**
	 * @author Björn Merschmeier
	 */
	private void joinAsSpectator()
	{
		try
		{
			serviceHandler.enterLobbyAsSpectator();
			
			startGameButton.setText(DISCONNECT);
			startGameButton.setActionCommand(ACTIONCOMMAND_DISCONNECT);
		}
		catch (NotLoggedInException e1)
		{
			e1.printStackTrace();
			//TODO - BM - 04.01.2018 - Fehlermeldung abfangen und darstellen!
		}
		spectatorButton.setEnabled(false);
		startGameButton.setEnabled(false);
	}

	/**
	 * @author Björn Merschmeier
	 */
	private void joinAsPlayer()
	{
		try
		{
			serviceHandler.enterLobbyAsPlayer();
			spectatorButton.setEnabled(false);
			
			startGameButton.setText(START);
			startGameButton.setActionCommand(ACTIONCOMMAND_START);
		}
		catch (NoFreeSlotException exception)
		{
			//TODO - BM - 04.01.2018 - Fehlermeldung abfangen und darstellen!
			exception.printStackTrace();
			startGameButton.setEnabled(false);
		}
		catch (PlayerDoesNotExistsException e1)
		{
			//TODO - BM - 04.01.2018 - Fehlermeldung abfangen und darstellen!
			e1.printStackTrace();
		}
		catch (NotLoggedInException e2)
		{
			//TODO - BM - 04.01.2018 - Fehlermeldung abfangen und darstellen!
			e2.printStackTrace();
		}
	}

	/**
	 * @author Björn Merschmeier
	 */
	private void disconnectFromLobby()
	{
		try
		{
			serviceHandler.exitLobby();
		}
		catch (NotLoggedInException e)
		{
			//User is not logged in at this time
			this.getGuiManager().showLoginGui();
		}
		
		spectatorButton = new JButton(SPECTATE);
		spectatorButton.setActionCommand(ACTIONCOMMAND_SPECTATE);
		startGameButton = new JButton(JOIN);
		startGameButton.setActionCommand(ACTIONCOMMAND_PLAY);
	}
	
	/**
	 * @author Björn Merschmeier
	 */
	private void startGame()
	{
		try
		{
			serviceHandler.startGame();
		}
		catch (NotEnoughPlayerException exception)
		{
			//TODO - BM - 04.01.2018 - Fehlermeldung abfangen und darstellen!
			exception.printStackTrace();
		}
		catch (PlayerDoesNotExistsException e1)
		{
			//TODO - BM - 04.01.2018 - Fehlermeldung abfangen und darstellen!
			e1.printStackTrace();
		}
		catch (NotLoggedInException e2)
		{
			//User is not logged in at this time
			this.getGuiManager().showLoginGui();
		}
	}

	private Container setUI()
	{
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		spectatorButton = new JButton(SPECTATE);
		spectatorButton.setActionCommand(ACTIONCOMMAND_SPECTATE);
		spectatorButton.addActionListener(this);
		startGameButton = new JButton(JOIN);
		startGameButton.setActionCommand(ACTIONCOMMAND_PLAY);
		startGameButton.addActionListener(this);

		JLabel label = new JLabel("Some text");

		layout.setHorizontalGroup(layout.createSequentialGroup()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
					.addComponent(this.statusPanel)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
							.addComponent(spectatorButton)
							.addComponent(startGameButton)
							)
					.addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
							.addComponent(label)
							.addComponent(this.userList)
							)					
					)				
				);

		layout.linkSize(SwingConstants.HORIZONTAL, userList, statusPanel);

		layout.setVerticalGroup(layout.createSequentialGroup()
				.addComponent(this.userList)
				.addComponent(label)
				.addComponent(spectatorButton)
				.addComponent(startGameButton)
				.addComponent(this.statusPanel)
				);
		
		return panel;
	}
}
