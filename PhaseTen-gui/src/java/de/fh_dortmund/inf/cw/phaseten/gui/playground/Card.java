package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class Card extends JPanel{
	public Card() {				
		this.setPreferredSize(new Dimension(80,130));
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		this.setBackground(Color.red);
	}
}
