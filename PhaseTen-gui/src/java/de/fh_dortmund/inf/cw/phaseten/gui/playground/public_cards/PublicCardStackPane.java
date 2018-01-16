package de.fh_dortmund.inf.cw.phaseten.gui.playground.public_cards;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.CardPane;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.OpenPileDataPane;
import de.fh_dortmund.inf.cw.phaseten.server.messages.GameGuiData;
import de.fh_dortmund.inf.cw.phaseten.server.messages.OpenPileGuiData;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 * @author Sven Krefeld
 */
public class PublicCardStackPane extends JPanel {
	private static final long serialVersionUID = -4152706728493122866L;

	protected JScrollPane scrollPane;
	protected JPanel stackListPanel = new JPanel();

	protected ServiceHandler serviceHandler;

	public PublicCardStackPane(ServiceHandler serviceHandler) {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.serviceHandler = serviceHandler;
		this.scrollPane = new JScrollPane(this.stackListPanel);
		this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		this.stackListPanel.setLayout(new FlowLayout()); 
		this.add(this.scrollPane);
		this.setPreferredSize(new Dimension(CardPane.cardSize.width*11+200, CardPane.cardSize.height+30));		
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
	}

	public void gameDataUpdated(GameGuiData game) {
		this.stackListPanel.removeAll();
		for (OpenPileGuiData pile : game.getOpenPiles()) {
			OpenPileDataPane dockPilePane = new OpenPileDataPane(this.serviceHandler, pile);
			this.stackListPanel.add(dockPilePane);
		}
		this.revalidate();
		this.repaint();
	}
}
