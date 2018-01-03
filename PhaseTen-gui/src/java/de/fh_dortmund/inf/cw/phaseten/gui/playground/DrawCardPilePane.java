package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.CardAlreadyTakenInThisTurnException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotYourTurnException;

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
			public void mouseClicked(MouseEvent e) { 
				super.mouseClicked(e);
				try {
					DrawCardPilePane.this.serviceHandler.takeCardFromDrawPile();
				} catch (NotYourTurnException e1) { 					
				} catch (CardAlreadyTakenInThisTurnException e1) {
				}
			}
		});		
	}

}
