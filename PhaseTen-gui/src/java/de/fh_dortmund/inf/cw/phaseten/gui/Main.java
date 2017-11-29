package de.fh_dortmund.inf.cw.phaseten.gui;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandlerImpl;

public class Main {
	public static void main(String[] args) {
		ServiceHandler serviceHandler = ServiceHandlerImpl.getInstance();
		System.out.println(serviceHandler.helloWorld());
	}
}
