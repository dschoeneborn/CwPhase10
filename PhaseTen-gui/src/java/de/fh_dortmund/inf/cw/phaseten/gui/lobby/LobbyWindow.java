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
import de.fh_dortmund.inf.cw.phaseten.gui.GuiFrame;
import de.fh_dortmund.inf.cw.phaseten.gui.elements.StatusPanel;
import de.fh_dortmund.inf.cw.phaseten.gui.elements.UserList;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;

/**
 * @author Marc Mettke
 * @author Sven Krefeld
 */
public class LobbyWindow extends GuiFrame {
	private static final long serialVersionUID = -3411026015858719190L;

	private ServiceHandler serviceHandler;

	protected ButtonPane buttonPane = new ButtonPane();
	protected UserList userList = new UserList();
	protected StatusPanel statusPanel = new StatusPanel();

	public LobbyWindow(ServiceHandler serviceHandler) {
		super("Phaseten | Lobby", serviceHandler);
		this.serviceHandler = serviceHandler;
		this.setContentPane(this.setUI());
		this.setResizable(false);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private Container setUI() {
		JPanel panel = new JPanel();
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
		final JButton spectatorButton = new JButton("Zuschauen");
		final JButton startGameButton = new JButton("Beitreten");
		
		spectatorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serviceHandler.enterAsSpectator();
				spectatorButton.setEnabled(false);
				startGameButton.setEnabled(false);
			}
		});

		startGameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					serviceHandler.enterAsPlayer();
					spectatorButton.setEnabled(false);
					
					startGameButton.setText("Starten");
					startGameButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							try {
								serviceHandler.startGame();
							} catch (NotEnoughPlayerException exception) {
								exception.printStackTrace();
							}
						}
			    	});
				} catch (NoFreeSlotException exception) {
					exception.printStackTrace();
					startGameButton.setEnabled(false);
				}
			}
		});

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

	@Override
	public void gameDataUpdated(Game game) {

	}

	@Override
	public void currentPlayerDataUpdated(CurrentPlayer currentPlayer) {
		statusPanel.updateData(currentPlayer);
	}

	@Override
	public void lobbyDataUpdated(Lobby lobby) {
		userList.updateData(lobby.getPlayers(), lobby.getSpectator());
	}
}
