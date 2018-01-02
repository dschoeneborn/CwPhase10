package de.fh_dortmund.inf.cw.phaseten.gui.lobby;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.GuiFrame;
import de.fh_dortmund.inf.cw.phaseten.gui.SpringUtilities;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;

/**
 * @author Marc Mettke
 */
public class LobbyWindow extends GuiFrame {
	private static final long serialVersionUID = -3411026015858719190L;
	
	private ServiceHandler serviceHandler;

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
        JPanel panel = new JPanel(new SpringLayout());
        
        JButton startGame = new JButton("Login");
        startGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					serviceHandler.startGame();
				} catch (NotEnoughPlayerException exception) {
					exception.printStackTrace();
				}
			}
    	});
        
        panel.add(new JLabel("Lobby Stub"));
        panel.add(startGame);
 
        SpringUtilities.makeCompactGrid(panel,
                1, 2,
                6, 6,       
                6, 6);  
       return panel;	        
	}


	@Override
	public void gameDataUpdated(Game game) {
		
	}

	@Override
	public void currentPlayerDataUpdated(CurrentPlayer currentPlayer) {
		
	}

	@Override
	public void lobbyDataUpdated(Lobby lobby) {
		
	}
}
