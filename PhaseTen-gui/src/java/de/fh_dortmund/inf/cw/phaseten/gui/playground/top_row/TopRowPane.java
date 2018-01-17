package de.fh_dortmund.inf.cw.phaseten.gui.playground.top_row;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.user.UserList;
import de.fh_dortmund.inf.cw.phaseten.server.messages.GameGuiData;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class TopRowPane extends JPanel
{
	private static final long serialVersionUID = -6210706602718026386L;

	private DrawCardPilePane drawPile;
	private DiscardCardPile discardPile;

	private PhaseCard phaseCard = new PhaseCard();
	private UserList userList;

	private ServiceHandler serviceHandler;

	public TopRowPane(ServiceHandler serviceHandler)
	{
		this.serviceHandler = serviceHandler;
		this.userList = new UserList(this.serviceHandler);
		this.drawPile = new DrawCardPilePane(this.serviceHandler);
		this.discardPile =  new DiscardCardPile(this.serviceHandler);
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.add(topJustify(this.drawPile));
		this.add(Box.createHorizontalStrut(10));
		this.add(topJustify(this.discardPile));
		this.add(Box.createHorizontalGlue());
		this.add(topJustify(this.phaseCard));
		this.add(this.userList);
	}


	public void gameDataUpdated(GameGuiData game)
	{
		this.userList.updateData(game.getPlayers(), game.getSpectators(), game.getCurrentPlayer());
		this.discardPile.updateData(game.getLiFoStackTop());
		this.phaseCard.updatePhase(0);//TODO Get Phase
		this.revalidate();
		this.repaint();
	}

	private static Component topJustify( JPanel panel )  {
		Box  b = Box.createVerticalBox();
		b.add( panel );
		b.add( Box.createVerticalGlue() );
		b.setPreferredSize(panel.getPreferredSize());
		return b;
	}
}
