package fi.ankkala.bunnyrace;

public interface GameControl {

	public abstract void levelComplete();
	public abstract void goToLevelSelection();
	public abstract void toggleCredits();
	public abstract void loadMap(String mapname);
}