package fi.ankkala.bunnyrace.game;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import fi.ankkala.bunnyrace.fysiikka.FysiikkaAuto;


public class Pelimaailma {
	public final String mapname;
	
	public final FysiikkaAuto pelihahmo;
	public final World world;
	public final Texture[] maisema;
	public final ArrayList<Vector2> herneet;
	public final ArrayList<Vector2> mansikat;
	public final ArrayList<Vector2> pommit;

	public final float skaala;
	public final float maali;
	public final int kuvienlkm;
	public final int kuvanleveys;
	
	public Vector2 rajahdys;
	

	
	public Pelimaailma(String mapname, float skaala, float maali, int kuvienlkm, int kuvanleveys, World world, FysiikkaAuto pelihahmo, Texture[] maisema, ArrayList<Vector2> herneet, ArrayList<Vector2> mansikat, ArrayList<Vector2> pommit) {
		this.mapname = mapname;
		this.skaala = skaala;
		this.maali = maali;
		this.kuvienlkm = kuvienlkm;
		this.kuvanleveys = kuvanleveys;
		this.world = world;
		this.pelihahmo = pelihahmo;
		this.maisema = maisema;
		this.herneet = herneet;
		this.mansikat = mansikat;
		this.pommit = pommit;
	}
	
	public void destroy() {
		for (int i = 0; i<maisema.length; i++) {
			maisema[i].dispose();
		}
	}

}
