package de.fh_dortmund.inf.cw.phaseten.gui.playground.card;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class PhaseCard extends JPanel{
	private static final long serialVersionUID = -8351895741794896192L;

	public PhaseCard() {				
		this.setPreferredSize(new Dimension(200,150));
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		this.setBackground(Color.green);
	}
}
