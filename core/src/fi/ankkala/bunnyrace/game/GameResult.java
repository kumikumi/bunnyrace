package fi.ankkala.bunnyrace.game;

public class GameResult {
	public String tasonNimi;
	public long aikaMillisekunteina;
	public int herne_lkm;
	public int mansikka_lkm;
	public int pisteet;
	public boolean completed;
	
	public GameResult improve (GameResult newResult) {
		GameResult palautus = new GameResult();
		palautus.aikaMillisekunteina = Math.min(this.aikaMillisekunteina, newResult.aikaMillisekunteina);
		palautus.herne_lkm = Math.max(this.herne_lkm, newResult.herne_lkm);
		palautus.mansikka_lkm = Math.max(this.mansikka_lkm, newResult.mansikka_lkm);
		palautus.pisteet = Math.max(this.pisteet, newResult.pisteet);
		palautus.completed = this.completed && newResult.completed;
		return palautus;
	}
}
