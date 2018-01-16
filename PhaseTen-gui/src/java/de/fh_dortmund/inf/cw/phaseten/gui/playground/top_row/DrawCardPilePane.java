package de.fh_dortmund.inf.cw.phaseten.gui.playground.top_row;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.CardPilePane;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;

/**
 * @author Robin Harbecke
 *
 */
public class DrawCardPilePane extends CardPilePane {	
	private static final long serialVersionUID = 6385631323288303771L;
	
	protected ServiceHandler serviceHandler;
	
	public DrawCardPilePane(ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) { 
				super.mouseClicked(evt);
				try {
					DrawCardPilePane.this.serviceHandler.takeCardFromPullstack();
				} catch (MoveNotValidException e) {
					System.out.println("Move not valide");				
				} catch (NotLoggedInException e) {
					System.out.println("Not logged in");
				} catch (GameNotInitializedException e) {
					System.out.println("Game not initialized");
				}
			}
		});		
	}

}
