package fi.ankkala.bunnyrace.fileio;

import java.util.ArrayList;
import java.util.Scanner;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import fi.ankkala.bunnyrace.auto.AutoMaarittely;
import fi.ankkala.bunnyrace.game.Pelimaailma;

public class MapLoader {
	
	//Polku tason juureen
	private FileHandle rootPath;

	// Uuden tason nimi
	//private String mapname;

	// Kenttäkohtaisia parametrejä
	private int kuvienlkm;
	private float skaala;
	private float maali;
	private int kuvanleveys;
	private float autoX;
	private float autoY;


	public MapLoader() {

	}

//	public String getMapName() {
//		return this.mapname;
//	}

	public Pelimaailma loadMap(String mapname, AutoMaarittely auto) throws Exception {
		//this.mapname = mapname;
		this.rootPath = AssetLoader.load("maps/" + mapname);
		
		lueParametrit();
		
		World world = new World(new Vector2(0, -10), true);
		lataaPinnanmuodot(world);
	
		return new Pelimaailma(mapname, skaala, maali, this.kuvienlkm, this.kuvanleveys, world, auto.luoAuto(world, autoX, autoY),
				lataaMaisema(), lataaHerneet(), lataaMansikat(), lataaPommit());
	}

	private void lueParametrit() throws Exception {
		this.maali = -1;
		FileHandle file = rootPath.child("map.txt");
		
		

		Scanner sc = new Scanner(file.readString());

		while (sc.hasNextLine()) {
			String rivi = sc.nextLine();

			if (rivi.startsWith("kuvia:")) {
				this.kuvienlkm = Integer.parseInt(rivi.substring(6));
				// System.out.println("Pituus on " + pituus);
			} else if (rivi.startsWith("skaala:")) {
				this.skaala = Float.parseFloat(rivi.substring(7));
				// System.out.println("Skaala on " + skaala);

			} else if (rivi.startsWith("kuvanleveys:")) {
				this.kuvanleveys = Integer.parseInt(rivi.substring(12));
			} else if (rivi.startsWith("auto:")) {
				rivi = rivi.substring(5);
				// System.out.println("Rivi: " + rivi);
				String[] luvut = rivi.split(";");
				this.autoX = Float.parseFloat(luvut[0]) * this.skaala
						/ kuvanleveys;
				this.autoY = (1024 - Float.parseFloat(luvut[1])) * this.skaala
						/ kuvanleveys;
			} else if (rivi.startsWith("maali:")) {
				rivi = rivi.substring(6);
				this.maali = Float.parseFloat(rivi);
			}
		}
		
		if (this.maali == -1) {
			this.maali = this.skaala-3;
		}

		sc.close();
	}


	private ArrayList<Vector2> lataaHerneet() throws Exception {
		ArrayList<Vector2> herneet = new ArrayList<Vector2>();
		FileHandle file = rootPath.child("herneet.txt");
		
		Scanner sc = new Scanner(file.readString());
		String rivi;
		String[] luvut;
		float herneX;
		float herneY;

		while (sc.hasNextLine()) {
			rivi = sc.nextLine();
			try {
				luvut = rivi.split(";");
				herneX = Float.parseFloat(luvut[0]) * this.skaala / kuvanleveys;
				herneY = ((1024 - Float.parseFloat(luvut[1])) * this.skaala)
						/ kuvanleveys;
				// System.out.println("HerneX : " + herneX + " ja HerneY: " +
				// herneY);
				herneet.add(new Vector2(herneX, herneY));
			} catch (Exception e) {

			}
		}
		sc.close();
		return herneet;
	}

	private ArrayList<Vector2> lataaMansikat() throws Exception {
		ArrayList<Vector2> mansikat = new ArrayList<Vector2>();
		FileHandle file = rootPath.child("mansikat.txt");
		Scanner sc = new Scanner(file.readString());
		String rivi;
		String[] luvut;
		float mansikkaX;
		float mansikkaY;

		while (sc.hasNextLine()) {
			rivi = sc.nextLine();
			try {
				luvut = rivi.split(";");
				mansikkaX = Float.parseFloat(luvut[0]) * this.skaala
						/ kuvanleveys;
				mansikkaY = ((1024 - Float.parseFloat(luvut[1])) * this.skaala)
						/ kuvanleveys;
				// System.out.println("MansikkaX : " + mansikkaX +
				// " ja MansikkaY: " + mansikkaY);
				mansikat.add(new Vector2(mansikkaX, mansikkaY));
			} catch (Exception e) {

			}
		}
		sc.close();
		return mansikat;

	}

	private ArrayList<Vector2> lataaPommit() throws Exception {
		ArrayList<Vector2> pommit = new ArrayList<Vector2>();
		FileHandle file = rootPath.child("pommit.txt");
		Scanner sc = new Scanner(file.readString());
		String rivi;
		String[] luvut;
		float pommiX;
		float pommiY;

		while (sc.hasNextLine()) {
			rivi = sc.nextLine();
			try {
				luvut = rivi.split(";");
				pommiX = Float.parseFloat(luvut[0]) * this.skaala / kuvanleveys;
				pommiY = ((1024 - Float.parseFloat(luvut[1])) * this.skaala)
						/ kuvanleveys;
				// System.out.println("PommiX : " + pommiX + " ja PommiY: " +
				// pommiY);
				pommit.add(new Vector2(pommiX, pommiY));
			} catch (Exception e) {

			}
		}
		sc.close();
		return pommit;
	}

	private void lataaPinnanmuodot(World world) {
		// 0. Create a loader for the file saved from the editor.
		CustomBodyEditorLoader loader = new CustomBodyEditorLoader(
				rootPath.child("collisionmap.json"));
		// BodyEditorLoader loader = new
		// BodyEditorLoader(Gdx.files.internal("data/maps/mission1/mission1.json"));
		// Create our body definition
		BodyDef groundBodyDef = new BodyDef();
		// Set its world position
		groundBodyDef.position.set(0, 0);
		groundBodyDef.type = BodyType.StaticBody;

		// Create a body from the definition and add it to the world
		Body groundBody = world.createBody(groundBodyDef);

		FixtureDef fd = new FixtureDef();
		fd.density = 1;
		fd.friction = 0.5f;
		fd.restitution = 0.3f;

		loader.attachFixture(groundBody, "Name", fd, skaala);
	}

	private Texture[] lataaMaisema() {
		Texture[] palautus = new Texture[this.kuvienlkm];
		FileHandle imgHakemisto = rootPath.child("img");
		String alkuosa = "img_";
		String loppuosa = ".png";

		for (int i = 0; i < kuvienlkm; i++) {
			if (i < 10) {
				palautus[i] = new Texture(imgHakemisto.child(alkuosa + "0" + i
						+ loppuosa));
			} else {
				palautus[i] = new Texture(imgHakemisto.child(alkuosa + i
						+ loppuosa));
			}
		}
		return palautus;
	}
}
