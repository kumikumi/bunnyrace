package fi.ankkala.bunnyrace.gui;
import fi.ankkala.bunnyrace.game.Peli;

public class PelinPiirtaja implements Piirrettava {
	private Peli peli;
	private Piirrettava maailmanPiirtaja;
	private Piirrettava pauseScreen;
	private Piirrettava hud;
	private Piirrettava debugHUD;
	private Piirrettava fysiikka;

	public PelinPiirtaja(Peli peli, Piirrettava fysiikka,
			Piirrettava maailmanpiirtaja, Piirrettava hud,
			Piirrettava debugHUD) {
		this.peli = peli;
		this.fysiikka = fysiikka;
		this.pauseScreen = new PauseScreen();
		this.maailmanPiirtaja = maailmanpiirtaja;
		this.hud = hud;
		this.debugHUD = debugHUD;
	}

	@Override
	public void piirra() {
		if (this.peli == null) {
			return;
		}
		
		switch (peli.getGameState()) {
		case RUNNING:
			peli.etene();
			if (peli == null) {
				//peli.etene() kutsuttaessa tarkistetaan, onko peli loppunut joten voi olla, ettei peliä enää ole tämän jälkeen.
				return;
			}

			maailmanPiirtaja.piirra();
			hud.piirra();
			if (peli.getDebug()) {
				fysiikka.piirra();
				debugHUD.piirra();
			}

			break;
		case PAUSED:
			this.maailmanPiirtaja.piirra();
			this.pauseScreen.piirra();
			break;
		}
	}

	@Override
	public void resize(int w, int h) {
		if (this.maailmanPiirtaja != null) {
			this.maailmanPiirtaja.resize(w, h);
		}
		if (debugHUD != null) {
			debugHUD.resize(w, h);
		}
		if (hud != null) {
			hud.resize(w, h);
		}

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		this.debugHUD.destroy();
		this.debugHUD = null;
		//peli tuhoaa fysiikan
		//this.fysiikka.destroy();
		this.hud.destroy();
		this.hud = null;
		this.maailmanPiirtaja.destroy();
		this.maailmanPiirtaja = null;
		this.pauseScreen.destroy();
		this.pauseScreen = null;
		this.peli.destroy();
		this.peli = null;
	}

}
