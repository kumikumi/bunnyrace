package fi.ankkala.bunnyrace.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PauseScreen implements Piirrettava{
	
	private SpriteBatch batch;
	private BitmapFont font;
	
	public PauseScreen() {
		this.batch = new SpriteBatch();
		
		this.font = new BitmapFont();
		this.font.setColor(1, 1, 1, 1);
	}

	@Override
	public void piirra() {
		// TODO Auto-generated method stub
		batch.begin();
		
		font.draw(batch, "GAME IS PAUSED", 100, 100);
		
		batch.end();
		
	}

	@Override
	public void resize(int w, int h) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
