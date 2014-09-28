package fi.ankkala.bunnyrace;

import fi.ankkala.bunnyrace.game.GameResult;

public interface GameControl {

	public abstract void levelComplete(GameResult gr);
	public abstract void goToLevelSelection();
	public abstract void toggleCredits();
	public abstract void loadMap(String mapname);
}