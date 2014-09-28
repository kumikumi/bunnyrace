package fi.ankkala.bunnyrace;

import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

import fi.ankkala.bunnyrace.auto.PorkkanaAuto;
import fi.ankkala.bunnyrace.auto.Rengas;
import fi.ankkala.bunnyrace.auto.renkaat.HyvaRengas;
import fi.ankkala.bunnyrace.fileio.MapLoader;
import fi.ankkala.bunnyrace.fileio.TextureLoader;
import fi.ankkala.bunnyrace.game.GameResult;
import fi.ankkala.bunnyrace.game.Peli;
import fi.ankkala.bunnyrace.game.Pelimaailma;
import fi.ankkala.bunnyrace.gui.DebugDisplay;
import fi.ankkala.bunnyrace.gui.MaailmanPiirtaja;
import fi.ankkala.bunnyrace.gui.PelinPiirtaja;
import fi.ankkala.bunnyrace.gui.Piirrettava;
import fi.ankkala.bunnyrace.gui.valikko.Alkuvalikko;
import fi.ankkala.bunnyrace.gui.valikko.Credits;
import fi.ankkala.bunnyrace.gui.valikko.LevelCompleteValikko;
import fi.ankkala.bunnyrace.gui.valikko.TasonValinta;
import fi.ankkala.bunnyrace.gui.valikko.ValikonPiirtaja;
import fi.ankkala.bunnyrace.gui.valikko.ValikonTausta;
import fi.ankkala.bunnyrace.gui.valikko.Ylapalkki;
import fi.ankkala.bunnyrace.input.HeadsUpDisplay;
import fi.ankkala.bunnyrace.sound.SoundEngine;

public class PiirrettavaTehdas {
	private TextureLoader textureLoader;
	private SoundEngine soundengine;
	private MapLoader loader;
	private GameControl gc;
	private ValikonTausta tausta;
	private Ylapalkki ylapalkki;

	public PiirrettavaTehdas(GameControl gc, SoundEngine sound) {
		this.textureLoader = new TextureLoader();
		this.soundengine = sound;
		this.loader = new MapLoader();
		this.gc = gc;
	}

	public Piirrettava luoPeli(String mapname) {
		//Rengas perusRengas = new TavallinenRengas();
		//Rengas liukasRengas = new LiukasRengas();
		Rengas hyvaRengas = new HyvaRengas();
		PorkkanaAuto porkkana = new PorkkanaAuto(hyvaRengas);
		//AutoMaarittely perusAuto = new AutoMaarittely(perusRengas, perusRengas);
		Pelimaailma pelimaailma = null;
		try {
			pelimaailma = loader.loadMap(mapname, porkkana);
		} catch (Exception e) {
			e.printStackTrace();
		}

		MaailmanPiirtaja maailmanPiirtaja = new MaailmanPiirtaja(pelimaailma,
				textureLoader);

		Peli peli = new Peli(gc, pelimaailma, soundengine, textureLoader,
				maailmanPiirtaja.getCamera());

		HeadsUpDisplay hud = new HeadsUpDisplay(gc, textureLoader, pelimaailma,
				maailmanPiirtaja.getCamera(), peli);

		Gdx.input.setInputProcessor(hud);

		DebugDisplay debugHUD = new DebugDisplay(pelimaailma, peli);

		return new PelinPiirtaja(peli, peli.getDebugFysiikka(),
				maailmanPiirtaja, hud, debugHUD);
	}

	public Piirrettava luoTasonValintaValikko(Map<String, GameResult> huipputulokset) {
		TasonValinta tasonvalinta = new TasonValinta(gc, huipputulokset);
		ValikonPiirtaja palautus = new ValikonPiirtaja(tasonvalinta, tausta,
				ylapalkki);
		// TasonValinta palautus = new TasonValinta(gc, soundengine, tausta);

		Gdx.input.setInputProcessor((InputProcessor) palautus);
		// Alkuvalikko palautus = new Alkuvalikko(soundengine);

		return palautus;
	}

	public Piirrettava luoSplashValikko() {

		if (tausta == null) {
			tausta = new ValikonTausta();
		}
		if (ylapalkki == null) {
			ylapalkki = new Ylapalkki(gc);
		}

		Alkuvalikko valikko = new Alkuvalikko(soundengine, gc);
		ValikonPiirtaja palautus = new ValikonPiirtaja(valikko, tausta,
				ylapalkki);
		Gdx.input.setInputProcessor((InputProcessor) palautus);
		return palautus;
	}

	public Piirrettava luoCredits() {

		Credits valikko = new Credits();
		ValikonPiirtaja palautus = new ValikonPiirtaja(valikko, tausta,
				ylapalkki);
		Gdx.input.setInputProcessor((InputProcessor) palautus);
		return palautus;
	}

	public Piirrettava luoLevelCompleteValikko(GameResult result) {
		LevelCompleteValikko valikko = new LevelCompleteValikko(gc, result);
		ValikonPiirtaja palautus = new ValikonPiirtaja(valikko, tausta, ylapalkki);
		Gdx.input.setInputProcessor((InputProcessor) palautus);
		return palautus;
	}
}
