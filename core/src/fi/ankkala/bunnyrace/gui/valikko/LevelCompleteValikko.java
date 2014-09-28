package fi.ankkala.bunnyrace.gui.valikko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import fi.ankkala.bunnyrace.GameControl;
import fi.ankkala.bunnyrace.fileio.AssetLoader;
import fi.ankkala.bunnyrace.game.GameResult;
import fi.ankkala.bunnyrace.sound.SoundEngine;

public class LevelCompleteValikko implements Valikko{
	
	private SpriteBatch batch;
	private BitmapFont font1;
	private BitmapFont font3;
	private int leveys;
	private int korkeus;
	private int ylaPalkinKorkeus;
	private float skaala;

	private SoundEngine soundengine;
	private GameControl gamecontrol;
	
	private ShaderProgram fontShader;

	public LevelCompleteValikko(GameControl gamecontrol, GameResult result) {
		this.batch = new SpriteBatch();
		this.gamecontrol = gamecontrol;
		
		Texture texture = new Texture(AssetLoader.load("press_start/press_start.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.font3 = new BitmapFont(AssetLoader.load("press_start/press_start.fnt"), new TextureRegion(texture), false);

		this.fontShader = new ShaderProgram(AssetLoader.load("press_start/font.vert"), AssetLoader.load("press_start/font.frag"));
		if (!fontShader.isCompiled()) {
		    Gdx.app.error("fontShader", "compilation failed:\n" + fontShader.getLog());
		}
		
	}
	
	@Override
	public void piirra() {
		batch.begin();
		batch.setShader(fontShader);
		font3.draw(batch, "LEVEL COMPLETE!!11", 100, 100);
		batch.setShader(null);

		batch.end();
	}

	@Override
	public void resize(int w, int h) {
		this.leveys = w;
		this.korkeus = h;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
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
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		gamecontrol.goToLevelSelection();
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
