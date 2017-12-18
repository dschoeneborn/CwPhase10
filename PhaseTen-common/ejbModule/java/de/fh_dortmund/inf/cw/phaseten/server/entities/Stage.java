/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

/**
 * @author 012789
 *
 */
public enum Stage {
	TWO_TRIPLES(1), TRIPLE_AND_SET_OF_FOUR(2), QUADRUPLE_AND_SET_OF_FOUR(3), SET_OF_SEVEN(4), SET_OF_EIGHT(5),
	SET_OF_NINE(6), TWO_QUADRUPLES(7), SEVEN_OF_ONE_COLOR(8), QUINTUPLE_AND_TWIN(9), QUINTUPLE_AND_TRIPLE(10);
	private int value;

	private Stage(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static Stage getStage(int value) {
		Stage chatMessageType = null;

		for (Stage tempChatMessageType : values()) {
			if (tempChatMessageType.getValue() == value) {
				chatMessageType = tempChatMessageType;
			}
		}

		return chatMessageType;
	}
}
