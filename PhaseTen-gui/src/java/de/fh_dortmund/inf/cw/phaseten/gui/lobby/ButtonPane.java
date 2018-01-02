package de.fh_dortmund.inf.cw.phaseten.gui.lobby;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * @author Sven Krefeld
 */
public class ButtonPane extends JPanel {

	private static final long serialVersionUID = 7349598820493864575L;
	
	protected JButton spectatorButton;
	protected JButton startButton;
	
	public ButtonPane() {		
		spectatorButton = new JButton("Zuschauen");
		spectatorButton.setHorizontalAlignment(SwingConstants.CENTER);
		spectatorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(spectatorButton);

		startButton = new JButton("Beitreten");
		spectatorButton.setHorizontalAlignment(SwingConstants.CENTER);
		startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(startButton);
	}

}
