package de.fh_dortmund.inf.cw.phaseten.gui.login;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.GuiFrame;
import de.fh_dortmund.inf.cw.phaseten.gui.SpringUtilities;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PasswordIncorrectException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;

/**
 * @author Marc Mettke
 */
public class LoginWindow extends GuiFrame {
	private static final long serialVersionUID = -3411026015858719190L;

	private ServiceHandler serviceHandler;
	private JTextField usernameLogin;
	private JTextField passwordLogin;
	private JTextField usernameRegister;
	private JTextField passwordRegister;
	private JTextField passwordWdhRegister;
	private JLabel statusLabel;

	public LoginWindow(ServiceHandler serviceHandler) {
		super("Phaseten | Login", serviceHandler);
		this.serviceHandler = serviceHandler;
		this.setContentPane(this.setUI());
		this.setResizable(false);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	
	private Container setUI() {
	        JPanel panel = new JPanel(new SpringLayout());
	        
	        JButton login = new JButton("Login");
	        login.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String username = usernameLogin.getText();
					String password = passwordLogin.getText();
					
					if(!username.isEmpty() && !password.isEmpty()) {
						try {
							serviceHandler.login(username, password);
						} catch (UserDoesNotExistException ignored) {
							statusLabel.setText("Username is not registered");
						} catch (PasswordIncorrectException ignored) {
							statusLabel.setText("Password is invalid for the given username");
						}
					}
				}
        	});
	        JButton register = new JButton("Register");
	        register.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String username = usernameRegister.getText();
					String password = passwordRegister.getText();
					String passwordWdh = passwordWdhRegister.getText();
					
					if(!username.isEmpty() && !password.isEmpty()) {
						if( password.equals(passwordWdh) ) {
							try {
								serviceHandler.register(username, password);
							} catch (UsernameAlreadyTakenException ignored) {
								statusLabel.setText("Username is already registered");
							}						
						} else {
							statusLabel.setText("Passwords do not match");
						}						
					}
					
				}
        	});
	        
	        usernameLogin = new JTextField("", 15);
	        passwordLogin = new JTextField("", 15);
	        usernameRegister = new JTextField("", 15);
	        passwordRegister = new JTextField("", 15);
	        passwordWdhRegister = new JTextField("", 15);
	        statusLabel = new JLabel();
	        
	        panel.add(new JLabel("Username: "));
	        panel.add(usernameLogin);
	        panel.add(new JLabel("Password: "));
	        panel.add(passwordLogin);
	        panel.add(new JLabel());
	        panel.add(login);
	        panel.add(new JLabel("Username: "));
	        panel.add(usernameRegister);
	        panel.add(new JLabel("Password: "));
	        panel.add(passwordRegister);
	        panel.add(new JLabel("Password Wdh: "));
	        panel.add(passwordWdhRegister);
	        panel.add(new JLabel());
	        panel.add(register);
	        panel.add(new JLabel());
	        panel.add(statusLabel);
	 
	        SpringUtilities.makeCompactGrid(panel,
	                8, 2,
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
