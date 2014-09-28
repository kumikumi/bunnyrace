package fi.ankkala.bunnyrace.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

import fi.ankkala.bunnyrace.GameControl;
import fi.ankkala.bunnyrace.fileio.TextureLoader;
import fi.ankkala.bunnyrace.fysiikka.Fysiikkamallinnus;
import fi.ankkala.bunnyrace.gui.Piirrettava;
import fi.ankkala.bunnyrace.sound.GameSound;
import fi.ankkala.bunnyrace.sound.SoundEngine;

public class Peli implements GameEventObserver, PelinOhjaus {
	private boolean debug;
	private int pisteet;
	private int turbo;
	private int kaytettavaTurbo;
	private String mapname;

	private boolean levelComplete;
	private boolean levelFailed;
	private GameResult gameResult;

	private SoundEngine sound;
	private Fysiikkamallinnus fysiikka;
	private GameControl gameControl;

	private Kello kello;

	private Kello endGameTimer;

	private GameState gameState = GameState.RUNNING;

	public Peli(GameControl br, Pelimaailma maailma, SoundEngine s,
			TextureLoader tx, OrthographicCamera debugCamera) {
		this.mapname = maailma.mapname;
		this.sound = s;
		this.gameControl = br;
		this.fysiikka = new Fysiikkamallinnus(maailma, this);
		this.fysiikka.setDebugCamera(debugCamera);
		// sound.playMusic(GameMusic.MUSIIKKI1);
		sound.playRandomGameMusic();
		this.kello = new Kello();
		this.kello.kaynnista();
	}

	public void toggleDebug() {
		this.debug = !this.debug;
	}

	public boolean getDebug() {
		return this.debug;
	}

	public boolean isEteen() {
		return this.fysiikka.isEteen();
	}

	public boolean isTaakse() {
		return this.fysiikka.isTaakse();
	}

	public void setEteen(boolean eteen) {
		this.fysiikka.setEteen(eteen);
	}

	public void setTaakse(boolean taakse) {
		this.fysiikka.setTaakse(taakse);
	}

	public void togglePause() {
		if (this.gameState == GameState.RUNNING) {
			this.gameState = GameState.PAUSED;
		} else {
			this.gameState = GameState.RUNNING;
		}
	}

	public GameState getGameState() {
		return this.gameState;
	}

	public void destroy() {
		this.fysiikka.destroy();
	}

	public int getPisteet() {
		return this.pisteet;
	}

	@Override
	public void herne(Vector2 sijainti) {
		this.sound.playSound(GameSound.HERNE);
		this.pisteet += 20;
	}

	@Override
	public void mansikka(Vector2 sijainti) {
		this.sound.playSound(GameSound.MANSIKKA);
		this.lisaaTurboa();
		this.pisteet += 50;
	}

	@Override
	public void pommi(Vector2 sijainti) {
		this.sound.playSound(GameSound.POMMI);
		// this.rajahdys = sijainti;
		this.fysiikka.setRajahdys(sijainti);
	}

	private void lisaaTurboa() {
		this.turbo = Math.min(this.turbo + 100, 400);
	}

	public void kaytaTurbo() {
		if (this.turbo == 0) {
			return;
		}
		if (!this.fysiikka.turboKaytossa()) {
			this.fysiikka.setTurbo(true);
			this.kaytettavaTurbo = Math.min(this.turbo, 100);
		}
	}

	private void kulutaTurboa() {
		if (this.fysiikka.turboKaytossa()) {
			this.turbo--;
			kaytettavaTurbo--;
			if (kaytettavaTurbo == 0) {
				this.fysiikka.setTurbo(false);
			}
		}
	}

	public String getKellonAika() {
		return this.kello.toString();
	}

	public int getTurbo() {
		return this.turbo;
	}

	public void levelComplete() {
		GameResult gr = new GameResult();
		gr.tasonNimi = mapname;
		gr.pisteet = this.pisteet;
		gr.aikaMillisekunteina = this.kello.getAikaMillisekunteina();
		gr.completed = true;
		gr.herne_lkm = 0;
		gr.mansikka_lkm = 0;
		this.gameResult = gr;
		this.levelComplete = true;
		this.endGameTimer = new Kello();
		this.endGameTimer.kaynnista();
		// this.gameControl.levelComplete(gr);
	}

	public void etene() {
		this.fysiikka.step();

		if (levelComplete) {
			if (endGameTimer.getAikaMillisekunteina() > 2000) {
				this.gameControl.levelComplete(this.gameResult);
			}
			return;
		}
		
		if (levelFailed) {
			if (endGameTimer.getAikaMillisekunteina() > 2000) {
				this.gameControl.loadMap(this.mapname);
			}
			return;
		}
		

		this.kulutaTurboa();
		this.fysiikka.liikutaPelihahmoa();

	}

	@Override
	public void levelFailed() {
		this.levelFailed = true;
		this.endGameTimer = new Kello();
		this.endGameTimer.kaynnista();
	}

	public Piirrettava getDebugFysiikka() {
		return this.fysiikka;
	}

	public boolean isLevelComplete() {
		return levelComplete;
	}

	public boolean isLevelFailed() {
		return levelFailed;
	}

}
