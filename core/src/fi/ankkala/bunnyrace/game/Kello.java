package fi.ankkala.bunnyrace.game;

public class Kello {
	long aika_alussa;
	
	long aikamillisekunteina;
	long kymmenesosat;
	long sekunnit;
	long minuutit;
	
	String kymmenesosatTekstina;
	String sekunnitTekstina;
	String minuutitTekstina;
	
	public Kello() {
//		System.out.println("Kello luotiin");
		
	}
	
	public void kaynnista() {
//		System.out.println("Kello käynnistettiin");
		this.aika_alussa = System.currentTimeMillis();
	}
	
	public long getAikaMillisekunteina() {
//		System.out.println("Kellon aika millisekunteina tarkastettiin");
		return System.currentTimeMillis()-aika_alussa;
	}
	
	public String toString() {
//		System.out.println("Kellon ajalle pyydettiin tekstimuotoinen esitys");
		aikamillisekunteina = getAikaMillisekunteina();
		kymmenesosat = ((aikamillisekunteina - (aikamillisekunteina%10))/10) % 100;
		sekunnit = ((aikamillisekunteina - (aikamillisekunteina%1000))/1000) % 60;
		minuutit = ((aikamillisekunteina - (aikamillisekunteina%60000))/60000);
		
		if (kymmenesosat < 10) {
			kymmenesosatTekstina = "0"+kymmenesosat;
		} else {
			kymmenesosatTekstina = ""+kymmenesosat;
		}
		
		if (sekunnit < 10) {
			sekunnitTekstina = "0"+sekunnit;
		} else {
			sekunnitTekstina = ""+sekunnit;
		}
		
		if (minuutit < 10) {
			minuutitTekstina = "0"+minuutit;
		} else {
			minuutitTekstina = ""+minuutit;
		}
		//return minuutit+":"+sekunnit+":"+kymmenesosat;
		return minuutitTekstina+":"+sekunnitTekstina+":"+kymmenesosatTekstina;		
	}
}
