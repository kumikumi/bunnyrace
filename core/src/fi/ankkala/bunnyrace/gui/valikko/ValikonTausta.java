package fi.ankkala.bunnyrace.gui.valikko;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import fi.ankkala.bunnyrace.fileio.AssetLoader;
import fi.ankkala.bunnyrace.gui.Piirrettava;

public class ValikonTausta implements Piirrettava{
	private SpriteBatch batch;
	private Texture tausta;
	private Texture pilvet;
	private int leveys;
	private int korkeus;
	private double pilvienSuhteellinenPaikka;
	private int pilvenLeveys;
	private int pilvenKorkeus;
	private TextureRegion auringonKeskiosa;
	private TextureRegion auringonSateet;
	private float auringonKorkeus;
	private float auringonLeveys;
	private float auringonPaikkaX;
	private float auringonPaikkaY;
	
//	private float skaala;
//	private float ylaPalkinKorkeus;
	
	public ValikonTausta() {
		this.batch = new SpriteBatch();
		this.tausta = new Texture(AssetLoader.load("valikontausta.png"));
		this.pilvet = new Texture(AssetLoader.load("pilvet.png"));
		Texture aurinko = new Texture(AssetLoader.load("aurinko.png"));
		this.auringonKeskiosa = new TextureRegion(aurinko, 0, 0, aurinko.getWidth()/2, aurinko.getHeight());
		this.auringonSateet = new TextureRegion(aurinko, aurinko.getWidth()/2, 0, aurinko.getWidth()/2, aurinko.getHeight());
//		this.auringonKeskiosa = new TextureRegion(aurinko, 0, 0, 68, 61);
//		this.auringonSateet = new TextureRegion(aurinko, 68, 0, 68, 61);
		//TextureRegion e1 = new TextureRegion(texture1, 640, 0, 128, 128);
		
		this.leveys = Gdx.graphics.getWidth();
		this.korkeus = Gdx.graphics.getHeight();
		this.resize(leveys, korkeus);
	}
	
	@Override
	public void piirra() {
		batch.begin();

		batch.draw(tausta, 0, 0, leveys, korkeus, 0, 1, 1, 0);
		
		batch.draw(auringonKeskiosa, auringonPaikkaX, auringonPaikkaY, 0, 0, auringonLeveys, auringonKorkeus, 1f, 1f, 0f);
		batch.draw(auringonSateet, auringonPaikkaX, auringonPaikkaY, auringonLeveys/2, auringonKorkeus/2, auringonLeveys, auringonKorkeus, 1f, 1f, 20*(float)Math.sin((double)System.currentTimeMillis()/1200));

		pilvienSuhteellinenPaikka = ((System.currentTimeMillis() % 20000)/20000.0);

		for (int pilviX = (int)(pilvenLeveys*pilvienSuhteellinenPaikka-pilvenLeveys); pilviX<leveys; pilviX+= pilvenLeveys) {
			batch.draw(pilvet, pilviX, (int)(korkeus*0.8) - pilvenKorkeus, pilvenLeveys, pilvenKorkeus, 0, 1, 1, 0);
		}
		batch.end();
	}

	@Override
	public void resize(int w, int h) {
		this.leveys = w;
		this.korkeus = h;

		this.batch = new SpriteBatch();
		
		this.auringonLeveys = 68f*this.leveys/ 408;
		//this.auringonKorkeus = 61f*this.korkeus / 245;
		this.auringonKorkeus = 61f*this.auringonLeveys/68;
		this.auringonPaikkaX = 332f*this.leveys/408;
		this.auringonPaikkaY = 140*this.korkeus/245;
		
//		this.ylaPalkinKorkeus = (int) (0.0931 * leveys);

//		skaala = Math.min((float) (w - 50) / 590,
//				(float) (h - 50 - ylaPalkinKorkeus) / 354f);
		
//		this.pilvenLeveys = (int)(0.6*leveys);
//		this.pilvenKorkeus = (int)(0.19*leveys);
		this.pilvenLeveys = (int)(0.8*korkeus);
//		this.pilvenLeveys = (int)(500f*skaala);
		this.pilvenKorkeus = (int)(0.3167*pilvenLeveys);
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
