package de.fh_dortmund.inf.cw.phaseten.gui.playground.top_row;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 * @author Sebastian Seitz
 */
public class PhaseCard extends JPanel{
	private static final long serialVersionUID = -8351895741794896192L;
		

	public PhaseCard() {
		this.setPreferredSize(new Dimension(200,200));
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		this.setBackground(Color.green);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
	}
			
	public void updatePhase(int newPhase){
		this.removeAll();
		for(int i=1; i<=10; i++){			
			JLabel label = new JLabel(getPhase(i), JLabel.LEFT);		
			if(i == newPhase){
				label.setForeground(Color.red);
			} else {
				label.setForeground(Color.black);				
			}			
			this.add(label);
		}				
		this.revalidate();
		this.repaint();
	}
	
	private String getPhase(int phase)
	{
		switch(phase){
		
		case 1: return "1.   2 Drillinge";
		case 2: return "2.   1 Drilling + 1 Viererfolge";
		case 3: return "3.   1 Vierling + 1 Viererfolge";
		case 4: return "4.   1 Siebenerfolge";
		case 5: return "5.   1 Achterfolge";
		case 6: return "6.   1 Neunerfolge";
		case 7: return "7.   2 Vierlinge";
		case 8: return "8.   7 Karten einer Farbe";
		case 9: return "9.   1 Fünfling + 1 Zwilling";
		case 10: return "10. 1 Fünfling + 1 Drilling";
		default: return "Diese Phase existiert nicht"; // should not be reached
		}
	}
}