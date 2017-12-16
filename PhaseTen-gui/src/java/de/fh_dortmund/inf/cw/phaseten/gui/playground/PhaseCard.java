package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

/**
 * @author Robin Harbecke
 *
 */
public class PhaseCard extends JPanel{
	public PhaseCard() {				
		this.setPreferredSize(new Dimension(200,150));
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		this.setBackground(Color.green);
	}
}
