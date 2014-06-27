package fi.ankkala.bunnyrace.fileio;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import fi.ankkala.bunnyrace.auto.PorkkanaAuto;
import fi.ankkala.bunnyrace.auto.Rengas;
import fi.ankkala.bunnyrace.auto.renkaat.HyvaRengas;

public class MapLister {

	private MapLoader loader;
	private ArrayList<String> lista;
	private ArrayList<Integer> virheelliset;
	
	public MapLister() {
		this.lista = new ArrayList<String>();
		this.virheelliset = new ArrayList<Integer>();
	}

	public void loadList() {
		lista.removeAll(lista);
		virheelliset.removeAll(virheelliset);
		this.loader = new MapLoader();
		
		FileHandle file = Gdx.files.internal("data/maps/maplist.txt");

		//System.out.println(file.readString());
		Scanner sc = new Scanner(file.readString());
		
		int i = 0;
		
		Rengas perusRengas = new HyvaRengas();
		PorkkanaAuto porkkana = new PorkkanaAuto(perusRengas);
		//AutoMaarittely perusAuto = new AutoMaarittely(perusRengas, perusRengas);

		while (sc.hasNextLine()) {
			String rivi = sc.nextLine();
			
			try {
				loader.loadMap(rivi, porkkana).destroy();
			} catch (Exception e) {
				virheelliset.add(i);
			}
			lista.add(rivi);
			//System.out.println(rivi);
			i++;
		}
		sc.close();
		

//		if (Gdx.app.getType() == ApplicationType.Android) {
//			FileHandle file = Gdx.files.internal("data/maps/");
//			System.out.println("ANDROID");
//			FileHandle[] lista1 = file.list();
//
//			for (FileHandle fh : lista1) {
//				try {
//					//System.out.println(fh.name());
//					loader.loadMap(fh.name());
//
//				} catch (Exception e) {
//					continue;
//				}
//				lista.add(fh.name());
//				System.out.println(fh.name());
//			}
//
//		} else {
//			System.out.println("NOT ANDROID");
//			FileHandle file = Gdx.files.internal("data/maps/");
//
//			if (file == null) {
//				System.out.println("File == null !!!!");
//			} else {
//				System.out.println("File != null !!!!");
//			}
//			
//			String string1 = file.readString();
//			
//			if (string1 == null) {
//				System.out.println("String1 == null !!!!");
//			} else {
//				System.out.println("String1 != null !!!!");
//			}
//			
//			System.out.println(file.readString());
//			Scanner sc = new Scanner(file.readString());
//
//			while (sc.hasNextLine()) {
//				String rivi = sc.nextLine();
//
//				try {
//
//					loader.loadMap(rivi);
//
//				} catch (Exception e) {
//					continue;
//				}
//				lista.add(rivi);
//				System.out.println(rivi);
//			}
//			sc.close();

		}
		//
		// System.out.println(file.readString());
		//
		// System.out.println(Gdx.files.internal("data/maps/").readString());

		// FileHandle[] lista1 = file.list();
		// //
		// System.out.println(lista1.length);
		// //
		// for (FileHandle fh : lista1) {
		// System.out.println(fh.name());
		// }
		//

	

	public List<String> getList() {

		return lista;
	}
	
	public List<Integer> getVirheelliset() {
		return this.virheelliset;
	}

}
