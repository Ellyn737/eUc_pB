package controller;

import java.util.List;

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
	
	private Boolean inBib;
	private String title;
	private String autor;
	private String verlag;
	private int jahr;
	private String genre;
	private String inhalt;
	private String kommentar;
	private int exemplar;
	private int auflage;
	
	private SessionFactory factory;

	
	public void aufnehmenInBib() {
		/*
		 * neues Buch erstellen
		 * 		Parameter aus den textfeldern der View
		 * 
		 * Buch an DB uebergeben
		 */
		
		//SessionFactory holen
		try {
			System.out.println("In BC");
			factory = SingletonFactory.getFactory();
			//session starten
			Session newTitleSession = factory.openSession();
			
			
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
			
			System.out.println("inhalt: " + inhalt);
			buch.setInhalt(inhalt);

			System.out.println("kommentar: "+ kommentar);
			buch.setKommentar(kommentar);
			
			System.out.println("auflage: " + auflage);
			buch.setAuflage(auflage);
			
			//herausfinden, ob es den Titel mit dem Autoren und der Auflage schon gibt
			int anzahlAndererEx = suchenNachAnderenExemplaren(autor, title, auflage);
			
			// wenn ja, das neue Exemplar mit nächst höherer Exemplarzahl austatten
			if(anzahlAndererEx > 0) {
				exemplar = anzahlAndererEx++;
			
			}else {
				//wenn es das erste Exemplar ist --> mit 1 ausstatten
				exemplar = 1;
			}
			
			System.out.println("anzahle der Exemplare: " + exemplar);
			buch.setExemplar(exemplar);
			
			//start transaction
			System.out.println("Beginn transaction");
			newTitleSession.beginTransaction();
			
			//save the book
			System.out.println("Saving the book");
			newTitleSession.save(buch);
			
			//commit the transaction
			System.out.println("Commiting");
			newTitleSession.getTransaction().commit();
			
			System.out.println("Done Fine");
			newTitleSession.close();

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
		
		
		//uebergebe aenderungen bei WHERE ID = buchID
		try {
			System.out.println("In BC");
			factory = SingletonFactory.getFactory();
			Session changeSession = factory.openSession();
			
			
			//aendere Daten
			Buch buch = (Buch)changeSession.get(Buch.class, buchID);
		
			if(!title.equals(buch.getTitle())) {
				buch.setTitle(title);
				System.out.println("neuer Titel: " + title);
			}
			
			if(!genre.equals(buch.getGenre())) {
				buch.setGenre(genre);
				System.out.println("neues Genre: " + genre);
			}
			
			if(jahr != buch.getErscheinungsjahr()) {
				buch.setErscheinungsjahr(jahr);
				System.out.println("neues Erscheinungsjahr: " + jahr);
			}
			
			if(!autor.equals(buch.getAutor())) {
				buch.setAutor(autor);
				System.out.println("neuer Autor: " + autor);
				}
				
			if(!verlag.equals(buch.getVerlag())) {
				buch.setVerlag(verlag);
				System.out.println("neuer Verlag: " + verlag);
			}
			
			if(inhalt != "") {
				buch.setInhalt(inhalt);
				System.out.println("neuer Inhalt: " + inhalt);
			}
			
			if(kommentar != "") {
				buch.setKommentar(kommentar);
				System.out.println("neuer Kommentar: " + kommentar);
			}
			
			if(auflage != buch.getAuflage()) {
				buch.setAuflage(auflage);
				System.out.println("andere Auflage: " + auflage);
			}
			
			
			changeSession.beginTransaction();
			
			changeSession.update(buch);
			
			changeSession.getTransaction().commit();
			
			changeSession.close();
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
	
	public List<Integer> findeBuchID(String autorSuch, String titleSuch) throws Exception {
		
		/*
		 * wird nur von SEARCHVIEW aufgerufen 
		 * (sonst immer von anderer View weitergegeben)
		 * 
		 * gibt Liste mit Ids zurück
		 */
		
		
			System.out.println("In BC - findeBuchID");
			factory = SingletonFactory.getFactory();
			Session findSession = factory.openSession();
			
			//holt die id der Buecher mit diesem Titel und Autor
			List<Integer> idPassend = findSession.createQuery("select m.id_media from Media m where m.title=? and m.autor=?")
					.setParameter(0, titleSuch).setParameter(1, autorSuch).list();
		
			findSession.beginTransaction();
			
			findSession.getTransaction().commit();
			
			findSession.close();
			
			return idPassend;
		
	}
	
	public int suchenNachAnderenExemplaren(String autorSuche, String titleSuche, int auflageSuche) {
		int anzahlAndererExemplare = 0;
		
		try {
			System.out.println("In BC - suchenNachAnderenExemplaren");
			factory = SingletonFactory.getFactory();
			Session findSession = factory.openSession();
			
			//holt die id der Buecher mit diesem Titel und Autor
			List<Integer> passendeIds = findSession.createQuery("select m.id_media from Media m "
					+ "where m.title like ? and m.autor like ? and m.auflage like ?")
					.setParameter(0, titleSuche)
					.setParameter(1, autorSuche)
					.setParameter(2, auflageSuche).list();
			
			findSession.beginTransaction();
			
			findSession.getTransaction().commit();
					
			
			//zaehlen der ids
			for(int i: passendeIds) {
				anzahlAndererExemplare++;
			}
			
			findSession.close();
		}catch(Exception e) {}
		
		return anzahlAndererExemplare;
	}
	

	//Getter, Setter und ToString
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

	public int getExemplar() {
		return exemplar;
	}

	public void setExemplar(int exemplar) {
		this.exemplar = exemplar;
	}

	public int getAuflage() {
		return auflage;
	}

	public void setAuflage(int auflage) {
		this.auflage = auflage;
	}
	
	
	
	
}
