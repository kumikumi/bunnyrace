package fi.ankkala.bunnyrace.auto.renkaat;


import fi.ankkala.bunnyrace.auto.Rengas;
import fi.ankkala.bunnyrace.auto.RengasTekstuuri;

public class LiukasRengas implements Rengas{

	@Override
	public RengasTekstuuri getTexture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHinta() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getRadius() {
		// TODO Auto-generated method stub
		return 0.4f;
	}

	@Override
	public float getDensity() {
		// TODO Auto-generated method stub
		return 0.3f;
	}

	@Override
	public float getFriction() {
		// TODO Auto-generated method stub
		return 0.1f;
	}

	@Override
	public float getRestitution() {
		// TODO Auto-generated method stub
		return 0.6f;
	}

	@Override
	public float getMaxTorque() {
		// TODO Auto-generated method stub
		return 1f;
	}

}
