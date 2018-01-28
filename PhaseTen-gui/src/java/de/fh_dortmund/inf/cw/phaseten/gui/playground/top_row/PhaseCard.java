package de.fh_dortmund.inf.cw.phaseten.gui.playground.top_row;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 * @author Sebastian Seitz
 * @author Sven Krefeld
 */
public class PhaseCard extends JPanel{
	private static final long serialVersionUID = -8351895741794896192L;
		

	public PhaseCard() {
		this.setPreferredSize(new Dimension(200,200));
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		this.setBackground(java.awt.Color.white);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
	}
			
	public void updatePhase(int newPhase){
		this.removeAll();
		JLabel headline = new JLabel("Phase 10");
		headline.setFont(new Font("DIALOG", Font.BOLD, 16));
		headline.setForeground(Color.BLUE.getRGBColor());
		this.add(headline);
		JLabel subHeadline = new JLabel("The Phases Are:");
		subHeadline.setFont(new Font("DIALOG", Font.BOLD, 14));
		subHeadline.setForeground(Color.BLUE.getRGBColor());
		this.add(subHeadline);
		for(int i=1; i<=10; i++){			
			JLabel label = new JLabel(getPhase(i), JLabel.LEFT);		
			if(i == newPhase){
				label.setForeground(Color.RED.getRGBColor());
			} else {
				label.setForeground(Color.BLUE.getRGBColor());
			}			
			this.add(label);
		}				
		this.revalidate();
		this.repaint();
	}
	
	private String getPhase(int phase)
	{
		switch(phase){
		
		case 1: return "1. 2 sets of 3";
		case 2: return "2. 1 set of 3 + 1 run of 4";
		case 3: return "3. 1 set of 4 + 1 run of 4";
		case 4: return "4. 1 run of 7";
		case 5: return "5. 1 run of 8";
		case 6: return "6. 1 run of 9";
		case 7: return "7. 2 sets of 4";
		case 8: return "8. 7 cards of one color";
		case 9: return "9. 1 set of 5 + 1 set of 2";
		case 10: return "10. 1 set of 5 + 1 set of 3";
		default: return "This phase does not exists"; // should not be reached
		}
	}
}