package de.fh_dortmund.inf.cw.phaseten.gui.elements;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.server.entities.User;

/**
 * @author Robin Harbecke
 */
public class StatusPanel extends JPanel{
	private static final long serialVersionUID = 9162651354589609526L;
	
	protected JLabel statusLabel;
	protected JLabel nameLabel;
	protected JLabel coinsLabel;
	
	public StatusPanel() {
		this.add(Box.createHorizontalGlue());
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
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

	public void updateData(User user)
	{
		this.statusLabel.setText("TODO - Status setzen");
		this.nameLabel.setText(user.getLoginName());
		this.coinsLabel.setText(user.getCoins() + "");
	}
}
