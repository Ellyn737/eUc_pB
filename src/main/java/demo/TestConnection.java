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

import models.Lender;
import models.Rating;
import models.Book;
import models.Media;
import models.BorrowMedia;

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
				addAnnotatedClass(Media.class).addAnnotatedClass(Book.class).
				addAnnotatedClass(Lender.class).addAnnotatedClass(Rating.class).
				addAnnotatedClass(BorrowMedia.class).buildSessionFactory();
		
		
		Session session = factory.getCurrentSession();
		
		try {
			
			//use the session object to save/retrieve Java objects
			//create a media/buch object
			System.out.println("Create a media/buch object");
			Lender tempAusleiher = new Lender();
			System.out.println("VorName");
			tempAusleiher.setFirstName("Ellyn");
			System.out.println("Nachname");
			tempAusleiher.setLastName("Reese-Bünning");
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
