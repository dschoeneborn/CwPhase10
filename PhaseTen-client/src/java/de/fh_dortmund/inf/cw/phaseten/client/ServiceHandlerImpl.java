package de.fh_dortmund.inf.cw.phaseten.client;

import javax.jms.JMSContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.fh_dortmund.inf.cw.phaseten.server.shared.StubRemote;

public class ServiceHandlerImpl implements ServiceHandler {
	private static ServiceHandlerImpl instance;
	
	private Context context;
	private StubRemote stubRemote;
	
	@SuppressWarnings("unused")
	private JMSContext jmsContext;
	
	private ServiceHandlerImpl() {
		try {
			context = new InitialContext();
			stubRemote = (StubRemote) context.lookup(
				"java:global/PhaseTen-ear/PhaseTen-ejb/ServerStub!de.fh_dortmund.inf.cw.phaseten.server.shared.StubRemote"
			);
			//ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("java:comp/DefaultJMSConnectionFactory");
			//jmsContext = connectionFactory.createContext();	
		} catch (NamingException ex) {
			System.err.println(ex.getMessage());
		}
	}
	
	public static ServiceHandlerImpl getInstance() {
		if(instance == null) {
			instance = new ServiceHandlerImpl();
		}
		return instance;
	}

	public String helloWorld() {
		return stubRemote.helloWorld();
	}
}
