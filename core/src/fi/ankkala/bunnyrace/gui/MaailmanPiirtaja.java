package fi.ankkala.bunnyrace.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

import fi.ankkala.bunnyrace.fileio.TextureLoader;
import fi.ankkala.bunnyrace.game.Pelimaailma;

public class MaailmanPiirtaja implements Piirrettava {
	private OrthographicCamera camera;
	private OrthographicCamera backgroundCamera;
	private SpriteBatch batch;

	private ShapeRenderer shaperenderer;
	private Texture[] taulukko;
	private Pelimaailma pelimaailma;
	private TextureLoader textureLoader;
	private Texture tausta;

	private TextureRegion herneTexture;
	private TextureRegion mansikkaTexture;
	private TextureRegion pommiTexture;
	private TextureRegion rengasTexture;
	private TextureRegion autoTexture;
//
//	private TextureRegion pupu2Texture;

	private Animation explodeAnimation;
	private float stateTime;
	private Vector2 rajahdys;

	private float skaalaus = 70f;

	public MaailmanPiirtaja(Pelimaailma p, TextureLoader t) {
		this.pelimaailma = p;
		this.textureLoader = t;
		this.loadCommonTextures();
		float aspectRatio = (float) Gdx.graphics.getWidth()
				/ Gdx.graphics.getHeight();
		this.camera = new OrthographicCamera(20, 20 / aspectRatio);
		this.backgroundCamera = new OrthographicCamera(20, 20 / aspectRatio);

		// "Skaalaus" tarkoittaa tässä
		// "kuinka monta metriä on yksi kuvan pikseli"
		// ts. kartan pituus metreinä jaettuna kuvan pituus pikseleinä

		this.skaalaus = pelimaailma.skaala / pelimaailma.kuvanleveys;

		backgroundCamera.zoom = 1.4f;
		camera.zoom = 0.8f;

		this.batch = new SpriteBatch();
		this.shaperenderer = new ShapeRenderer();
		this.taulukko = pelimaailma.maisema;

	}

	private void loadCommonTextures() {
		this.herneTexture = textureLoader.getHerneTexture();
		this.mansikkaTexture = textureLoader.getMansikkaTexture();
		this.pommiTexture = textureLoader.getPommiTexture();
		this.autoTexture = textureLoader.getAutoTexture();
		this.rengasTexture = textureLoader.getRengasTexture();
		this.tausta = textureLoader.getTausta();
		this.explodeAnimation = textureLoader.getExplodeAnimation();
//		this.pupu2Texture = textureLoader.getPupu2();

	}

	public void destroy() {
		this.batch.dispose();
		this.batch = null;
		this.camera = null;
		this.backgroundCamera = null;
		this.pelimaailma = null;
		this.rajahdys = null;
		this.shaperenderer.dispose();
		this.shaperenderer = null;
		for (Texture t : this.taulukko) {
			t.dispose();
		}
		this.taulukko = null;
		this.tausta = null;
	}

	private void tutkiOnkoRajahdysta() {
		if (this.pelimaailma.rajahdys == null) {
			return;
		}
		this.rajahdys = this.pelimaailma.rajahdys;
		this.stateTime = 0;
		this.pelimaailma.rajahdys = null;

	}

	public OrthographicCamera getCamera() {
		return this.camera;
	}

	public void setRajahdys(Vector2 v) {
		this.stateTime = 0;
		this.rajahdys = v;
	}

	public void resize(int width, int height) {
		// float aspectRatio = width / height;
		this.camera.viewportHeight = 20 * (float) height / width;
		this.backgroundCamera.viewportHeight = 20 * (float) height / width;
	}

	public void piirra() {
		if (this.pelimaailma == null) {
			return;
		}

		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		tutkiOnkoRajahdysta();
		asetaKamerat();
		piirraTausta();
		piirraPelimaailma();

		// shaperenderer.setProjectionMatrix(camera.combined);
		// shaperenderer.begin(ShapeType.Circle);
		// shaperenderer.setColor(1, 1, 0, 1);
		// // shaperenderer.circle(wheel1.getPosition().x,
		// // wheel1.getPosition().y, 0.5f);
		//
		// for (Vector2 v : pommit) {
		// shaperenderer.circle(v.x, v.y, 0.5f);
		// }
		//
		// shaperenderer.end();

	}

	private void piirraTausta() {
		batch.setProjectionMatrix(backgroundCamera.combined);
		batch.begin();

		// batch.draw(tausta, -60, -40, 200, 100);
		// batch.draw(tausta, -20, -40, 200, 100);
		batch.draw(tausta, -120, -240, 1200, 600);

		batch.end();
	}

	private void piirraPelimaailma() {
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		piirraPommit();
		piirraMaailmaKuvat();
		piirraAuto();
		piirraKerattavat();

		batch.end();
	}

	private void asetaKamerat() {
		camera.position.x = pelimaailma.pelihahmo.getRunko().getPosition().x;
		camera.position.y = pelimaailma.pelihahmo.getRunko().getPosition().y + 1;
		camera.update();

		backgroundCamera.position.x = camera.position.x;
		backgroundCamera.position.y = camera.position.y;
		backgroundCamera.zoom = camera.zoom + 9.6f;
		// backgroundCamera.zoom = camera.zoom + 1f;
		backgroundCamera.update();
	}

	private void piirraPommit() {
		stateTime += Gdx.graphics.getDeltaTime();

		for (Vector2 v : pelimaailma.pommit) {
			batch.draw(pommiTexture, v.x - 2.2f, v.y - 0.35f, 4f, 4f);
		}

		if (this.rajahdys != null) {
			batch.draw(explodeAnimation.getKeyFrame(stateTime, false),
					rajahdys.x - 2.2f, rajahdys.y - 0.35f, 4f, 4f);
		}
	}

	private void piirraMaailmaKuvat() {

		for (int i = 0; i < pelimaailma.kuvienlkm; i++) {
			batch.draw(taulukko[i], i * 1024 * skaalaus, 0, 1024 * skaalaus,
					1024 * skaalaus);
		}

	}

	private void piirraAuto() {
		// float autoX = pelimaailma.pelihahmo.getRunko().getPosition().x - 1.8f
		// * (float) Math.cos(pelimaailma.pelihahmo.getRunko().getAngle())
		// + 0.5f
		// * (float) Math.sin(pelimaailma.pelihahmo.getRunko().getAngle());
		// float autoY = pelimaailma.pelihahmo.getRunko().getPosition().y - 1.8f
		// * (float) Math.sin(pelimaailma.pelihahmo.getRunko().getAngle())
		// - 0.5f
		// * (float) Math.cos(pelimaailma.pelihahmo.getRunko().getAngle());

		// float autoX = pelimaailma.pelihahmo.getRunko().getPosition().x - 1.8f
		// * (float) Math.cos(pelimaailma.pelihahmo.getRunko().getAngle())
		// + 0.4f
		// * (float) Math.sin(pelimaailma.pelihahmo.getRunko().getAngle());
		// float autoY = pelimaailma.pelihahmo.getRunko().getPosition().y - 1.8f
		// * (float) Math.sin(pelimaailma.pelihahmo.getRunko().getAngle())
		// - 0.4f
		// * (float) Math.cos(pelimaailma.pelihahmo.getRunko().getAngle());
		//
		//
		// batch.draw(autoTexture, autoX, autoY, 0, 0, 3.2f, 1.6f, 1f, 1f,
		// pelimaailma.pelihahmo.getRunko().getAngle() * 57.296f);
//
//		piirraKallistunutNelikulmioOffSeteilla(autoTexture,
//				pelimaailma.pelihahmo.getRunko(), pelimaailma.pelihahmo
//						.getRunko().getAngle(), -1.8f, -0.4f, 3.2f, 1.6f);
		
		piirraKallistunutNelikulmioOffSeteilla(autoTexture,
				pelimaailma.pelihahmo.getRunko(), pelimaailma.pelihahmo
						.getRunko().getAngle(), -1.8f, -0.4f, 3.2f, 1.6f, 0, 0);

//		piirraKallistunutNelikulmioOffSeteilla(pupu2Texture,
//				pelimaailma.pelihahmo.getPupu2(), pelimaailma.pelihahmo
//						.getPupu2().getAngle(), 0.25f, 0, 0.62f, 1f, 0, 0);

		batch.draw(rengasTexture, pelimaailma.pelihahmo.getWheel1()
				.getPosition().x - 0.4f, pelimaailma.pelihahmo.getWheel1()
				.getPosition().y - 0.4f, 0.4f, 0.4f, 0.8f, 0.8f, 1f, 1f,
				pelimaailma.pelihahmo.getWheel1().getAngle() * 57.296f);
		batch.draw(rengasTexture, pelimaailma.pelihahmo.getWheel2()
				.getPosition().x - 0.4f, pelimaailma.pelihahmo.getWheel2()
				.getPosition().y - 0.4f, 0.4f, 0.4f, 0.8f, 0.8f, 1f, 1f,
				pelimaailma.pelihahmo.getWheel2().getAngle() * 57.296f);

	}

	private void piirraKallistunutNelikulmioOffSeteilla(TextureRegion texture,
			Body runko, float kulma, float offsetX, float offsetY,
			float leveys, float korkeus, float originX, float originY) {
		float autoX = pelimaailma.pelihahmo.getRunko().getPosition().x
				+ offsetX
				* (float) Math.cos(pelimaailma.pelihahmo.getRunko().getAngle())
				- offsetY
				* (float) Math.sin(pelimaailma.pelihahmo.getRunko().getAngle());
		float autoY = pelimaailma.pelihahmo.getRunko().getPosition().y
				+ offsetX
				* (float) Math.sin(pelimaailma.pelihahmo.getRunko().getAngle())
				+ offsetY
				* (float) Math.cos(pelimaailma.pelihahmo.getRunko().getAngle());

		batch.draw(texture, autoX, autoY, originX, originY, leveys, korkeus, 1f, 1f,
				kulma * 57.296f);

	}

	private void piirraKerattavat() {
		for (Vector2 v : pelimaailma.herneet) {
			batch.draw(herneTexture, v.x - 0.4f, v.y - 0.3f, 0.8f, 0.8f);
		}

		for (Vector2 v : pelimaailma.mansikat) {
			batch.draw(mansikkaTexture, v.x - 0.4f, v.y - 0.3f, 1.5f, 1.5f);
		}
	}
}
