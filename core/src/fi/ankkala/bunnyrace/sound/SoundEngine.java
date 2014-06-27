package fi.ankkala.bunnyrace.sound;

import java.util.EnumMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundEngine {
private EnumMap<GameSound, Sound> soundmap;
private EnumMap<GameSound, Float> volumemap;
private EnumMap<GameMusic, Music> musicmap;
private GameMusic playing;
private Random arpoja;


	public SoundEngine() {
		this.arpoja = new Random();
		this.soundmap = new EnumMap<GameSound, Sound>(GameSound.class);
		this.volumemap = new EnumMap<GameSound, Float>(GameSound.class);
		this.musicmap = new EnumMap<GameMusic, Music>(GameMusic.class);
		soundmap.put(GameSound.HERNE, Gdx.audio.newSound(Gdx.files.internal("data/sound/sound1.ogg")));
		volumemap.put(GameSound.HERNE, 0.2f);
		soundmap.put(GameSound.MANSIKKA, Gdx.audio.newSound(Gdx.files.internal("data/sound/mansikka.ogg")));
		volumemap.put(GameSound.MANSIKKA, 0.6f);
		soundmap.put(GameSound.POMMI, Gdx.audio.newSound(Gdx.files.internal("data/sound/pommi.ogg")));
		volumemap.put(GameSound.POMMI, 0.9f);
		
		musicmap.put(GameMusic.MUSIIKKI1, Gdx.audio.newMusic(Gdx.files.internal("data/sound/music/Lachaim.mp3")));
		musicmap.get(GameMusic.MUSIIKKI1).setLooping(true);
		musicmap.put(GameMusic.VALIKKO, Gdx.audio.newMusic(Gdx.files.internal("data/sound/music/Mariachi Snooze.mp3")));
		musicmap.get(GameMusic.VALIKKO).setLooping(true);
		musicmap.put(GameMusic.MUSIIKKI2, Gdx.audio.newMusic(Gdx.files.internal("data/sound/music/Easy Jam.mp3")));
		musicmap.get(GameMusic.MUSIIKKI2).setLooping(true);
		
		musicmap.put(GameMusic.MUSIIKKI3, Gdx.audio.newMusic(Gdx.files.internal("data/sound/music/Slow Ska Game Loop.mp3")));
		musicmap.get(GameMusic.MUSIIKKI3).setLooping(true);
		musicmap.get(GameMusic.MUSIIKKI3).setVolume(0.6f);
		//this.music = Gdx.audio.newMusic(Gdx.files.internal("data/sound/music/Lachaim.mp3"));
		//this.music.setLooping(true);
	}
	
	
	public void playSound(GameSound sound) {
		soundmap.get(sound).play(volumemap.get(sound));
	}
	
//	public void playMusic(BackGroundMusic m) {
	public void playMusic(GameMusic musiikki) {
		this.stopMusic();
		this.playing = musiikki;
		musicmap.get(musiikki).play();
	}
	
	public void playRandomGameMusic() {

		int luku = arpoja.nextInt(3);
		
		switch(luku) {
		case 0:
			this.playMusic(GameMusic.MUSIIKKI1);
			break;
		case 1:
			this.playMusic(GameMusic.MUSIIKKI2);
			break;
		case 2:
			this.playMusic(GameMusic.MUSIIKKI3);
			break;
		}
	}
	
	public void stopMusic() {
		if (playing == null) {
			return;
		}
		musicmap.get(playing).stop();
		this.playing = null;
	}
}
