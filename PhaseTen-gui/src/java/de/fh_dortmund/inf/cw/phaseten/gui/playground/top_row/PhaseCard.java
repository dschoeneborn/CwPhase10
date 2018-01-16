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
	private int phase = 1;
	private JLabel[] phasen = new JLabel[10];
	

	public PhaseCard() {
		this.setPreferredSize(new Dimension(200,200));
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		this.setBackground(Color.green);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		for(int i=0; i<10; i++){
			phasen[i] = new JLabel(getPhase(i+1), JLabel.LEFT);
			
			if(i == (phase-1)){
				phasen[i].setForeground(Color.red);
			} else phasen[i].setForeground(Color.black);
			
			add(phasen[i]);
		}	
	}
	
	public void updatePhase(int phaseUpdate){
		phaseUpdate++; // ugly fix
		phasen[this.phase-1].setForeground(Color.black);
		this.phase = phaseUpdate;
		phasen[this.phase-1].setForeground(Color.red);
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
		default: return "Diese Phase existiert nicht"; // sollte nicht erreicht werden
		}
	}
}