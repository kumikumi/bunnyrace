package fi.ankkala.bunnyrace.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import fi.ankkala.bunnyrace.fileio.AssetLoader;
import fi.ankkala.bunnyrace.game.Peli;

public class EndGameText implements Piirrettava{

	private Peli peli;
	
	private SpriteBatch batch;
	private BitmapFont font3;
	private int leveys;
	private int korkeus;
	private int ylaPalkinKorkeus;
	private float skaala;

	private String levelCompleteText;
	private String levelFailedText;
	
	private Texture tausta;
	
	private ShaderProgram fontShader;

	public EndGameText(Peli peli) {
		this.tausta = new Texture(AssetLoader.load("tausta.png"));
		this.peli = peli;
		this.levelCompleteText = "Level complete!";
		this.levelFailedText = "FAIL";
		this.batch = new SpriteBatch();
		
		Texture texture = new Texture(AssetLoader.load("press_start/press_start.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		this.font3 = new BitmapFont(AssetLoader.load("press_start/press_start.fnt"), new TextureRegion(texture), false);

		this.fontShader = new ShaderProgram(AssetLoader.load("press_start/font.vert"), AssetLoader.load("press_start/font.frag"));
		if (!fontShader.isCompiled()) {
		    Gdx.app.error("fontShader", "compilation failed:\n" + fontShader.getLog());
		}
		
		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
	}
	
	@Override
	public void piirra() {
		
		
		batch.begin();
		
		batch.draw(tausta, 25, 25, this.leveys - 50, this.korkeus
				- ylaPalkinKorkeus - 50);
		
		batch.setShader(fontShader);
		if (peli.isLevelComplete()) {
			font3.draw(batch, levelCompleteText, 100, 100);
		} else if (peli.isLevelFailed()) {
			font3.draw(batch, levelFailedText, 100, 100);
		}
		
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



}
