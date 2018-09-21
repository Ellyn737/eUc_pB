package controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import models.Ausleiher;
import models.Bewertung;
import models.Buch;
import models.Media;
import models.MediumAusleihen;

public final class SingletonFactory {
	

	public static SessionFactory factoryReal;
	
	private SingletonFactory() {
		
	}
	
	public static synchronized SessionFactory getFactory() {
		if(factoryReal == null) {
			factoryReal = new Configuration().configure("hibernate.cfg.remote.xml").addPackage("models").
					addAnnotatedClass(Media.class).addAnnotatedClass(Buch.class).
					addAnnotatedClass(Ausleiher.class).addAnnotatedClass(Bewertung.class).
					addAnnotatedClass(MediumAusleihen.class).buildSessionFactory();
		}
		return factoryReal;
	}
	
}
