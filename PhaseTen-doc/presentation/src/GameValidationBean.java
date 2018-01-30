@Override
public boolean isValidLaySkipCard(Player currentPlayer, Player destinationPlayer, Game g) {
	boolean canLaySkipCard = false;

	Card skipCard = new Card(Color.NONE, CardValue.SKIP);

	if (g.isInitialized() && playerHasCard(skipCard, currentPlayer)
			&& currentPlayer.getRoundStage() == RoundStage.PUT_AND_PUSH && playerIsCurrentPlayer(g, currentPlayer)
			&& !currentPlayer.hasSkipCard() && !destinationPlayer.hasSkipCard() && !destinationPlayer.hasNoCards()
			&& currentPlayer.getPlayerPile().getCopyOfCardsList().size() > 1) {
		canLaySkipCard = true;
	}

	return canLaySkipCard;
}