package de.fh_dortmund.inf.cw.phaseten.gui.playground.lay_phase;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.DockPilePane;

/**
 * @author Robin Harbecke
 *
 */
public class LayPhaseCardsRowPane extends JPanel{	
	private static final long serialVersionUID = 5268736642704635817L;
	
	protected DockPilePane leftPile;
	protected DockPilePane rightPile;
	
	public LayPhaseCardsRowPane(ServiceHandler serviceHandler) {
		this.leftPile = new TemporaryDockPilePane(serviceHandler);		
		this.rightPile = new TemporaryDockPilePane(serviceHandler);
		this.setLayout(new BorderLayout());
		this.add(this.leftPile);
		this.add(this.rightPile);
	}
}
