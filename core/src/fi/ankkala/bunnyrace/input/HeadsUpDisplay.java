package fi.ankkala.bunnyrace.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fi.ankkala.bunnyrace.GameControl;
import fi.ankkala.bunnyrace.fileio.TextureLoader;
import fi.ankkala.bunnyrace.game.Pelimaailma;
import fi.ankkala.bunnyrace.game.PelinOhjaus;
import fi.ankkala.bunnyrace.gui.Piirrettava;

public class HeadsUpDisplay implements InputProcessor, Piirrettava {
	private GameControl gameControl;
	private OrthographicCamera gameCamera;
	private PelinOhjaus peli;

	// Drawing

	private TextureLoader textureLoader;
	private SpriteBatch batch;
	private TextureRegion right;
	private TextureRegion left;
	private TextureRegion right_pressed;
	private TextureRegion left_pressed;

	private TextureRegion endGameTexture;
	private TextureRegion turboTexture;
	private TextureRegion turboDot;

	private float nuolenKoko;
	private float uiOffset;
	private float turbonEtaisyysReunasta;
	private float turboPalkinMaxPituus;

	// Touch
	private boolean isTouchDown;
	private boolean isMultiTouchDown;

	private boolean endGamePressed;

	public int begin_x;
	public int begin_y;

	private int pointer1;
	public int cur_x;
	public int cur_y;

	private int pointer2;
	public int cur2_x;
	public int cur2_y;

	public double begin_dist;
	public double cur_dist;

	private int width;
	private int height;

	private float begin_zoom;

	public HeadsUpDisplay(GameControl br, TextureLoader textureLoader,
			Pelimaailma pelimaailma, OrthographicCamera camera, PelinOhjaus peli) {
		this.gameControl = br;
		this.textureLoader = textureLoader;
		this.gameCamera = camera;
		this.peli = peli;

		this.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		this.loadTextures();

	}

	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		this.nuolenKoko = this.height / 5;
		this.uiOffset = this.nuolenKoko / 3;
		this.batch = new SpriteBatch();
		this.turbonEtaisyysReunasta = this.nuolenKoko + this.uiOffset * 2;
		this.turboPalkinMaxPituus = this.width - this.turbonEtaisyysReunasta
				- (this.nuolenKoko * 3 + this.uiOffset * 1.8f);
	}

	private void loadTextures() {
		this.left = textureLoader.getLeftArrow();
		this.right = textureLoader.getRightArrow();
		this.left_pressed = textureLoader.getLeftArrow_pressed();
		this.right_pressed = textureLoader.getRightArrow_pressed();
		this.endGameTexture = textureLoader.getEndGameTexture();
		this.turboTexture = textureLoader.getTurboTexture();
		this.turboDot = textureLoader.getTurboDot();
	}

	public void destroy() {
		this.gameControl = null;
		this.gameCamera = null;
		// this.fysiikka = null;
	}

	public void piirra() {
		batch.begin();

		batch.draw(endGameTexture, this.width - nuolenKoko - uiOffset,
				this.height - nuolenKoko - uiOffset, nuolenKoko, nuolenKoko);

		if (this.peli.isTaakse()) {
			batch.draw(left_pressed, uiOffset, uiOffset, nuolenKoko, nuolenKoko);
		} else {
			batch.draw(left, uiOffset, uiOffset, nuolenKoko, nuolenKoko);
		}

		if (this.peli.isEteen()) {
			batch.draw(right_pressed, this.width - nuolenKoko - uiOffset,
					uiOffset, nuolenKoko, nuolenKoko);
		} else {
			batch.draw(right, this.width - nuolenKoko - uiOffset, uiOffset,
					nuolenKoko, nuolenKoko);
		}

		batch.draw(turboTexture, this.turbonEtaisyysReunasta,
				this.uiOffset * 2, this.nuolenKoko * 2, this.nuolenKoko / 3);
		// batch.draw(turboDot, this.nuolenKoko*3+this.uiOffset*1.8f,
		// this.uiOffset*2, 0.2f*this.uiOffset*peli.turbo+1f,
		// this.nuolenKoko/3);
		// turbonEtaisyysReunasta = nuolenKoko + uiOffset*2
		batch.draw(turboDot, this.nuolenKoko * 3 + this.uiOffset * 1.8f,
				this.uiOffset * 2,
				this.turboPalkinMaxPituus * this.peli.getTurbo() / 400 + 1,
				this.nuolenKoko / 3);

		batch.end();

	}

	@Override
	public boolean keyDown(int keycode) {
		// System.out.println("Keycode:" + keycode);

		// TODO Auto-generated method stub
		// 22 on oikea
		// 21 on vasen

		if (this.gameControl == null) {
			System.out.println("gamecontrol on null!");
			return false;
		}

		if (keycode == 22) {
			this.peli.setEteen(true);
		} else if (keycode == 21) {
			this.peli.setTaakse(true);
		} else if (keycode == 62) {
			this.peli.kaytaTurbo();
		} else if (keycode == 131) {
			Gdx.input.setInputProcessor(null);
			gameControl.goToLevelSelection();
		} else if (keycode == 8) {
			Gdx.input.setInputProcessor(null);
			gameControl.loadMap("mission1_uusi");
		} else if (keycode == 9) {
			Gdx.input.setInputProcessor(null);
			gameControl.loadMap("mission2_uusi");
		} else if (keycode == 45) {
			peli.toggleDebug();
		} else if (keycode == 44) {
			peli.togglePause();
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		if (this.peli == null) {
			return false;
		}
		if (keycode == 22) {
			this.peli.setEteen(false);
		}
		if (keycode == 21) {
			this.peli.setTaakse(false);
		}
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
		// System.out.println("Touchdown: (" + screenX + ", " + screenY + ")");

		if (this.isTouchDown) {
			if (!this.isMultiTouchDown) {
				this.isMultiTouchDown = true;

				begin_zoom = gameCamera.zoom;
				// begin_cam_x = camera.position.x;
				// begin_cam_y = camera.position.y;

				// width = Gdx.graphics.getWidth();
				// height = Gdx.graphics.getHeight();

				pointer2 = pointer;
				cur2_x = screenX;
				cur2_y = screenY;

				begin_dist = Math.max(
						Math.sqrt((cur2_x - cur_x) * (cur2_x - cur_x)
								+ (cur2_y - cur_y) * (cur2_y - cur_y)), 50);
				// begin_mid_x = (cur_x + cur2_x) / 2;
				// begin_mid_y = (cur_y + cur2_y) / 2;
				// cur_mid_x = begin_mid_x;
				// cur_mid_y = begin_mid_y;
				cur_dist = begin_dist;
			}
		} else {
			this.isTouchDown = true;
			pointer1 = pointer;
			begin_x = screenX;
			begin_y = screenY;
			cur_x = screenX;
			cur_y = screenY;

			if (screenY > 3 * height / 4) {

				if (screenX > this.nuolenKoko + this.uiOffset * 2
						&& screenX < this.width - this.nuolenKoko
								- this.uiOffset * 2) {
					this.peli.kaytaTurbo();
				} else if (screenX < this.width / 2) {
					this.peli.setTaakse(true);
				} else {
					this.peli.setEteen(true);
				}
			} else if (screenY < this.uiOffset + this.nuolenKoko) {
				if (screenX > this.width - this.uiOffset - this.nuolenKoko) {
					this.endGamePressed = true;
				}
			}

		}

		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		this.peli.setEteen(false);
		this.peli.setTaakse(false);
		this.isTouchDown = false;
		this.isMultiTouchDown = false;

		if (screenY < this.uiOffset + this.nuolenKoko) {
			if (screenX > this.width - this.uiOffset - this.nuolenKoko) {
				if (this.endGamePressed == true) {
					Gdx.input.setInputProcessor(null);
					gameControl.goToLevelSelection();
				}
			}
		} else {
			this.endGamePressed = false;
		}

		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		if (pointer == pointer1) {
			cur_x = screenX;
			cur_y = screenY;
		} else if (pointer == pointer2) {
			cur2_x = screenX;
			cur2_y = screenY;
		}

		if (this.isMultiTouchDown) {
			cur_dist = Math.sqrt((cur2_x - cur_x) * (cur2_x - cur_x)
					+ (cur2_y - cur_y) * (cur2_y - cur_y));
			// cur_mid_x = (cur_x + cur2_x) / 2;
			// cur_mid_y = (cur_y + cur2_y) / 2;

			gameCamera.zoom = (float) (begin_zoom / (cur_dist / begin_dist));
		}

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
		switch (amount) {
		case -1:
			gameCamera.zoom -= 0.05f;
			break;
		case 1:
			gameCamera.zoom += 0.05f;
			break;
		}
		return false;
	}

}
