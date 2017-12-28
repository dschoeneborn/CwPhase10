package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * @author Robin Harbecke
 *
 */
public class PublicCardStackPane  extends JPanel{
	protected JScrollPane scrollPane;
	protected JPanel stackListPanel = new JPanel();
	
	public PublicCardStackPane() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.scrollPane = new JScrollPane(this.stackListPanel);
		this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.stackListPanel.setLayout(new FlowLayout());
		//this.setMaximumSize(new Dimension(500,200));
		this.add(this.scrollPane);		
	}
		
	public void updateData() {//todo
		this.stackListPanel.removeAll();		
		for (int i = 4; i > 0; i--) {//only for test data
			JPanel cardStack = new JPanel();			
			cardStack.setLayout(new FlowLayout());
			this.stackListPanel.add(cardStack);						
			for(int y=0;y<i;y++){
				CardPane card = new CardPane();
				cardStack.add(card);
			}
		}		
	}
}
