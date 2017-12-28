/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

/**
 * @author Dennis Schöneborn
 * @author Sven Krefeld
 *
 */
public enum CardValue {
	ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7),
	EIGHT(8), NINE(9), TEN(10), ELEVEN(11), TWELVE(12), SKIP(15), WILD(20);
	
	private int value;

	private CardValue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public String getWrittenValue() {
		if (value == 15) {
			return "Skip";
		} else if (value == 20) {
			return "Wild";
		} else {
			return Integer.toString(value);
		}
	}

	public String getShorthandValue() {
		if (value == 15) {
			return "S";
		} else if (value == 20) {
			return "W";
		} else {
			return Integer.toString(value);
		}
	}

	public static CardValue getCardValue(int value) {
		CardValue chatMessageType = null;

		for (CardValue tempChatMessageType : values()) {
			if (tempChatMessageType.getValue() == value) {
				chatMessageType = tempChatMessageType;
			}
		}

		return chatMessageType;
	}
}
