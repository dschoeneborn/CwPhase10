package de.fh_dortmund.inf.cw.phaseten.gui.playground.top_row;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.elements.UserList;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.LiFoStackPane;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.PhaseCard;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.GameGuiData;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class TopRowPane extends JPanel {
	private static final long serialVersionUID = -6210706602718026386L;

	private DrawCardPilePane drawPile;
	private LiFoStackPane discardPile;

	private PhaseCard phaseCard = new PhaseCard();
	private UserList userList = new UserList();

	private ServiceHandler serviceHandler;

	public TopRowPane(ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
		this.drawPile = new DrawCardPilePane(this.serviceHandler);
		this.discardPile = new LiFoStackPane(this.serviceHandler);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(topJustify(this.drawPile));
		this.add(Box.createHorizontalStrut(10));
		this.add(topJustify(this.discardPile));
		this.add(Box.createHorizontalGlue());
		this.add(topJustify(this.phaseCard));
		this.add(this.userList);
		this.setupListeners();
	}

	public void gameDataUpdated(GameGuiData game) {
		this.userList.updateData(game.getPlayers(), game.getSpectators());
		this.discardPile.updateData(game.getLiFoStackTop());
	}

	private void setupListeners() {
		this.drawPile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					TopRowPane.this.serviceHandler.takeCardFromPullstack();
				}
				catch (MoveNotValidException e1) {
					// TODO - BM - 04.01.2018 - Exception abfangen und ausgeben
					e1.printStackTrace();
				}
				catch (NotLoggedInException e1) {
					// TODO - BM - 04.01.2018 - Exception abfangen und ausgeben
					e1.printStackTrace();
				}
				catch (GameNotInitializedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		this.discardPile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					TopRowPane.this.serviceHandler.takeCardFromLiFoStack();
				}
				catch (MoveNotValidException e1) {
					// TODO - BM - 04.01.2018 - Exception abfangen und ausgeben
					e1.printStackTrace();
				}
				catch (NotLoggedInException e1) {
					// TODO - BM - 04.01.2018 - Exception abfangen und ausgeben
					e1.printStackTrace();
				}
				catch (GameNotInitializedException e1) {
					// TODO - BM - 04.01.2018 - Exception abfangen und ausgeben
					e1.printStackTrace();
				}
			}
		});
	}

	private static Component topJustify(JPanel panel) {
		Box b = Box.createVerticalBox();
		b.add(panel);
		b.add(Box.createVerticalGlue());
		b.setPreferredSize(panel.getPreferredSize());
		return b;
	}
}
