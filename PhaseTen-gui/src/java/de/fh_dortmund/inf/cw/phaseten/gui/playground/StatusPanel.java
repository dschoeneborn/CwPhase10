package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Robin Harbecke
 *
 */
public class StatusPanel extends JPanel{
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
	
	public void updateData() {
		this.statusLabel.setText("Teststatus");
		this.nameLabel.setText("Name");
		this.coinsLabel.setText("Coins");
	}
}
