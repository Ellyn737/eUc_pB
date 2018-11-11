package controller;

import java.io.IOException;
import java.util.ArrayList;

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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.Lender;
import models.Book;
import models.Media;
import models.BorrowMedia;
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
	private LenderController lc;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		mainWindow();
	}
	
	public void mainWindow(){
//		wenn admin noch nicht existiert --> tabelle lender.isEmpty --> dann Dialog zum erstellen des admins
		lc = new LenderController();
		int lastID = lc.getLastLenderId();
		
		System.out.println(lastID);
		if(lastID >= 1) {
			showStartMenu();
		}else {
			setAddAdminDialog();
			showStartMenu();
		}
		
}
	
	public void showStartMenu() {
		try {
			Lender librarian = lc.getLender(1);
			String librarianName = librarian.getFirstName();
			String title = librarianName.toUpperCase() + "'s BIBLIOTHEK";	
			
			FXMLLoader loader = new FXMLLoader(MainBibliothek.class.getResource("/view/StartMenu.fxml"));
			Parent pane = loader.load();
	
			StartMenuController startMenuController = loader.getController();
			startMenuController.setMain(this);	
			startMenuController.setTitleOfStartMenu(title);
			
			Scene scene = new Scene(pane);
			primaryStage.setTitle(title);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void connectingToDB() {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	

	public Stage getStage() {
		return primaryStage;
	}
	
	/**
	 * Anlegen des Bibliothekars als ersten Lender
	 */
	public void setAddAdminDialog() {
		Alert dialog = new Alert(AlertType.WARNING, "Bitte geben Sie Ihren Namen und Ihre Email ein. ", ButtonType.OK);
		dialog.setTitle("ERSTELLUNG DER BIBLIOTHEK");
		dialog.setHeaderText("Der Bibliothekar existiert noch nicht.");
		
//		x in rechter oberer ecke ausgeschaltet
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.setOnCloseRequest(event ->{
			event.consume();
		});
		
		Label adminLbl = new Label("Bibliothekar: ");
		Label adminFName = new Label("Vorname: ");
		TextField adminFNameTxt = new TextField();
		Label adminLName = new Label("Nachname: ");
		TextField adminLNameTxt = new TextField();
		Label adminEmail = new Label("Email: ");
		TextField adminEmailTxt = new TextField();

		
		GridPane grid = new GridPane();
		grid.add(adminLbl, 1, 1);
		grid.add(adminFName, 1, 2);
		grid.add(adminFNameTxt, 2, 2);
		grid.add(adminLName, 1, 3);
		grid.add(adminLNameTxt, 2, 3);
		grid.add(adminEmail, 1, 4);
		grid.add(adminEmailTxt, 2, 4);
		
		dialog.getDialogPane().setContent(grid);

		dialog.showAndWait();
		
		if(dialog.getResult() == ButtonType.OK){
			lc = new LenderController();
			ArrayList<Pair> params = new ArrayList<Pair>();
			if(!adminFNameTxt.getText().isEmpty()) {
//				Bibliothekar als ersten Lender anlegen
				params.add(new Pair("Firstname", adminFNameTxt.getText()));
			}
			if(!adminLNameTxt.getText().isEmpty()) {
				params.add(new Pair("Lastname", adminLNameTxt.getText()));
			}
			if(!adminEmailTxt.getText().isEmpty()) {
				params.add(new Pair("Email", adminEmailTxt.getText()));
			}
			if(params.size() < 3) {
				setWarningFillOutAllFields();
			}else {
				lc.addNewLender(params);
				dialog.close();
			}
		}
//		javafx.scene.control.FXDialog.requestPermissionToClose(Dialog<?>)
	}
	
	/**
	 * Warnung, wenn nicht alle Felder des Bibliothekaranlegedialogs ausgefüllt sind
	 */
	public void setWarningFillOutAllFields() {
		Alert warning = new Alert(AlertType.WARNING, "Bitte füllen Sie alle Felder aus. ", ButtonType.OK);
		warning.setTitle("ACHTUNG");
		warning.setHeaderText("Es fehlen Daten, um den Bibliothekar zu registrieren.");
		warning.showAndWait();
		if(warning.getResult() == ButtonType.OK) {
			warning.close();
		}
	}
	


}
