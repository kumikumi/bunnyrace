package fi.ankkala.bunnyrace;

import java.util.HashMap;
import java.util.Map;

import fi.ankkala.bunnyrace.game.GameResult;
import fi.ankkala.bunnyrace.sound.GameMusic;
import fi.ankkala.bunnyrace.sound.SoundEngine;

public class SovellusOhjain implements GameControl {
	private Piirtaja piirtaja;
	private PiirrettavaTehdas tehdas;
	private SoundEngine soundengine;
	private boolean valikko;
	private boolean credits;
	private Map<String, GameResult> results;

	public SovellusOhjain(Piirtaja br) {
		this.piirtaja = br;
		this.soundengine = new SoundEngine();
		this.tehdas = new PiirrettavaTehdas(this, this.soundengine);
		this.goToSplashScreen();
		this.credits = false;
		this.results = new HashMap<>();
	}

	@Override
	public void levelComplete(GameResult result) {
		System.out.println("Onneksi olkoon, taso läpi!");
		if (results.containsKey(result.tasonNimi)) {
			results.put(result.tasonNimi, results.get(result.tasonNimi).improve(result));
		} else {
			results.put(result.tasonNimi, result);
		}
		this.goToLevelCompleteScreen(result);
	}
	
	private void goToLevelCompleteScreen(GameResult result) {
		if (!valikko) {
			this.soundengine.playMusic(GameMusic.VALIKKO);
			valikko = true;
		}
		if (piirtaja.getPiirrettava() != null) {
			piirtaja.getPiirrettava().destroy();
		}
		piirtaja.setPiirrettava(tehdas.luoLevelCompleteValikko(result));
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
		piirtaja.setPiirrettava(tehdas.luoTasonValintaValikko(results));
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
