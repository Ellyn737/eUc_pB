package demo;

import java.sql.Connection;
import java.sql.DriverManager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import models.Ausleiher;
import models.Bewertung;
import models.Buch;
import models.Media;
import models.MediumAusleihen;

public class TestConnection {
	
	
	public static void main (String [] args) {
		TestConnection connection = new TestConnection();
		//connection.testConnectionWithoutConfigFile("ellynhb");
		connection.testConnection();
	}
	
	private void testConnection() {
		
		System.out.println("Connecting to database");
		//wird nur einmal erstellt
		SessionFactory factory = new Configuration().configure("hibernate.cfg.remote.xml").addPackage("models").
				addAnnotatedClass(Media.class).addAnnotatedClass(Buch.class).
				addAnnotatedClass(Ausleiher.class).addAnnotatedClass(Bewertung.class).
				addAnnotatedClass(MediumAusleihen.class).buildSessionFactory();
		
		
		Session session = factory.getCurrentSession();
		
		try {
			
			//use the session object to save/retrieve Java objects
			//create a media/buch object
			System.out.println("Create a media/buch object");
			Ausleiher tempAusleiher = new Ausleiher();
			System.out.println("VorName");
			tempAusleiher.setVorName("Ellyn");
			System.out.println("Nachname");
			tempAusleiher.setNachName("Reese-Bünning");
			System.out.println("Email");
			tempAusleiher.setEmail("e.reese@mail.de");
			
			//start transaction
			session.beginTransaction();
			
			//save the book
			System.out.println("Saving the book");
			session.save(tempAusleiher);
			
			//commit the transaction
			session.getTransaction().commit();
			
			System.out.println("Done Fine");
			
		}finally {
			factory.close();
		}
				
		/*
		Session session = factory.getCurrentSession();

		System.out.println("Connection successful");
		System.out.println("Begin transaction");
		
		Transaction transA = session.beginTransaction();

		Media m1 = new Buch("Das Lächeln der Fortuna", "Belletristik", 1999, "Rebecca Gable", "Lübbe");
		session.persist(m1);

		session.getTransaction().commit();
		System.out.println("End transaction");
		session.close();
		System.out.println("Success");

	*/
		
/*
		System.out.println("Connecting to database");
		
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
		Metadata meta = new MetadataSources(ssr).getMetadataBuilder().build();
		
		SessionFactory factory = meta.getSessionFactoryBuilder().build();
		Session session = factory.openSession();
		
		Transaction t = session.beginTransaction();
		
			Media m1 = new Buch("Das Lächeln der Fortuna", "Belletristik", 1999, "Rebecca Gable", "Lübbe");
			
			session.persist(m1);
			t.commit();
			session.close();
			System.out.println("Success");
			
			*/
	}
	
	
private void testConnectionWithoutConfigFile(String kennung) {
		
		String jdbcUrl = "jdbc:mysql://localhost:3306/" + kennung + "?useSSL=false";
		String user = kennung;
		String pass = kennung;
		
		try {
			System.out.println("Connecting to database");
			Connection myConn = DriverManager.getConnection(jdbcUrl, user, pass);
			System.out.println(myConn.getMetaData().getURL());
			System.out.println("Connection successful");						
			
			myConn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
