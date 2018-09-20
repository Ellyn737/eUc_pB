package controller;

import javax.management.Query;

import org.apache.commons.collections.functors.InstantiateFactory;
import org.eclipse.osgi.internal.debug.Debug;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import models.Ausleiher;
import models.Bewertung;
import models.Buch;
import models.Media;
import models.MediumAusleihen;
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
	
	SessionFactory factory = new Configuration().configure("hibernate.cfg.remote.xml").addPackage("models").
			addAnnotatedClass(Media.class).addAnnotatedClass(Buch.class).
			addAnnotatedClass(Ausleiher.class).addAnnotatedClass(Bewertung.class).
			addAnnotatedClass(MediumAusleihen.class).buildSessionFactory();
	
	public void aufnehmenInBib() {
		/*
		 * neues Buch erstellen
		 * 		Parameter aus den textfeldern der View
		 * 
		 * Buch an DB uebergeben
		 */
		
		//SessionFactory holen
		try {
			//session starten
			Session session = factory.getCurrentSession();
			
		
			
			//use the session object to save/retrieve Java objects
			//create a media/buch object
			System.out.println("Create a media/buch object");
			Buch buch = new Buch();
			
			System.out.println("Autor: " + autor);
			buch.setAutor(autor);
			
			System.out.println("Verlag: " + verlag);
			buch.setVerlag(verlag);
			
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
			
			session.close();

		}catch (Exception e) {}
		
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
		
		//get buchData from buchId
		try {
			Session session = factory.getCurrentSession();
			session.beginTransaction();
			//aendere Daten
			Buch buch = (Buch)session.get(Buch.class, buchID);
		
			if(title != "") {
				buch.setTitle(title);
				System.out.println("neuer Titel: " + title);
			}
			
			if(genre != "") {
				buch.setGenre(genre);
				System.out.println("neues Genre: " + genre);
			}
			
			if(jahr != 0) {
				buch.setErscheinungsjahr(jahr);
				System.out.println("neues Erscheinungsjahr: " + jahr);
			}
			
			//inBIb hier nicht aendern
			//anpassen an BUCH (autor/ verlag fehlen)
			if(jahr == 0 && genre == "" && title == ""){
				System.out.println("Es wurde nichts verändert.");
			}
			
			if(autor != "") {
			buch.setAutor(autor);
			System.out.println("neuer Autor: " + autor);
			}
			
			if(verlag != "") {
				buch.setVerlag(verlag);
				System.out.println("neuer Verlag: " + verlag);
			}
			
			session.getTransaction().commit();
		}catch (Exception e) {
			System.out.println("Fehler!");

		}
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
	
	public int findeBuchID(String autorSuch, String titleSuch) {
		int buchID = 0;
		
		
		
		
		return buchID;
	}

	public Boolean getInBib() {
		return inBib;
	}

	public void setInBib(Boolean inBib) {
		this.inBib = inBib;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getVerlag() {
		return verlag;
	}

	public void setVerlag(String verlag) {
		this.verlag = verlag;
	}

	public int getJahr() {
		return jahr;
	}

	public void setJahr(int jahr) {
		this.jahr = jahr;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getInhalt() {
		return inhalt;
	}

	public void setInhalt(String inhalt) {
		this.inhalt = inhalt;
	}

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}
	
	
	
	
}
