package fi.ankkala.bunnyrace.fysiikka;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import fi.ankkala.bunnyrace.game.GameEventObserver;
import fi.ankkala.bunnyrace.game.Pelimaailma;
import fi.ankkala.bunnyrace.gui.Piirrettava;

public class Fysiikkamallinnus implements Piirrettava{

	private Box2DDebugRenderer debugRenderer;
	private double autonAsento;
	private Pelimaailma pelimaailma;
	private OrthographicCamera camera;
	private GameEventObserver peli;
	
	public void setEteen(boolean eteen) {
		this.pelimaailma.pelihahmo.eteen = eteen;
	}

	public void setTaakse(boolean taakse) {
		this.pelimaailma.pelihahmo.taakse = taakse;
	}

	public Fysiikkamallinnus(Pelimaailma pelimaailma, GameEventObserver peli) {
		this.pelimaailma = pelimaailma;
		this.debugRenderer = new Box2DDebugRenderer();
		this.peli = peli;
	}

	public boolean turboKaytossa() {
		return this.pelimaailma.pelihahmo.turboKaytossa();
	}

	public void setTurbo(boolean turbo) {
		this.pelimaailma.pelihahmo.setTurbo(turbo);
	}

	public boolean isEteen() {
		return this.pelimaailma.pelihahmo.eteen;
	}

	public boolean isTaakse() {
		return this.pelimaailma.pelihahmo.taakse;
	}

	public void destroy() {
		this.debugRenderer.dispose();
		this.debugRenderer = null;
		this.pelimaailma.destroy();
	}

	public void applyTorque(float torque) {
		this.pelimaailma.pelihahmo.getWheel1().applyTorque(torque, true);
	}

	public void step() {
		this.pelimaailma.world.step(1 / 60f, 6, 2);
	}
	
	public void liikutaPelihahmoa() {
		poimiKerattavat();
		ajaAutolla();
		lopetaPeliTarvittaessa();
	}

	private void lopetaPeliTarvittaessa() {
		//if (!this.pelimaailma.pelihahmo.getRunko().isAwake()) {
		if (this.pelimaailma.pelihahmo.getRunko().getLinearVelocity().len2()<0.0001f) {
			autonAsento = Math.abs(this.pelimaailma.pelihahmo.getRunko()
					.getAngle()) % (2 * Math.PI);
			if (autonAsento > Math.PI - 1.2) {
				if (autonAsento < Math.PI + 1.2) {
					this.peli.levelFailed();
				}
			}
		}
		
		if (this.pelimaailma.pelihahmo.getRunko().getPosition().x > this.pelimaailma.maali) {
			this.peli.levelComplete();
		}

		if (this.pelimaailma.pelihahmo.getRunko().getPosition().y < -5) {
			this.peli.levelFailed();
		}
	}
	
	public void setRajahdys(Vector2 sijainti) {
		this.pelimaailma.rajahdys = sijainti;
	}

	private void poimiKerattavat() {
		poimiHerneet();
		poimiMansikat();
		poimiPommit();
	}

	private void poimiHerneet() {
		for (int i = 0; i < pelimaailma.herneet.size(); i++) {
			if (Math.abs(pelimaailma.herneet.get(i).x
					- this.pelimaailma.pelihahmo.getRunko().getPosition().x) < 1.5f) {
				if (Math.abs(pelimaailma.herneet.get(i).y
						- this.pelimaailma.pelihahmo.getRunko().getPosition().y) < 1.5f) {
					peli.herne(pelimaailma.herneet.get(i));
					pelimaailma.herneet.remove(i);
					break;
				}
			}
		}
	}

	private void poimiMansikat() {
		for (int i = 0; i < pelimaailma.mansikat.size(); i++) {
			if (Math.abs(pelimaailma.mansikat.get(i).x
					- this.pelimaailma.pelihahmo.getRunko().getPosition().x) < 1.5f) {
				if (Math.abs(pelimaailma.mansikat.get(i).y
						- this.pelimaailma.pelihahmo.getRunko().getPosition().y) < 1.5f) {
					peli.mansikka(pelimaailma.mansikat.get(i));
					pelimaailma.mansikat.remove(i);
					break;
				}
			}
		}
	}

	private void poimiPommit() {
		for (int i = 0; i < pelimaailma.pommit.size(); i++) {
			if (Math.abs(pelimaailma.pommit.get(i).x
					- this.pelimaailma.pelihahmo.getRunko().getPosition().x) < 1.5f) {
				if (Math.abs(pelimaailma.pommit.get(i).y
						- this.pelimaailma.pelihahmo.getRunko().getPosition().y) < 1.5f) {
					peli.pommi(pelimaailma.pommit.get(i));

					pelimaailma.pommit.remove(i);
					this.pelimaailma.pelihahmo.getRunko().applyLinearImpulse(
							new Vector2(0, 15),
							this.pelimaailma.pelihahmo.getRunko()
									.getWorldCenter(), true);
					break;
				}
			}
		}
	}

	private void ajaAutolla() {
		this.pelimaailma.pelihahmo.ajaAutolla();
	}

	public void setDebugCamera(OrthographicCamera cam) {
		this.camera = cam;
	}

	public void piirra() {
		debugRenderer.render(this.pelimaailma.world, camera.combined);
	}

	@Override
	public void resize(int w, int h) {
		
	}

}
