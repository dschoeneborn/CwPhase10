package de.fh_dortmund.inf.cw.phaseten.gui.playground.player.lay_phase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.player.PlayerCardsPane;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;

/**
 * @author Robin Harbecke
 *
 */
public class LayPhaseRowPane extends JPanel{
	private static final long serialVersionUID = -1543713977507708492L;

	protected LayPhaseCardsWrapperPane leftLayPhaseCardsWrapperPane;
	protected LayPhaseCardsWrapperPane rightLayPhaseCardsWrapperPane;
	private LayPhaseCardsWrapperPane[] layPhaseCardsWrapperPanes;
	protected JButton sendDataButton = new JButton("Lay phase cards");

	protected ServiceHandler serviceHandler;
	PlayerCardsPane playerCardsPane;

	public LayPhaseRowPane(ServiceHandler serviceHandler,PlayerCardsPane playerCardsPane) {
		this.serviceHandler = serviceHandler;
		this.playerCardsPane = playerCardsPane;
		clearPiles();
		this.layPhaseCardsWrapperPanes = new LayPhaseCardsWrapperPane[]{this.leftLayPhaseCardsWrapperPane,this.rightLayPhaseCardsWrapperPane};
		this.sendDataButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LayPhaseRowPane.this.layCurrentPhaseCards();
			}
		});
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.add(this.leftLayPhaseCardsWrapperPane);
		this.add(this.rightLayPhaseCardsWrapperPane);
		this.add(sendDataButton);
	}

	protected void layCurrentPhaseCards() {
		Collection<DockPile> cardPiles = new ArrayList<>();
		for (LayPhaseCardsWrapperPane wrapper : this.layPhaseCardsWrapperPanes) {
			DockPile newPile = wrapper.getCurrentPile();
			if(newPile != null) {
				cardPiles.add(newPile);
			}
		}
		try {
			this.serviceHandler.layPhaseToTable(cardPiles);
			this.clearPiles();
			this.playerCardsPane.updateData();
		} catch (MoveNotValidException e) {
			System.out.println("Move not valide");
		} catch (NotLoggedInException e) {
			System.out.println("Not logged in");
		} catch (GameNotInitializedException e) {
			System.out.println("Game not initialized");
		}
		this.revalidate();
		this.repaint();
	}

	public void updateData(Collection<Card> allCards) {

		List<Class<? extends DockPile>> dockPileTypes;
		try {
			dockPileTypes = new ArrayList<>(serviceHandler.getDockPileTypesForPlayer());
			this.leftLayPhaseCardsWrapperPane.updateData(allCards, dockPileTypes.get(0));

			if(dockPileTypes.size() > 1)
			{
				this.rightLayPhaseCardsWrapperPane.updateData(allCards, dockPileTypes.get(1));
			}
		}
		catch (NotLoggedInException e)
		{
			System.out.println("User is not logged in!");
		}

		this.revalidate();
		this.repaint();
	}

	public List<Card> getCardsOnTempPile() {
		List<Card> result = new ArrayList<>();
		result.addAll(this.leftLayPhaseCardsWrapperPane.getCardsOnTempPile());
		result.addAll(this.rightLayPhaseCardsWrapperPane.getCardsOnTempPile());
		return result;
	}

	public boolean removeCardFromTempPile(Card card) {
		if(this.leftLayPhaseCardsWrapperPane.removeCardFromTempPile(card))return true;
		if(this.rightLayPhaseCardsWrapperPane.removeCardFromTempPile(card))return true;
		return false;
	}

	private void clearPiles()
	{
		List<Class<? extends DockPile>> dockPileTypes;
		try {
			dockPileTypes = new ArrayList<>(serviceHandler.getDockPileTypesForPlayer());
			this.leftLayPhaseCardsWrapperPane = new LayPhaseCardsWrapperPane(this.serviceHandler, this.playerCardsPane, dockPileTypes.get(0));

			if(dockPileTypes.size() > 1)
			{
				this.rightLayPhaseCardsWrapperPane = new LayPhaseCardsWrapperPane(this.serviceHandler, this.playerCardsPane, dockPileTypes.get(1));
			}
		}
		catch (NotLoggedInException e)
		{
			System.out.println("User is not logged in!");
		}
	}
}
