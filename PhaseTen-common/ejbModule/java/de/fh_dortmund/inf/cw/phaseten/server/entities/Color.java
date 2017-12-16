/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

/**
 * @author Dennis Schöneborn
 *
 */
public enum Color {
	NONE(0), RED(1), BLUE(2), YELLOW(3), GREEN(4);

	private int value;

	private Color(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static Color getColor(int value) {
		Color chatMessageType = null;

		for (Color tempChatMessageType : values()) {
			if (tempChatMessageType.getValue() == value) {
				chatMessageType = tempChatMessageType;
			}
		}

		return chatMessageType;
	}
}
