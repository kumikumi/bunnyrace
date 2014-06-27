package fi.ankkala.bunnyrace;

import fi.ankkala.bunnyrace.sound.GameMusic;
import fi.ankkala.bunnyrace.sound.SoundEngine;

public class SovellusOhjain implements GameControl {
	private Piirtaja piirtaja;
	private PiirrettavaTehdas tehdas;
	private SoundEngine soundengine;
	private boolean valikko;
	private boolean credits;
//	private Piirrettava edellinen;

	public SovellusOhjain(Piirtaja br) {
		this.piirtaja = br;
		this.soundengine = new SoundEngine();
		this.tehdas = new PiirrettavaTehdas(this, this.soundengine);
		this.goToSplashScreen();
		this.credits = false;
	}

	@Override
	public void levelComplete() {
		System.out.println("Onneksi olkoon, taso läpi!");
		this.goToLevelSelection();
	}

	@Override
	public void goToLevelSelection() {
		if (!valikko) {
			this.soundengine.playMusic(GameMusic.VALIKKO);
			valikko = true;
		}
		if (piirtaja.getPiirrettava() != null) {
			piirtaja.getPiirrettava().destroy();
		}
		piirtaja.setPiirrettava(tehdas.luoTasonValintaValikko());
	}

	@Override
	public void loadMap(String mapname) {
		valikko = false;
		if (piirtaja.getPiirrettava() != null) {
			piirtaja.getPiirrettava().destroy();
		}
		piirtaja.setPiirrettava(tehdas.luoPeli(mapname));
	}

	public void goToSplashScreen() {
		valikko = true;
		this.soundengine.playMusic(GameMusic.VALIKKO);
		if (piirtaja.getPiirrettava() != null) {
			piirtaja.getPiirrettava().destroy();
		}
		piirtaja.setPiirrettava(tehdas.luoSplashValikko());
	}

	@Override
	public void toggleCredits() {
		if (credits) {
			credits = false;
			this.goToLevelSelection();
			return;
		}
		
		credits = true;
//		if (edellinen != null) {
//			Piirrettava p = edellinen;
//			edellinen = null;
//			if (piirtaja.getPiirrettava() != null) {
//				piirtaja.getPiirrettava().destroy();
//			}
//			piirtaja.setPiirrettava(p);
//			return;
//		}

		if (!valikko) {
			this.soundengine.playMusic(GameMusic.VALIKKO);
			valikko = true;
		}

//		edellinen = piirtaja.getPiirrettava();
		if (piirtaja.getPiirrettava() != null) {
			piirtaja.getPiirrettava().destroy();
		}
		piirtaja.setPiirrettava(tehdas.luoCredits());

	}

}
