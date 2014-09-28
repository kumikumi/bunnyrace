package fi.ankkala.bunnyrace.gui.valikko;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import fi.ankkala.bunnyrace.fileio.AssetLoader;

public class Credits implements Valikko {
	//private BitmapFont font;
	private BitmapFont font2;
	
	private ShaderProgram fontShader;

	private Texture tausta;
	private Texture libgdxlogo;
	private SpriteBatch batch;
	private int leveys;
	private int korkeus;
	private int ylaPalkinKorkeus;
	private float skaala;

	private int rivi;

	public Credits() {
		this.tausta = new Texture(AssetLoader.load("tausta.png"));
		this.libgdxlogo = new Texture(
				AssetLoader.load("libgdx-logo.png"));
		//this.font = new BitmapFont();
		//this.font = new BitmapFont(Gdx.files.internal("data/fontti.fnt"));

		
		//Texture texture = new Texture(Gdx.files.internal("data/defaultfont.png"), true);
		Texture texture = new Texture(AssetLoader.load("defaultfont.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		//texture.setFilter(TextureFilter.MipMapLinearNearest, TextureFilter.Linear);

		this.font2 = new BitmapFont(AssetLoader.load("defaultfont.fnt"), new TextureRegion(texture), false);

		this.fontShader = new ShaderProgram(AssetLoader.load("press_start/font.vert"), AssetLoader.load("press_start/font.frag"));
		if (!fontShader.isCompiled()) {
		    Gdx.app.error("fontShader", "compilation failed:\n" + fontShader.getLog());
		}
		
		this.batch = new SpriteBatch();
		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		
	}

	@Override
	public void piirra() {

		rivi = 0;
		batch.begin();
		
		batch.draw(tausta, 25, 25, this.leveys - 50, this.korkeus
				- ylaPalkinKorkeus - 50);
		
		batch.setShader(fontShader);

		piirraTekstirivi("CREDITS");
		piirraTekstirivi("--------------------");
		piirraTekstirivi("Tuomas Kaura");
		piirraTekstirivi("   - original idea");
		piirraTekstirivi("   - graphics");
		piirraTekstirivi("   - mapping");
		piirraTekstirivi("Mikko Kumara");
		piirraTekstirivi("   - programming");
		piirraTekstirivi("");
		piirraTekstirivi("MUSIC BY: Kevin MacLeod (incompetech.com)");
		piirraTekstirivi("Licensed under Creative Commons: By Attribution 3.0");
		piirraTekstirivi("http://creativecommons.org/licenses/by/3.0/");
		piirraTekstirivi("The following music pieces are used in this game: ");
		piirraTekstirivi("   - \"Easy Jam\"");
		piirraTekstirivi("   - \"Lachaim\"");
		piirraTekstirivi("   - \"Mariachi Snooze\"");
		piirraTekstirivi("   - \"Slow Ska Game Loop\"");

		rivi = 0;
		piirraTekstirivi2("TECHNOLOGIES USED");
		piirraTekstirivi2("------------------------------------------------------------");
		piirraTekstirivi2("LibGDX - http://libgdx.badlogicgames.com/");
		piirraTekstirivi2("   - Thank you for making this game possible");
		piirraTekstirivi2("RoboVM (iOS version) - http://www.robovm.com/");
		piirraTekstirivi2("   - Without you I would have had to learn Objective C");
		
		batch.setShader(null);


		batch.draw(libgdxlogo, 25 + 175 * skaala, korkeus - ylaPalkinKorkeus
				- 25 - 175 * skaala, 150 * skaala, 25 * skaala);

		batch.end();
	}

	private void piirraTekstirivi(String teksti) {
		if (!teksti.isEmpty()) {
			font2.draw(batch, teksti, 25 + 25 * skaala, korkeus
					- ylaPalkinKorkeus - 25 - 25 * skaala - 20 * skaala * rivi);
		}
		rivi++;
	}

	private void piirraTekstirivi2(String teksti) {
		if (!teksti.isEmpty()) {
			font2.draw(batch, teksti, 25 + 175 * skaala, korkeus
					- ylaPalkinKorkeus - 25 - 25 * skaala - 20 * skaala * rivi);
		}
		rivi++;
	}

	@Override
	public void resize(int w, int h) {
		// TODO Auto-generated method stub
		this.leveys = w;
		this.korkeus = h;
		this.batch = new SpriteBatch();
		this.ylaPalkinKorkeus = (int) (0.0931 * leveys);

//		skaala = Math.min((float) (w - 50) / 640,
//				(float) (h - 50 - ylaPalkinKorkeus) / 370f);
		skaala = Math.min((float) (w - 50) / 590,
				(float) (h - 50 - ylaPalkinKorkeus) / 354f);

		//System.out.println("(w - 50) / 640 = " + w + " - 50) / 640 = " + (float) (w - 50) / 640);
		//System.out.println("(h - 50 - ylaPalkinKorkeus) / 370f) = " + h + "- 50 - " + ylaPalkinKorkeus + ") / 370f = " + (float) (h - 50 - ylaPalkinKorkeus) / 370f);
		System.out.println("Fontin skaala: " + skaala);
		font2.setScale(skaala);
		//font2.setScale(skaala*0.2f);
		// leveysSkaala = w / 640;
		// korkeusSkaala = h / 480;

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
