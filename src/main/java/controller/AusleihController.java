package controller;

/**
 * 
 * @author ellyn
 *
 * Klasse zur Kontrolle der Ausleihen und Rückgaben
 * 
 */
public class AusleihController {

	public void buchAusleihen(int buchID, int ausleiherID) {
		/*
		 * sucht das Buch heraus und 
		 * ordnet in DB den ausleiher dem Buch zu
		 * aendert istInBib auf false
		 */
		
	}
	
	public void buchZurueck(int buchID) {
		/*
		 * sucht das Buch in der DB
		 * loescht ausleiherID aus Buch
		 * aendert istInBib auf true
		 */
	}
	
	public int findeAusleiherVonBuch(int buchID) {
		int ausleiherID = 0;
		/*
		 * sucht nach Buch mit buchID
		 * holt den Wert von ausleiher
		 * (ueberprueft, ob istInBib auf falsch ist)
		 * 
		 */
		
		return ausleiherID;
	}
}
