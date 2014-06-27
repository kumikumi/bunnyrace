package fi.ankkala.bunnyrace.fileio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TextureLoader {
	
	private Texture texture1;
	private Texture tausta;
	private Animation explodeAnimation;

	public TextureRegion getAutoTexture() {
		return autoTexture;
	}

	public TextureRegion getRengasTexture() {
		return rengasTexture;
	}

	public TextureRegion getHerneTexture() {
		return herneTexture;
	}

	public TextureRegion getMansikkaTexture() {
		return mansikkaTexture;
	}

	public TextureRegion getPommiTexture() {
		return pommiTexture;
	}

	//TextureRegionit
	private TextureRegion autoTexture;
	private TextureRegion rengasTexture;
	private TextureRegion herneTexture;
	private TextureRegion mansikkaTexture;
	private TextureRegion pommiTexture;
	
	private TextureRegion pieniPupu;
	
	private TextureRegion rightArrow;
	private TextureRegion leftArrow;
	private TextureRegion rightArrow_pressed;
	private TextureRegion leftArrow_pressed;
	private TextureRegion endGameTexture;
	private TextureRegion turboTexture;
	private TextureRegion turboDot;
	
	
	public TextureRegion getTurboDot() {
		return turboDot;
	}
	
	public TextureRegion getPupu2() {
		return pieniPupu;
	}

	public Texture getTexture1() {
		return texture1;
	}

	public Texture getTausta() {
		return tausta;
	}

	public Animation getExplodeAnimation() {
		return explodeAnimation;
	}

	public TextureLoader() {
		this.texture1 = new Texture(Gdx.files.internal("data/texture1.png"));
		this.tausta = new Texture(Gdx.files.internal("data/tausta3.png"));
		this.pommiTexture = new TextureRegion(texture1, 512, 0, 128, 128);
		TextureRegion e1 = new TextureRegion(texture1, 640, 0, 128, 128);
		TextureRegion e2 = new TextureRegion(texture1, 768, 0, 128, 128);
		TextureRegion e3 = new TextureRegion(texture1, 896, 0, 128, 128);
		TextureRegion e4 = new TextureRegion(texture1, 512, 128, 128, 128);
		TextureRegion e5 = new TextureRegion(texture1, 640, 128, 128, 128);
		TextureRegion e6 = new TextureRegion(texture1, 512, 256, 128, 128);
		TextureRegion e7 = new TextureRegion(texture1, 0, 0, 0, 0);

		//this.explodeAnimation = new Animation(0.15f, e1, e2, e3, e4, e5);
		this.explodeAnimation = new Animation(0.10f, e1, e2, e3, e4, e5, e6, e7);
		
		this.autoTexture = new TextureRegion(texture1, 0, 0, 512, 256);
		this.rengasTexture = new TextureRegion(texture1, 0, 320, 128, 128);
		this.herneTexture = new TextureRegion(texture1, 0, 256, 64, 64);
		//this.mansikkaTexture = new TextureRegion(texture1, 128, 299, 77, 149);
		//this.mansikkaTexture = new TextureRegion(texture1, 128, 299, 77, 140);
		this.mansikkaTexture = new TextureRegion(texture1, 640, 256, 128, 128);
		
		this.pieniPupu = new TextureRegion(texture1, 205, 320, 79, 128);
		
		this.rightArrow = new TextureRegion(texture1, 768, 128, 128, 128);
		this.leftArrow = new TextureRegion(texture1, 896, 128, 128, 128);
		this.rightArrow_pressed = new TextureRegion(texture1, 768, 256, 128, 128);
		this.leftArrow_pressed = new TextureRegion(texture1, 896, 256, 128, 128);
		this.endGameTexture = new TextureRegion(texture1, 256, 256, 64, 64);
		this.turboTexture = new TextureRegion(texture1, 0, 448, 384, 64);
		this.turboDot = new TextureRegion(texture1, 0, 460, 1, 1);
	}

	public TextureRegion getTurboTexture() {
		return turboTexture;
	}

	public TextureRegion getEndGameTexture() {
		return endGameTexture;
	}

	public TextureRegion getRightArrow() {
		return rightArrow;
	}

	public TextureRegion getLeftArrow() {
		return leftArrow;
	}

	public TextureRegion getRightArrow_pressed() {
		return rightArrow_pressed;
	}

	public TextureRegion getLeftArrow_pressed() {
		return leftArrow_pressed;
	}
}
