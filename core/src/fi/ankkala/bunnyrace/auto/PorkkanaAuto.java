package fi.ankkala.bunnyrace.auto;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import fi.ankkala.bunnyrace.fysiikka.FysiikkaAuto;

public class PorkkanaAuto implements AutoMaarittely{
	private Rengas etuRengas;
	//private Rengas takaRengas;
	
	public PorkkanaAuto(Rengas rengas) {
		this.etuRengas = rengas;
	}
	
	public void setRengas(Rengas rengas) {
		this.etuRengas = rengas;
	}
	
	public Rengas getRengas() {
		return this.etuRengas;
	}

	@Override
	public FysiikkaAuto luoAuto(World world, float x, float y) {

		// First we create a body definition
		BodyDef wheel1def = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't
		// move we would set it to StaticBody
		wheel1def.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		wheel1def.position.set(x, y);

		// First we create a body definition
		BodyDef wheel2def = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't
		// move we would set it to StaticBody
		wheel2def.type = BodyType.DynamicBody;
		// Set our body's starting position in the world
		wheel2def.position.set(x + 2, y);

		// Create our body in the world using our body definition
		Body wheel1 = world.createBody(wheel1def);
		Body wheel2 = world.createBody(wheel2def);

		// Create a circle shape and set its radius
		CircleShape circle = new CircleShape();
		circle.setRadius(etuRengas.getRadius());

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		// fixtureDef.density = 0.4f;
		fixtureDef.density = etuRengas.getDensity();
		fixtureDef.friction = etuRengas.getFriction();
		fixtureDef.restitution = etuRengas.getRestitution(); // Make it bounce a little bit

		// Create our fixture and attach it to the body
		// Fixture fixture = wheel1.createFixture(fixtureDef);
		
		wheel1.createFixture(fixtureDef);
		wheel2.createFixture(fixtureDef);
		
//		wheel1.createFixture(automaarittely.getTakarengas().getFixtureDef());
//
//		wheel2.createFixture(automaarittely.getTakarengas().getFixtureDef());

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		circle.dispose();

		// DistanceJointDef jointDef = new DistanceJointDef();
		// jointDef.collideConnected = false;
		// jointDef.frequencyHz = 4.0f;
		// jointDef.initialize(wheel1, wheel2, wheel1.getPosition(),
		// wheel2.getPosition());
		// world.createJoint(jointDef);

		BodyDef runkodef = new BodyDef();

		runkodef.type = BodyType.DynamicBody;
		runkodef.position.set(x, y + 0.6f);
		//runkodef.position.set(x, y + 0.4f);

		Body runko = world.createBody(runkodef);

		PolygonShape rect = new PolygonShape();
		rect.setAsBox(1.3f, 0.3f);

		runko.createFixture(rect, 1f);

		rect.dispose();

		RevoluteJointDef rev1Def = new RevoluteJointDef();
		RevoluteJointDef rev2Def = new RevoluteJointDef();

		rev1Def.bodyA = runko;
		rev1Def.bodyB = wheel1;

		rev2Def.bodyA = runko;
		rev2Def.bodyB = wheel2;

		
		rev1Def.enableMotor = true;
		rev1Def.motorSpeed = 0.0f;
		rev1Def.maxMotorTorque = etuRengas.getMaxTorque();
		//TEST:
		rev1Def.collideConnected = false;

		rev2Def.enableMotor = true;
		rev2Def.motorSpeed = 0.0f;
		rev2Def.maxMotorTorque = etuRengas.getMaxTorque();
		
		//TEST:
		rev2Def.collideConnected = false;

		rev1Def.localAnchorA.x = -1f;
		rev1Def.localAnchorA.y = -0.4f;
		//rev1Dev.localAnchorA.y = -0.5f;

		rev1Def.localAnchorB.set(wheel1.getLocalCenter());

		rev2Def.localAnchorA.x = 1f;
		rev2Def.localAnchorA.y = -0.4f;
		rev2Def.localAnchorB.set(wheel2.getLocalCenter());

		world.createJoint(rev1Def);
		world.createJoint(rev2Def);
		
//		//Luodaan pupu1
//		
//		BodyDef pupu1def = new BodyDef();
//		
//		pupu1def.type = BodyType.DynamicBody;
//		pupu1def.position.set(x, y + 1f);
//		
//		Body pupu1 = world.createBody(pupu1def);
//		
//		PolygonShape rect1 = new PolygonShape();
//		rect1.setAsBox(0.3f, 0.3f);
//		
//
//		Fixture pupu1Fixture = pupu1.createFixture(rect1, 1f);
//		rect1.dispose();
//		
//		//System.out.println("pupu1 tiheys: " + pupu1Fixture.getDensity());
//		pupu1Fixture.setDensity(0.0001f);
//		pupu1.resetMassData();
//
//		
//		RevoluteJointDef revpupu1Dev = new RevoluteJointDef();
//		revpupu1Dev.bodyA = runko;
//		revpupu1Dev.bodyB = pupu1;
//		revpupu1Dev.collideConnected = false;
//		revpupu1Dev.enableMotor = false;
//		
//		revpupu1Dev.enableLimit = true;
//		revpupu1Dev.upperAngle = 0.3f;
//		revpupu1Dev.lowerAngle = -0.3f;
//		
//		revpupu1Dev.localAnchorA.x = -0.5f;
//		revpupu1Dev.localAnchorA.y = 0.1f;
//		
//		revpupu1Dev.localAnchorB.set(0, -0.3f);
//		
//		world.createJoint(revpupu1Dev);
//		
//		//Luodaan pupu2
//		
//		BodyDef pupu2def = new BodyDef();
//		
//		pupu2def.type = BodyType.DynamicBody;
//		pupu2def.position.set(x, y + 2f);
//		
//		Body pupu2 = world.createBody(pupu2def);
//		
//		PolygonShape rect2 = new PolygonShape();
//		rect2.setAsBox(0.3f, 0.3f);
//		
//
//		Fixture pupu2Fixture = pupu2.createFixture(rect2, 1f);
//		rect2.dispose();
//		
//		pupu2Fixture.setDensity(0.0001f);
//		pupu2.resetMassData();
//		
//		RevoluteJointDef revpupu2Dev = new RevoluteJointDef();
//		revpupu2Dev.bodyA = runko;
//		revpupu2Dev.bodyB = pupu2;
//		revpupu2Dev.collideConnected = false;
//		revpupu2Dev.enableMotor = false;
//		
//		revpupu2Dev.enableLimit = true;
//		revpupu2Dev.upperAngle = 0.3f;
//		revpupu2Dev.lowerAngle = -0.3f;
//		
//		revpupu2Dev.localAnchorA.x = 0.6f;
//		revpupu2Dev.localAnchorA.y = 0.1f;
//		
//		revpupu2Dev.localAnchorB.set(0, -0.3f);
//		
//		world.createJoint(revpupu2Dev);
		
		FysiikkaAuto a = new FysiikkaAuto(runko, wheel1, wheel2, null, null);
		return a;
	}

}
