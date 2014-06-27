package fi.ankkala.bunnyrace.gui.valikko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import fi.ankkala.bunnyrace.GameControl;
import fi.ankkala.bunnyrace.sound.GameSound;
import fi.ankkala.bunnyrace.sound.SoundEngine;

public class Alkuvalikko implements Valikko {
	private SpriteBatch batch;
	private BitmapFont font1;
	private BitmapFont font3;
	private Texture mansikka;
	private int leveys;
	private int korkeus;
	
	private float mansikanPaikkaX;
	private float mansikanPaikkaY;
	private float mansikanLeveys;
	private float mansikanKorkeus;
	private SoundEngine soundengine;
	private GameControl gamecontrol;
	
	private float ylaPalkinKorkeus;
	private float skaala;
	
	private ShaderProgram fontShader;

	public Alkuvalikko(SoundEngine sound, GameControl gc) {
		
		this.gamecontrol = gc;

		this.soundengine = sound;
		this.batch = new SpriteBatch();
		this.font1 = new BitmapFont();
		
		Texture texture = new Texture(Gdx.files.internal("data/press_start.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.font3 = new BitmapFont(Gdx.files.internal("data/press_start.fnt"), new TextureRegion(texture), false);

		this.fontShader = new ShaderProgram(Gdx.files.internal("data/font.vert"), Gdx.files.internal("data/font.frag"));
		if (!fontShader.isCompiled()) {
		    Gdx.app.error("fontShader", "compilation failed:\n" + fontShader.getLog());
		}
		
		

		
		font1.setColor(0, 0, 0, 1);
		this.mansikka = new Texture(
				Gdx.files.internal("data/etualan_mansikka.png"));
		this.leveys = Gdx.graphics.getWidth();
		this.korkeus = Gdx.graphics.getHeight();
	}

	@Override
	public void piirra() {
		batch.begin();
		batch.draw(mansikka, mansikanPaikkaX, mansikanPaikkaY, mansikanLeveys, mansikanKorkeus, 0, 1, 1, 0);
		font1.draw(batch, "Bunnyrace-0.10", leveys-120, 30);
		batch.setShader(fontShader);
		font3.draw(batch, "Start", mansikanPaikkaX+70*skaala, mansikanPaikkaY+120*skaala);
		batch.setShader(null);
		batch.end();

	}

	@Override
	public void resize(int w, int h) {
		this.ylaPalkinKorkeus = (int) (0.0931 * leveys);
		skaala = Math.min((float) w / 640,
				(float) (h - ylaPalkinKorkeus) / 404f);
		System.out.println("skaala alkuvalikossa: " + skaala);
		this.leveys = w;
		this.korkeus = h;
		this.batch = new SpriteBatch();
		
		this.mansikanPaikkaX = (float)leveys/2-120*skaala;
		this.mansikanPaikkaY = (float)korkeus/2-140*skaala;
		
		this.mansikanLeveys = 236*skaala;
		this.mansikanKorkeus = 276*skaala;
		
		this.font3.setScale(skaala*0.8f);

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		this.batch.dispose();
		this.font1.dispose();
		this.font3.dispose();
		this.fontShader.dispose();
		this.mansikka.dispose();
		
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
		// TODO Auto-generated method stub
		screenY = korkeus-screenY;
		//System.out.println("Touchdown: " + screenX + ", " + screenY);
		if (screenX > this.mansikanPaikkaX && screenX < this.mansikanPaikkaX + this.mansikanLeveys) {
			if (screenY > this.mansikanPaikkaY && screenY < this.mansikanPaikkaY + this.mansikanKorkeus) {
				soundengine.playSound(GameSound.MANSIKKA);
				this.gamecontrol.goToLevelSelection();
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
