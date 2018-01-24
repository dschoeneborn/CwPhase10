package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.LinkedList;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/***
 * Filtert beim Registrieren und/oder einloggen Schimpfwörter heraus.**
 *
 * @author Daniela Kaiser
 */
public class FilterUsermanagement {
	private LinkedList<String> insults;

	@AroundInvoke
	public Object filter(InvocationContext ctx) throws Exception {
		insults = new LinkedList<>();
		insults.add("schimpfwort");
		insults.add("fieses schimpfwort");
		insults.add("ganz fieses schimpfwort");

		Object[] params = ctx.getParameters();
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof String) {
					params[i] = format((String) params[i]);
				}
			}
			ctx.setParameters(params);
		}
		return ctx.proceed();
	}

	/**
	 * filtert Schimpfwörter.
	 *
	 * @param text
	 * @return text
	 */
	private String format(String text) {
		int start;
		int end;
		String textBefore;
		String textAfter;

		String caseInsensitiveText = text.toLowerCase();

		for (String insult : insults) {
			while (caseInsensitiveText.contains(insult)) {

				start = caseInsensitiveText.indexOf(insult);
				end = start + insult.length();

				textBefore = text.substring(0, start);
				textAfter = text.substring(end, text.length());

				text = textBefore + "XXX" + textAfter;
				caseInsensitiveText = text.toLowerCase();
			}
		}
		return text;
	}

}
