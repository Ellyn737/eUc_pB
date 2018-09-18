package controller;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Ausleiher;
import models.Bewertung;
import models.Buch;
import models.Media;
import models.MediumAusleihen;
import view.StartMenuController;


/**
 * @author ellyn
 * 
 * Klasse zum Aufrufen der Main-Methode
 * 
 * Regelt den Aufruf des Programms
 */
public class MainBibliothek extends Application{

	private Stage primaryStage;
	

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		mainWindow();
	}
	
	public void mainWindow() throws IOException{
		
		FXMLLoader loader = new FXMLLoader(MainBibliothek.class.getResource("../view/StartMenu.fxml"));
		Parent pane = loader.load();

		StartMenuController startMenuController = loader.getController();
		startMenuController.setMain(this);
		
		Scene scene = new Scene(pane);
		primaryStage.setTitle("Ellyns Bib");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
	}
	
	public void connectingToDB() {
		
	}
	
	public static void main(String[] args) {
		launch(args);
		
		System.out.println("Connecting to database");
		//wird nur einmal erstellt
		SessionFactory factory = new Configuration().configure("hibernate.cfg.remote.xml").addPackage("models").
				addAnnotatedClass(Media.class).addAnnotatedClass(Buch.class).
				addAnnotatedClass(Ausleiher.class).addAnnotatedClass(Bewertung.class).
				addAnnotatedClass(MediumAusleihen.class).buildSessionFactory();
	}
	

	


}
