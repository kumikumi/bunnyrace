package fi.ankkala.bunnyrace.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.physics.box2d.Body;

import fi.ankkala.bunnyrace.game.Pelimaailma;
import fi.ankkala.bunnyrace.game.PelinOhjaus;
import fi.ankkala.bunnyrace.input.HeadsUpDisplay;

public class DebugDisplay implements Piirrettava{
	
	//Voidaan tarvittaessa ottaa käyttöön
//	private OrthographicCamera camera;
//	private HeadsUpDisplay eventlistener;

	private ShapeRenderer debugRenderer;
	private Body wheel1;
	private BitmapFont font;
	private SpriteBatch batch;
	private float width;
	private float height;
	private Pelimaailma pelimaailma;
	private PelinOhjaus peli;

	public DebugDisplay(Pelimaailma pelimaailma, PelinOhjaus peli) {
		// camera = new OrthographicCamera(10, 7);
		// camera.position.set(5, 3, 0);
		// camera.update();
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(1, 0, 0, 1);
		this.width = Gdx.graphics.getWidth();
		this.height = Gdx.graphics.getHeight();
		debugRenderer = new ShapeRenderer();
		this.pelimaailma = pelimaailma;
		this.wheel1 = this.pelimaailma.pelihahmo.getWheel1();
		this.peli = peli;
	}

	public void setEventListener(HeadsUpDisplay e) {
//		this.eventlistener = e;
	}
	
	public void destroy() {
		this.batch.dispose();
		this.batch = null;
//		this.camera = null;
		this.debugRenderer.dispose();
		this.debugRenderer = null;
//		this.eventlistener = null;
		this.font.dispose();
		this.font = null;
		this.pelimaailma = null;
		this.wheel1 = null;
	}

	public void setCamera(OrthographicCamera c) {
//		this.camera = c;
	}
	
//	public void setPeli(Peli p) {
//		this.peli = p;
//	}

//	public void setFysiikka(Fysiikkamallinnus f) {
//		this.fysiikka = f;
//		this.wheel1 = peli.getAuto().getWheel1();
//	}
//	
	public void resize(int w, int h) {
		this.width = w;
		this.height = h;
		batch = new SpriteBatch();
//		font = new BitmapFont();
//		font.setColor(1, 0, 0, 1);
	}

	public void piirra() {
		// if (eventlistener == null) {
		// return;
		// }

		batch.begin();
		font.draw(batch, "width: " + this.width, 20, this.height - 20);
		font.draw(batch, "height: " + this.height, 20, this.height - 40);
//		font.draw(batch, "width: " + this.width, 20, 20);
//		font.draw(batch, "height: " + this.height, 20, 40);

			font.draw(batch, "paikka: (" + this.wheel1.getPosition().x + ", " + this.wheel1.getPosition().y + ")", 20, this.height - 60);
			font.draw(batch, "pisteet: " + this.peli.getPisteet(), 20, this.height - 80);
			font.draw(batch, "aika: " + this.peli.getKellonAika(), 20, this.height - 100);

		if (peli.isEteen()){
			font.draw(batch, "ETEEN", 20, this.height - 120);
		} else if (peli.isTaakse()) {
			font.draw(batch, "TAAKSE", 20, this.height - 120);

		}
		batch.end();

	}
}
