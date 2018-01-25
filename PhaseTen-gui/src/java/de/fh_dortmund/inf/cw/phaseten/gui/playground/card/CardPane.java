package de.fh_dortmund.inf.cw.phaseten.gui.playground.card;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color;

/**
 * @author Robin Harbecke
 * @author Sven Krefeld
 * @author Marc Mettke
 */
public class CardPane extends JPanel {
	private static final long serialVersionUID = 667877245670448219L;
	
	public static final Dimension cardSize = new Dimension(80, 130);
	
	private CardValue cardValue;
	private Color color;

	private Card baseCard;

	public CardPane() {
		this.init();
	}

	public CardPane(Card baseCard) {				
		this(baseCard.getCardValue(),CardPane.getColor(baseCard));
		this.baseCard = baseCard;
	}

	protected CardPane(CardValue cardValue, Color color) {
		this();
		this.cardValue = cardValue;
		this.color = color;
	}

	protected void init() {
		this.setPreferredSize(CardPane.cardSize);
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
	}

	public void paintComponent(java.awt.Graphics graphics) {
		if (this.cardValue == null) {
			// Rückseite
			super.paintComponent(graphics);

			int x = 0;
			int y = 0;

			java.awt.Graphics2D gr = (java.awt.Graphics2D) graphics;

			int cardWidth = this.getWidth(), cardHeight = this.getHeight();
			gr.setColor(Color.RED.getRGBColor());
			gr.fillRect(x, y, cardWidth, cardHeight);
			gr.setColor(java.awt.Color.BLACK);
			gr.drawRect(x, y, cardWidth - 1, cardHeight - 1);
		} else {
			// Vorderseite
			super.paintComponent(graphics);

			int x = 0;
			int y = 0;

			java.awt.Graphics2D gr = (java.awt.Graphics2D) graphics;

			int cardWidth = this.getWidth(), cardHeight = this.getHeight();
			gr.setColor(java.awt.Color.WHITE);
			gr.fillRect(x, y, cardWidth, cardHeight);
			gr.setColor(java.awt.Color.BLACK);
			gr.drawRect(x, y, cardWidth - 1, cardHeight - 1);
			Font font = new Font("Georgia", Font.BOLD, 20);
			gr.setFont(font);
			gr.setColor(color.getRGBColor());

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

	public Card getBaseCard() {
		return this.baseCard;
	}
	
	private static Color getColor(Card card) {
		if(card.getColor() == de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color.BLUE) return Color.BLUE;
		if(card.getColor() == de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color.RED) return Color.RED;
		if(card.getColor() == de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color.GREEN) return Color.GREEN;
		if(card.getColor() == de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color.YELLOW) return Color.YELLOW;
		return Color.NONE;
		
	}
}