package fi.ankkala.bunnyrace.gui.valikko;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

import fi.ankkala.bunnyrace.GameControl;
import fi.ankkala.bunnyrace.fileio.MapLister;
import fi.ankkala.bunnyrace.gui.kscroll.KScroller;

public class TasonValinta implements Valikko {

	private ShaderProgram fontShader;

	private SpriteBatch batch;
	private BitmapFont font;
	private float width;
	private float height;
//	private TextureRegion inactive;
	private TextureRegion active;
	private TextureRegion error;
	private TextureRegion kaytettava;
	private float palkinLeveys;
	private float palkinKorkeus;
	private float palkinOffsetX;
	private float palkinOffsetY;
	private float tekstinOffsetX;
	private float tekstinOffsetY;
	private float tekstinPaikkaY;
	private KScroller scroller;
	private float skrollausY;
	private float maxScroll;
	private float minScroll;
	private List<String> lista;
	private List<Integer> virheelliset;
	private int aktiivinen;
	private float skrollausAlussa;
	private float ylaPalkinKorkeus;
	private float skaala;
	private Texture napit;
	private Texture porkkanaTexture;
	private TextureRegion porkkana;
	private GameControl bunnyrace;
	private float tyhjaTilaYlhaalla;

	// class ListanLataaja extends Thread {
	// private MapLister ml;
	// ListanLataaja(MapLister ml) {
	// this.ml = ml;
	// }
	//
	// @Override
	// public void run() {
	// // TODO Auto-generated method stub
	// ml.loadList();
	// }
	//
	// }

	public TasonValinta(GameControl br) {

		MapLister ml = new MapLister();
		this.lista = ml.getList();
		this.virheelliset = ml.getVirheelliset();
		ml.loadList();

		this.bunnyrace = br;

		this.scroller = new KScroller();

		this.aktiivinen = -1;
		this.batch = new SpriteBatch();

		Texture texture = new Texture(
				Gdx.files.internal("data/press_start.png"), true);
		texture.setFilter(TextureFilter.MipMapLinearNearest,
				TextureFilter.Linear);
		this.font = new BitmapFont(Gdx.files.internal("data/press_start.fnt"),
				new TextureRegion(texture), false);
		this.fontShader = new ShaderProgram(
				Gdx.files.internal("data/font.vert"),
				Gdx.files.internal("data/font.frag"));
		if (!fontShader.isCompiled()) {
			Gdx.app.error("fontShader",
					"compilation failed:\n" + fontShader.getLog());
		}

		this.font.setColor(0.752941176f, 1, 0.68627451f, 1);

		lataaTekstuuri();
		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	}

	public void destroy() {
		this.active = null;
		this.batch.dispose();
		this.batch = null;
		this.bunnyrace = null;

		this.fontShader.dispose();
		this.font.dispose();
		this.font = null;

		this.lista = null;

		this.napit.dispose();
		this.napit = null;

		this.porkkanaTexture.dispose();
		this.porkkanaTexture = null;

		this.scroller = null;

	}

	public void resize(int w, int h) {
		this.width = (float) w;
		this.height = (float) h;

		this.ylaPalkinKorkeus = (int) (0.0931 * width);
		skaala = Math.min((float) w / 640,
				(float) (h - ylaPalkinKorkeus) / 404f);

		this.palkinLeveys = 440 * skaala;
		this.palkinKorkeus = 100 * skaala;
		this.palkinOffsetX = (width - palkinLeveys) / 2;
		this.palkinOffsetY = 130 * skaala;
		this.tyhjaTilaYlhaalla = ylaPalkinKorkeus;

		this.tekstinOffsetX = palkinOffsetX + 0.32f * palkinLeveys;
		this.tekstinOffsetY = 0.6f * palkinKorkeus;
		this.tekstinPaikkaY = -tyhjaTilaYlhaalla + skrollausY + this.height
				+ 0.7f * palkinKorkeus + 5f;
		this.minScroll = 0;

		this.maxScroll = lista.size() * palkinOffsetY - this.height
				+ palkinOffsetY - palkinKorkeus + tyhjaTilaYlhaalla;
		batch = new SpriteBatch();
		this.font.setScale(this.skaala * 0.5f);
	}

	// private void luoLista() {
	// this.lista = new ArrayList<String>();
	// this.lista.add("item1");
	// this.lista.add("item2");
	// this.lista.add("item3");
	// this.lista.add("item4");
	// this.lista.add("item5");
	// this.lista.add("item6");
	// this.lista.add("item7");
	// this.lista.add("item8");
	// }

	private void lataaTekstuuri() {
		this.napit = new Texture(Gdx.files.internal("data/menubuttons.png"));
//		this.inactive = new TextureRegion(napit, 0, 0, 32, 8);
		this.active = new TextureRegion(napit, 0, 8, 32, 8);
		this.error = new TextureRegion(napit, 0, 16, 32, 8);
		this.porkkanaTexture = new Texture(
				Gdx.files.internal("data/valikkoporkkana.png"));
		this.porkkana = new TextureRegion(porkkanaTexture);
	}

	public void piirra() {
		// TODO: Palkin liikuttelu poistettava piirra-metodista
		// this.skrollausY -= this.scroller.slowdown();
		this.skrollausY = Math
				.min(maxScroll,
						Math.max(minScroll,
								this.skrollausY - this.scroller.slowdown()));

		// Gdx.gl.glClearColor(0, 0, 0.6f, 1);
		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// batch.setProjectionMatrix(camera.combined);

		batch.begin();
		// font.draw(batch, "width: " + this.width, 20, this.height - 20);
		// font.draw(batch, "height: " + this.height, 20, this.height - 40);
		//
		// font.draw(batch, "SCALE: " + this.font.getScaleX(), 20, this.height -
		// 60);

		// batch2.draw(inactive, 0.1f*width, height - 0.1f*height);

		for (int i = 0; i < lista.size(); i++) {
			if (virheelliset.contains(i)) {
				kaytettava = error;
			} else if (i == aktiivinen) {
				kaytettava = active;
			} else {
				kaytettava = porkkana;
			}

			batch.draw(kaytettava, palkinOffsetX, -tyhjaTilaYlhaalla
					+ skrollausY + this.height - palkinOffsetY * (i + 1),
					palkinLeveys, palkinKorkeus);

			batch.setShader(fontShader);

			this.tekstinPaikkaY = this.height
					- tyhjaTilaYlhaalla
					- (palkinOffsetY * (i + 1) - skrollausY - tekstinOffsetY);
			// this.tekstinPaikkaY = this.height - palkinOffsetY * (i + 1) +
			// skrollausY;
			// this.tekstinPaikkaY = -tyhjaTilaYlhaalla+skrollausY + this.height
			// - palkinOffsetY * (i + 1) + 0.7f * palkinKorkeus;

			font.draw(batch, lista.get(i), tekstinOffsetX, tekstinPaikkaY);
			batch.setShader(null);
		}

		batch.end();

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
		scroller.grab(screenY);

		if (screenX > palkinOffsetX && screenX < palkinOffsetX + palkinLeveys) {
			for (int i = 0; i < lista.size(); i++) {
				if (screenY < -skrollausY + palkinOffsetY * (i + 1)
						+ tyhjaTilaYlhaalla
						&& screenY > -skrollausY + palkinOffsetY * (i + 1)
								- palkinKorkeus + tyhjaTilaYlhaalla) {
					this.aktiivinen = i;
				}
			}
		}
		this.skrollausAlussa = skrollausY;

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (aktiivinen != -1) {

			if (screenX > palkinOffsetX
					&& screenX < palkinOffsetX + palkinLeveys) {
				if (screenY < -skrollausY + palkinOffsetY * (aktiivinen + 1)
						+ tyhjaTilaYlhaalla
						&& screenY > -skrollausY + palkinOffsetY
								* (aktiivinen + 1) - palkinKorkeus
								+ tyhjaTilaYlhaalla) {

					bunnyrace.loadMap(lista.get(aktiivinen));
					return false;
				}

			}
			this.aktiivinen = -1;
		}
		scroller.startslowdown();
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		scroller.begin(screenY);
		this.skrollausY = Math.max(minScroll,
				Math.min(maxScroll, this.skrollausY - scroller.speed));
		if (this.aktiivinen != -1) {
			if (Math.abs(this.skrollausY - this.skrollausAlussa) > this.palkinKorkeus / 10) {
				this.aktiivinen = -1;
			}
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		this.skrollausY = Math.max(minScroll,
				Math.min(maxScroll, this.skrollausY + 30 * amount));
		return false;
	}

}
