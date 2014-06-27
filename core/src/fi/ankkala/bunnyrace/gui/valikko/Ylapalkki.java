package fi.ankkala.bunnyrace.gui.valikko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import fi.ankkala.bunnyrace.GameControl;


public class Ylapalkki implements Valikko{
	private SpriteBatch batch;
	private Texture ylaosa;
	private int leveys;
	private int korkeus;
	private int palkinKorkeus;
	private float skaala;
	private float tekstinPaikkaX;
	private float tekstinPaikkaY;
	private BitmapFont fontti;
	private GameControl gameControl;
	
	public Ylapalkki(GameControl gc) {
		this.gameControl = gc;
		this.batch = new SpriteBatch();
		this.ylaosa = new Texture(Gdx.files.internal("data/ylareuna1_credits.png"));
		this.fontti = new BitmapFont(Gdx.files.internal("data/fontti.fnt"));
		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	}
	@Override
	public void piirra() {
		batch.begin();
		batch.draw(ylaosa, 0, korkeus-palkinKorkeus, leveys, palkinKorkeus, 0, 1, 1, 0);
		//fontti.draw(batch, "BunnyRace", leveys/2-110, korkeus-palkinKorkeus*0.1f);
		fontti.draw(batch, "BunnyRace", tekstinPaikkaX, tekstinPaikkaY);
		batch.end();
	}

	@Override
	public void resize(int w, int h) {
		this.leveys = w;
		this.korkeus = h;
		this.palkinKorkeus = (int)(0.0931*leveys);
		this.batch = new SpriteBatch();
		
		skaala = Math.min((float) w / 640f,
				(float) palkinKorkeus / 45f);
		
		this.fontti.setScale(skaala);
		this.tekstinPaikkaX = 210*skaala;
		this.tekstinPaikkaY = korkeus-3*skaala;
		
	}

	@Override
	public void destroy() {
		
	}
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (screenY < palkinKorkeus) {
			if (screenX > leveys*0.84) {
				System.out.println("touchdown " + screenX + ", " + screenY);
				this.gameControl.toggleCredits();
			}
			return true;
		}
		
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
