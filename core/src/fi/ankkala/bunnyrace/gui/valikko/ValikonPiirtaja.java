package fi.ankkala.bunnyrace.gui.valikko;

import fi.ankkala.bunnyrace.gui.Piirrettava;

public class ValikonPiirtaja implements Valikko{
	private Piirrettava tausta;
	private Valikko ylapalkki;
	private Valikko piirrettavaValikko;
	private boolean palautus;
	
	public ValikonPiirtaja(Valikko piirrettavaValikko, Piirrettava tausta, Valikko ylapalkki) {
		this.piirrettavaValikko = piirrettavaValikko;
		this.tausta = tausta;
		this.ylapalkki = ylapalkki;
	}

	@Override
	public void piirra() {
		// TODO Auto-generated method stub
		this.tausta.piirra();
		this.piirrettavaValikko.piirra();
		this.ylapalkki.piirra();
		
	}

	@Override
	public void resize(int w, int h) {
		// TODO Auto-generated method stub
		this.tausta.resize(w, h);
		this.piirrettavaValikko.resize(w, h);
		this.ylapalkki.resize(w, h);
	}

	@Override
	public void destroy() {
		this.piirrettavaValikko.destroy();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		palautus = this.ylapalkki.touchDown(screenX, screenY, pointer, button);
		if (!palautus) {
			this.piirrettavaValikko.touchDown(screenX, screenY, pointer, button);
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		palautus = this.ylapalkki.touchUp(screenX, screenY, pointer, button);
		if (!palautus) {
			this.piirrettavaValikko.touchUp(screenX, screenY, pointer, button);
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		palautus = this.ylapalkki.touchDragged(screenX, screenY, pointer);
		if (!palautus) {
			this.piirrettavaValikko.touchDragged(screenX, screenY, pointer);
		}
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		palautus = this.ylapalkki.scrolled(amount);
		if (!palautus) {
			this.piirrettavaValikko.scrolled(amount);
		}
		return true;
	}
}
