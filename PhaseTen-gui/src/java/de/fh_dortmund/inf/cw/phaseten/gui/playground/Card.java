package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;

public class Card extends JPanel{

	private CardValue cardValue;
	private Color color;

	public Card() {				
		this.setPreferredSize(new Dimension(80,130));
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
	}

	public Card(CardValue cardValue, Color color) {
		this();
		this.cardValue = cardValue;
		this.color = color;			
	}

	public void paintComponent(java.awt.Graphics graphics) {
		if (this.cardValue == null) {
			// Rückseite
			super.paintComponent(graphics);

			int x = 0;
			int y = 0;

			java.awt.Graphics2D gr = (java.awt.Graphics2D) graphics;

			int cardWidth = this.getWidth(), cardHeight = this.getHeight();
			gr.setColor(Color.RED);
			gr.fillRect(x, y, cardWidth, cardHeight);
			gr.setColor(Color.BLACK);
			gr.drawRect(x, y, cardWidth - 1, cardHeight - 1);
		} else {
			// Vorderseite
			super.paintComponent(graphics);

			int x = 0;
			int y = 0;

			java.awt.Graphics2D gr = (java.awt.Graphics2D) graphics;

			int cardWidth = this.getWidth(), cardHeight = this.getHeight();
			gr.setColor(Color.WHITE);
			gr.fillRect(x, y, cardWidth, cardHeight);
			gr.setColor(Color.BLACK);
			gr.drawRect(x, y, cardWidth - 1, cardHeight - 1);
			Font font = new Font("Georgia", Font.BOLD, 20);
			gr.setFont(font);
			gr.setColor(color);

			gr.drawString(cardValue.getShorthandValue(), x + 6, y + 20);
			if (cardValue.getValue() == 10 || cardValue.getValue() == 11 || cardValue.getValue() == 12
					|| cardValue.getValue() == 20) {
				gr.drawString(cardValue.getShorthandValue(), x + 55, y + 122);
			} else if (cardValue.getValue() == 15) {
				gr.drawString(cardValue.getShorthandValue(), x + 60, y + 122);
			} else {
				gr.drawString(cardValue.getShorthandValue(), x + 60, y + 122);
			}

			if (cardValue.getValue() == 15 || cardValue.getValue() == 20) {
				font = new Font("Georgia", Font.BOLD, 22);
				gr.setFont(font);
				gr.drawString(cardValue.getWrittenValue(), x + 14, y + 72);
			} else if (cardValue.getValue() == 10 || cardValue.getValue() == 11 || cardValue.getValue() == 12) {
				font = new Font("Georgia", Font.BOLD, 52);
				gr.setFont(font);
				gr.drawString(cardValue.getWrittenValue(), x + 14, y + 80);
			} else {
				font = new Font("Georgia", Font.BOLD, 52);
				gr.setFont(font);
				gr.drawString(cardValue.getWrittenValue(), x + 24, y + 80);
			}
		}
	}
}