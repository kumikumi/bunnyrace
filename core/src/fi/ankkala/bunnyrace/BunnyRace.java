package fi.ankkala.bunnyrace;

import com.badlogic.gdx.ApplicationListener;
import fi.ankkala.bunnyrace.gui.Piirrettava;

public class BunnyRace implements ApplicationListener, Piirtaja {
	private Piirrettava piirrettava;
	
	@Override
	public void setPiirrettava(Piirrettava p) {
		this.piirrettava = p;
	}
	
	@Override
	public Piirrettava getPiirrettava() {
		return this.piirrettava;
	}

	@Override
	public void create() {
		new SovellusOhjain(this);
	}

	@Override
	public void dispose() {

	}

	@Override
	public void render() {
		this.piirrettava.piirra();
	}

	@Override
	public void resize(int width, int height) {
		try{
		this.piirrettava.resize(width, height);
		} catch (Exception e) {
			System.err.println("Koon muuttaminen kokoon (" + width + ", " + height + ") ei onnistunut");
			e.printStackTrace();
		}
	}

	@Override
	public void pause() {
		System.out.println("PAUSE");
	}

	@Override
	public void resume() {
		System.out.println("RESUME");

	}
}
