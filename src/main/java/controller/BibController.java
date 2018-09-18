package controller;

import org.hibernate.Session;

import models.Ausleiher;
import models.Buch;
import models.Media;
import view.AddNewTitleController;

/**
 * 
 * @author ellyn
 *
 * Klasse zur Kontrolle der Aufnahme, des Löschens und Bearbeitens
 * von Buechern in der Bibliothek
 * 
 */
public class BibController {

	public MainBibliothek mainBib = new MainBibliothek();
	public AddNewTitleController addTitleView = new AddNewTitleController();
	
	public Boolean inBib;
	public String title;
	public String autor;
	public String verlag;
	public int jahr;
	public String genre;
	public String inhalt;
	public String kommentar;
	
	
	public void aufnehmenInBib() {
		/*
		 * neues Buch erstellen
		 * 		Parameter aus den textfeldern der View
		 * 
		 * Buch an DB uebergeben
		 */
		
		//values holen
		inBib = addTitleView.getInBib();
		title = addTitleView.getTitle();
		autor = addTitleView.getAutor();
		verlag = addTitleView.getVerlag();
		jahr = addTitleView.getJahr();
		genre = addTitleView.getGenre();
		inhalt = addTitleView.getInhalt();
		kommentar = addTitleView.getKommentar();
		
		//SessionFactory holen
		
		//session starten
		Session session = factory.getCurrentSession();
		
		try {
			
			//use the session object to save/retrieve Java objects
			//create a media/buch object
			System.out.println("Create a media/buch object");
			Media buch = new Buch();
			
			System.out.println("Titel: " + title);
			buch.setTitle(title);
			
			System.out.println("Jahr: "+ jahr);
			buch.setErscheinungsjahr(jahr);
			
			System.out.println("Genre:" + genre);
			buch.setGenre(genre);
			
			System.out.println("inBib:" + inBib);
			buch.setIstInBib(inBib);
			
			
			//start transaction
			session.beginTransaction();
			
			//save the book
			System.out.println("Saving the book");
			session.save(buch);
			
			//commit the transaction
			session.getTransaction().commit();
			
			System.out.println("Done Fine");
			
		}finally {
			factory.close();
		}
		
	}
	
	public void loeschenAusBib(int buchID) {
		/* 
		 * Buch mit ID suchen und aus DB entfernen
		 * 
		 * AlarmFenster --> wirklich löschen?
		 * 		bestätigen lassen
		 * 
		 */
	}
	
	public void titelBearbeiten(int buchID) {
		/*
		 * Parameter der Txtfelder aus View vergleichen
		 * mit Parametern in DB
		 * --> Änderungen übernehmen
		 */
	}
	
	public boolean pruefenObInBestand(int buchID) {
		boolean isInBib = true;
		/*
		 * buch in DB suchen und istInBib überprüfen
		 * wenn da --> true
		 * wenn nicht da --> false
		 */
		
		return isInBib;
	}
	
	public int findeBuchID(String[] txtFelder) {
		int buchID = 0;
		/*
		 * Array auswerten und einzelnen Variablen zuordnen
		 * diese als Suchparameter in DB verwenden
		 * buchID zurückgeben
		 */
		
		return buchID;
	}
	
	
	
}
