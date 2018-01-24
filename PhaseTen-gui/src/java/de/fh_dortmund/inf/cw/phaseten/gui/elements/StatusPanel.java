package de.fh_dortmund.inf.cw.phaseten.gui.elements;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;

/**
 * @author Robin Harbecke
 * @author Sven Krefeld
 */
public class StatusPanel extends JPanel {
	private static final long serialVersionUID = 9162651354589609526L;

	protected JLabel statusLabel;
	protected JLabel nameLabel;
	protected JLabel coinsLabel;

	public StatusPanel() {
		this.add(Box.createHorizontalGlue());
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		{
			this.statusLabel = new JLabel();
			this.add(this.statusLabel);
		}
		this.add(Box.createHorizontalGlue());
		{
			this.nameLabel = new JLabel();
			this.add(this.nameLabel);
		}
		this.add(Box.createHorizontalGlue());
		{
			this.coinsLabel = new JLabel();
			this.add(this.coinsLabel);
		}
		this.add(Box.createHorizontalGlue());
	}

	public void updateData(User user, PlayerGuiData currentPlayer) {
		if (currentPlayer == null) {
			this.statusLabel.setText("Game not started");
		} else {
			if (user.getLoginName().equals(currentPlayer.getName())) {
				switch (currentPlayer.getRoundStage()) {
				case 0:
					this.statusLabel.setText("You must draw a card");
					break;
				case 1:
					this.statusLabel.setText("You can lay out a phase and end your turn");
					break;
				case 2:
					this.statusLabel.setText("Your turn is finished");
					break;
				default:
					break;
				}
			} else {
				this.statusLabel.setText("It's player " + currentPlayer.getName() + "'s turn");
			}
		}
		this.nameLabel.setText(user.getLoginName());
		this.coinsLabel.setText(user.getCoins() + "Coins");
		this.revalidate();
		this.repaint();
	}
}
