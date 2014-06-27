package fi.ankkala.bunnyrace.game;

public interface PelinOhjaus {
	int getPisteet();
	String getKellonAika();
	int getTurbo();
	void setEteen(boolean eteen);
	void setTaakse(boolean taakse);
	boolean isEteen();
	boolean isTaakse();
	void kaytaTurbo();
	void toggleDebug();
	void togglePause();
}
