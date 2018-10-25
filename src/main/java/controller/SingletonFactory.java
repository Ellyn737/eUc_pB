package controller;

import org.apache.tools.ant.taskdefs.Exec;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;

import models.Lender;
import models.Rating;
import models.Book;
import models.Media;
import models.BorrowMedia;

public final class SingletonFactory {
	

	public static SessionFactory factoryReal;
	
	private SingletonFactory() {
		
	}
	
	public static synchronized SessionFactory getFactory() {
		if(factoryReal == null) {
			factoryReal = new Configuration().configure("hibernate.cfg.remote.xml").addPackage("models").
					addAnnotatedClass(Media.class).addAnnotatedClass(Book.class).
					addAnnotatedClass(Lender.class).addAnnotatedClass(Rating.class).
					addAnnotatedClass(BorrowMedia.class).buildSessionFactory();

		}
		return factoryReal;
	}
	
}
