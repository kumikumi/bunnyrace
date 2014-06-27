package fi.ankkala.bunnyrace.auto;

import com.badlogic.gdx.physics.box2d.World;

import fi.ankkala.bunnyrace.fysiikka.FysiikkaAuto;

public interface AutoMaarittely {
	public FysiikkaAuto luoAuto(World world, float x, float y);
}
