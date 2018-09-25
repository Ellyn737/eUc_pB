package controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.util.Pair;
import models.Buch;



/**
 * 
 * @author ellyn
 *
 * Klasse zur Kontrolle der Aufnahme, des Löschens und Bearbeitens
 * von Buechern in der Bibliothek
 * 
 */
public class BibController {
	
	private Boolean ausgeliehen;
	private String ausgeliehenString;
	private String title;
	private String autor;
	private String verlag;
	private int jahr;
	private String jahrString;
	private String genre;
	private String inhalt;
	private String kommentar;
	private int exemplar;
	private String exemplarString;
	private int auflage;
	private String auflageString;
	
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
			
			System.out.println("inBib:" + ausgeliehen);
			buch.setAusgeliehen(ausgeliehen);			
			
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
	
	public List<Integer> findeBuchID(ArrayList<String> suchParameter) throws Exception {
		
		/*
		 * wird nur von SEARCHVIEW aufgerufen 
		 * (id sonst immer von anderer View weitergegeben)
		 * 
		 * gibt Liste mit Ids zurück
		 * 
		 * 
		 * switch case erstellen für verschiedene sql-query-Strings
		 * abhängig von den übergebenen Suchparametern
		 * 
		 * mögliche Suchparameter:
		 * 
		 * ausgeliehen / nicht
		 * Genre
		 * title
		 * autor
		 * verlag
		 * jahr 
		 * auflage
		 * exemplar
		 * 
		 * Sternebewertung (1-5)
		 * 
		 * 
		 * 
		 */
		
		System.out.println("In BC - findeBuchID");
		
//		diese Liste wird gefuellt und zurueckgegeben
		List<Integer> idPassend;

		
//		Suchparameter holen
		title = suchParameter.get(0);
		autor = suchParameter.get(1);
		verlag = suchParameter.get(2);
		jahrString = suchParameter.get(3);
		jahr = Integer.parseInt(suchParameter.get(3));
		genre = suchParameter.get(4);
		auflageString = suchParameter.get(5);
		auflage = Integer.parseInt(suchParameter.get(5));
		exemplarString = suchParameter.get(6);
		exemplar = Integer.parseInt(suchParameter.get(6));
		ausgeliehenString = suchParameter.get(7);
		ausgeliehen = Boolean.parseBoolean(suchParameter.get(7));
		
//		ueberblick ueber suchparameter
		System.out.println("Suchparameter sind: /ln Titel: " + title + ", Autor:  " + autor+ ", Verlag: " + verlag + ", Jahr: " + jahr 
				+ ", Genre: " + genre + ", Auflage: " + auflage + ", Exemplar: " + exemplar + ", ausgeliehen: " + ausgeliehen);

//		ArrayListe mit key und value anlegen
		ArrayList<Pair> parameter = new ArrayList<Pair>();
		
		if(title != "") {
			Pair titlePair = new Pair("title", title);
			parameter.add(titlePair);
		}
		
		if(autor != "") {
			Pair autorPair = new Pair("autor", autor);
			parameter.add(autorPair);
		}
		
		if(verlag != "") {
			Pair verlagPair = new Pair("verlag", verlag);
			parameter.add(verlagPair);		}
		
		if(jahrString != "") {
			Pair jahrPair = new Pair("jahr", jahrString);
			parameter.add(jahrPair);		}
		
		if(genre != "") {
			Pair genrePair = new Pair("genre", genre);
			parameter.add(genrePair);		}
		
		if(auflageString != "") {
			Pair auflagePair = new Pair("auflage", auflageString);
			parameter.add(auflagePair);		}
		
		if(exemplarString != "") {
			Pair exemplarPair = new Pair("exemplar", exemplarString);
			parameter.add(exemplarPair);
		}
		
		if(ausgeliehenString != "") {
			Pair ausgeliehenPair = new Pair("ausgeliehen", ausgeliehenString);
			parameter.add(ausgeliehenPair);
		}
		
//		factory holen und session erstellen
		factory = SingletonFactory.getFactory();
		Session findSession = factory.openSession();
		findSession.beginTransaction();
		
//		String und query erstellen zur Uebergabe der Parameter
		String hql = "select m.id_media from Media m where";
		Query query;
		
		
//		setze den hql-String je nach vorhandenen keys
		for(int i = 0; i < parameter.size(); i++) {
			String key = parameter.get(i).getKey().toString();
			switch(key) {
				case "title":
					hql += " m.title like ? ";
					break;
				case "autor":
					hql += " m.autor like ? ";
					break;			
				case "verlag":
					hql += " m.verlag like ? ";
					break;	
				case "jahr":
					hql += " m.jahr like ? ";
					break;
				case "genre":
					hql += " m.genre like ? ";
					break;
				case "auflage":
					hql += " m.auflage like ? ";
					break;
				case "exemplar":
					hql += " m.exemplar like ? ";
					break;
				case "ausgeliehen":
					hql += " m.ist_ausgeliehen like ? ";
					break;
			}
			if(i < parameter.size() -1) {
				hql += "and ";
			}
		}
		
		
//		uebergebe hql an query
		query = findSession.createQuery(hql);
		
		
//		setze parameter fuer query
		for(int i = 0; i < parameter.size(); i++) {
			String key = parameter.get(i).getKey().toString();
			switch(key) {
				case "title":
					query.setParameter(i, title);	
					break;
				case "autor":
					query.setParameter(i, autor);	
					break;			
				case "verlag":
					query.setParameter(i, verlag);	
					break;	
				case "jahr":
					query.setParameter(i, jahr);	
					break;
				case "genre":
					query.setParameter(i, genre);	
					break;
				case "auflage":
					query.setParameter(i, auflage);	
					break;
				case "exemplar":
					query.setParameter(i, exemplar);	
					break;
				case "ausgeliehen":
					query.setParameter(i, ausgeliehen);	
					break;
			}
		
		}
	
		
//		hole Ids
		idPassend = query.getResultList();
		
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
	public Boolean getAusgeliehen() {
		return ausgeliehen;
	}

	public void setAusgeliehen(Boolean ausgeliehen) {
		this.ausgeliehen = ausgeliehen;
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
