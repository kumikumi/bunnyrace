package fi.ankkala.bunnyrace.fysiikka;

import com.badlogic.gdx.physics.box2d.Body;

public class FysiikkaAuto {
	private Body wheel1;
	private Body wheel2;
	private Body runko;
	
	private Body pupu1;
	private Body pupu2;
	
	private boolean turboKaytossa;
	public boolean eteen;
	public boolean taakse;

	public FysiikkaAuto(Body runko, Body wheel1, Body wheel2, Body pupu1, Body pupu2) {
		this.runko = runko;
		this.wheel1 = wheel1;
		this.wheel2 = wheel2;
		this.pupu1 = pupu1;
		this.pupu2 = pupu2;
	}
	
	public Body getPupu1() {
		return pupu1;
	}
	
	public Body getPupu2() {
		return pupu2;
	}

	public Body getWheel1() {
		return wheel1;
	}
	
	public boolean turboKaytossa() {
		return this.turboKaytossa;
	}
	
	public void setTurbo(boolean turbo) {
		this.turboKaytossa = turbo;
	}

	public Body getWheel2() {
		return wheel2;
	}

	public Body getRunko() {
		return runko;
	}

	public void ajaAutolla() {
		if (this.turboKaytossa) {
			this.wheel1.applyTorque(-10, true);
			this.wheel2.applyTorque(-10, true);
		}

		if (this.eteen) {

			if (this.runko.getAngularVelocity() < 3) {
				if (this.runko.getAngularVelocity() < 0) {
					this.runko.setAngularVelocity(this.runko
							.getAngularVelocity() * 0.8f);
				}
				this.runko.applyAngularImpulse(0.08f, true);
			}
			this.wheel2.applyTorque(-4, true);
			this.wheel1.applyTorque(-8, true);
		}

		if (this.taakse) {
			if (this.runko.getAngularVelocity() > -3) {
				if (this.runko.getAngularVelocity() > 0) {
					this.runko.setAngularVelocity(this.runko
							.getAngularVelocity() * 0.8f);
				}
				this.runko.applyAngularImpulse(-0.08f, true);
			}
			this.wheel2.applyTorque(4, true);
			this.wheel1.applyTorque(8, true);
		}
	}
}
