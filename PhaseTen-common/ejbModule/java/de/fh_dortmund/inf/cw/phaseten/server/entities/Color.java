/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

/**
 * @author Dennis Schöneborn
 * @author Sven Krefeld
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

	public java.awt.Color getRGBColor() {
		switch (value) {
		case 1:
			return new java.awt.Color(227, 24, 54);
		case 2:
			return new java.awt.Color(0, 83, 155);
		case 3:
			return new java.awt.Color(253, 184, 19);
		case 4:
			return new java.awt.Color(0, 132, 62);
		default:
			return new java.awt.Color(0, 0, 0);
		}
	}
}
