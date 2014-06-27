package fi.ankkala.bunnyrace.game;

import com.badlogic.gdx.math.Vector2;

public interface GameEventObserver {

	public abstract void herne(Vector2 sijainti);

	public abstract void mansikka(Vector2 sijainti);

	public abstract void pommi(Vector2 sijainti);
	
	public abstract void levelComplete();
	
	public abstract void levelFailed();

}