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
import de.fh_dortmund.inf.cw.phaseten.gui.GuiManager;
import de.fh_dortmund.inf.cw.phaseten.gui.GuiWindow;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public class LoginWindow extends GuiWindow implements ActionListener {
	
	private static final String ACTIONCOMMAND_LOGIN = "login";
	private static final String ACTIONCOMMAND_REGISTER = "register";
	
	private static final String SUCCESSFULLY_REGISTERED = "Successfully registered";
	private static final String USERNAME_TAKEN = "Username is already taken";
	private static final String PASSWORD_NOT_MATCH = "Passwords do not match";
	private static final String FILL_USERNAME_PASSWORD = "Please fill out username and password fields";
	private static final String LOGINWINDOW_NAME = "Phaseten | Login";
	private static final String LOGIN = "Login";
	private static final String REGISTER = "Register";
	private static final String USERNAME = "Username";
	private static final String PASSWORD = "Password";
	private static final String REPEAT_PASSWORD = "Repeat Password";
	private static final String LOGIN_INVALID = "Username-password-combination is wrong";
	
	private static final long serialVersionUID = -3411026015858719190L;

	private ServiceHandler serviceHandler;
	private JTextField usernameLogin;
	private JTextField passwordLogin;
	private JTextField usernameRegister;
	private JTextField passwordRegister;
	private JTextField passwordWdhRegister;
	private JLabel statusLabel;

	public LoginWindow(ServiceHandler serviceHandler, GuiManager guiManager) {
		super(LOGINWINDOW_NAME, serviceHandler, guiManager);
		this.serviceHandler = serviceHandler;
		this.setContentPane(this.setUI());
		this.setResizable(false);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		switch(e.getActionCommand())
		{
			case ACTIONCOMMAND_LOGIN: login(); break;
			case ACTIONCOMMAND_REGISTER: register(); break;
			default: break;
		}
	}
	
	private Container setUI() {
	        JPanel panel = new JPanel(new SpringLayout());
	        
	        JButton login = new JButton(LOGIN);
	        login.setActionCommand(ACTIONCOMMAND_LOGIN);
	        login.addActionListener(this);
	        
	        JButton register = new JButton(REGISTER);
	        register.setActionCommand(ACTIONCOMMAND_REGISTER);
	        register.addActionListener(this);
	        
	        usernameLogin = new JTextField("", 15);
	        passwordLogin = new JTextField("", 15);
	        usernameRegister = new JTextField("", 15);
	        passwordRegister = new JTextField("", 15);
	        passwordWdhRegister = new JTextField("", 15);
	        statusLabel = new JLabel();
	        
	        panel.add(new JLabel(USERNAME + ": "));
	        panel.add(usernameLogin);
	        panel.add(new JLabel(PASSWORD + ": "));
	        panel.add(passwordLogin);
	        panel.add(new JLabel());
	        panel.add(login);
	        panel.add(new JLabel(USERNAME + ": "));
	        panel.add(usernameRegister);
	        panel.add(new JLabel(PASSWORD + ": "));
	        panel.add(passwordRegister);
	        panel.add(new JLabel(REPEAT_PASSWORD + ": "));
	        panel.add(passwordWdhRegister);
	        panel.add(new JLabel());
	        panel.add(register);
	        panel.add(new JLabel());
	        panel.add(statusLabel);
	 
	        makeCompactGrid(panel,
	                8, 2,
	                6, 6,       
	                6, 6);  
	       return panel;	        
	}
	
	/**
	 * @author Björn Merschmeier
	 */
	private void login()
	{
		String username = usernameLogin.getText();
		String password = passwordLogin.getText();
		
		if(!username.isEmpty() && !password.isEmpty())
		{
			try
			{
				serviceHandler.login(username, password);

				if(serviceHandler.playerIsInGame())
				{
					this.getGuiManager().showPlaygoundGui();
				}
				else
				{
					this.getGuiManager().showLobbyGui();
				}
			}
			catch (UserDoesNotExistException ignored)
			{
				statusLabel.setText(LOGIN_INVALID);
			}
			catch (NotLoggedInException e)
			{
				throw new RuntimeException("User is not logged in after successful login. This error should not happen!");
			}
		}
	}
	
	/**
	 * @author Björn Merschmeier
	 */
	private void register()
	{
		String username = usernameRegister.getText();
		String password = passwordRegister.getText();
		String passwordWdh = passwordWdhRegister.getText();
		
		if(!username.isEmpty() && !password.isEmpty())
		{
			if( password.equals(passwordWdh) ) {
				try
				{
					serviceHandler.register(username, password);
					statusLabel.setText(SUCCESSFULLY_REGISTERED);
					this.getGuiManager().showLobbyGui();
				}
				catch (UsernameAlreadyTakenException ignored)
				{
					statusLabel.setText(USERNAME_TAKEN);
				}					
			}
			else
			{
				statusLabel.setText(PASSWORD_NOT_MATCH);
			}						
		}
		else
		{
			statusLabel.setText(FILL_USERNAME_PASSWORD);
		}
	}
}
