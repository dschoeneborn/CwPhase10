/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.enumerations;

/**
 * Stage Enum
 * 
 * @author Dennis Schöneborn
 * @author Björn Merschmeier
 */
public enum Stage {
	TWO_TRIPLES(1), TRIPLE_AND_SEQUENCE_OF_FOUR(2), QUADRUPLE_AND_SEQUENCE_OF_FOUR(3), SEQUENCE_OF_SEVEN(
			4), SEQUENCE_OF_EIGHT(5), SEQUENCE_OF_NINE(6), TWO_QUADRUPLES(
					7), SEVEN_OF_ONE_COLOR(8), QUINTUPLE_AND_TWIN(9), QUINTUPLE_AND_TRIPLE(10), FINISHED(11);
	private int value;

	/**
	 * Konstruktor.
	 * 
	 * @param value
	 */
	private Stage(int value) {
		this.value = value;
	}

	/**
	 * Liefert Value
	 * 
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Liefert Stage
	 * 
	 * @param value
	 * @return chatMessageType
	 */
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
