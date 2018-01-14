package de.fh_dortmund.inf.cw.phaseten.gui.playground.top_row;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.DockPilePane;
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
		// this.setMaximumSize(new Dimension(500,200));
		this.add(this.scrollPane);
	}

	public void gameDataUpdated(GameGuiData game) {
		this.stackListPanel.removeAll();
		for (OpenPileGuiData pile : game.getOpenPiles()) {
			DockPilePane dockPilePane = new DockPilePane(this.serviceHandler, pile);
			this.stackListPanel.add(dockPilePane);
		}
	}
}
