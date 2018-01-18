package de.fh_dortmund.inf.cw.phaseten.server.entities;

/**
 * @author Björn Merschmeier
 */
public enum RoundStage {
	PULL(0), PUT_AND_PUSH(1), FINISHED(2);

	private int value;

	private RoundStage(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static RoundStage getRoundStageValue(int value) {
		RoundStage roundStage = null;

		for (RoundStage tempRoundStage : values()) {
			if (tempRoundStage.getValue() == value) {
				roundStage = tempRoundStage;
			}
		}

		return roundStage;
	}
}
