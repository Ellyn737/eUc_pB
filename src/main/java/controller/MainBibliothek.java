package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.Lender;
import models.Librarian;
import models.Book;
import models.Media;
import models.BorrowMedia;
import view.StartMenuController;


/**
 * @author ellyn
 * 
 * this class manages the main method
 * and calls the programm
 */
public class MainBibliothek extends Application{

	private Stage primaryStage;
	private LenderController lc;
	private EmailController emc;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		
		mainWindow();
	}
	
	public void mainWindow(){
//		if admin doesn't exist --> tabelle lender.isEmpty --> then dialog for generating the admin
		lc = new LenderController();
		int lastID = lc.getLastLenderId();
		
		System.out.println(lastID);
		if(lastID >= 1) {
			emc = new EmailController();
			emc.checkOverdueReturns();
			
			showStartMenu();
		}else {
			setAddAdminDialog();
			
		}
		
	}
	
	public void showStartMenu() {
		try {
			Librarian librarian = lc.getLibrarian();
			String librarianName = librarian.getFirstName();
			String title = librarianName.toUpperCase() + "'s BIBLIOTHEK";	
			
			FXMLLoader loader = new FXMLLoader(MainBibliothek.class.getResource("../view/StartMenu.fxml"));
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
	 * generates the librarian = the first lender
	 */
	public void setAddAdminDialog() {
		Alert dialog = new Alert(AlertType.WARNING, "Bitte geben Sie Ihren Namen, Ihre Email-Adresse und das zugehörige Email-Passwort an. ", ButtonType.OK);
		dialog.setTitle("ERSTELLUNG DER BIBLIOTHEK");
		dialog.setHeaderText("Der Bibliothekar existiert noch nicht.");
		
//		deactivate the x in the right upper corner
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.setOnCloseRequest(event ->{
			event.consume();
		});
		
		String email ="";
		String emailPW ="";
		String smtpH ="";
		String fName ="";
		String lName ="";
		
		Label adminLbl = new Label("Bibliothekar: ");
		Label adminFName = new Label("Vorname: ");
		TextField adminFNameTxt = new TextField();
		Label adminLName = new Label("Nachname: ");
		TextField adminLNameTxt = new TextField();
		Label adminEmail = new Label("Email: ");
		TextField adminEmailTxt = new TextField();
		Label adminEmailPW = new Label("Emailpasswort: ");
		PasswordField adminEmailPWTxt = new PasswordField();
		
		GridPane grid = new GridPane();
		grid.add(adminLbl, 1, 1);
		grid.add(adminFName, 1, 2);
		grid.add(adminFNameTxt, 2, 2);
		grid.add(adminLName, 1, 3);
		grid.add(adminLNameTxt, 2, 3);
		grid.add(adminEmail, 1, 4);
		grid.add(adminEmailTxt, 2, 4);
		grid.add(adminEmailPW, 1, 5);
		grid.add(adminEmailPWTxt, 2, 5);
		
		dialog.getDialogPane().setContent(grid);

		dialog.showAndWait();
		
		if(dialog.getResult() == ButtonType.OK){
			lc = new LenderController();
			ArrayList<Pair> params = new ArrayList<Pair>();
//			if textfield is not empty
			if(!adminFNameTxt.getText().isEmpty()) {
//				set variables 
				fName = adminFNameTxt.getText();
				params.add(new Pair("Firstname", fName));
			}
			if(!adminLNameTxt.getText().isEmpty()) {
				lName = adminLNameTxt.getText();
				params.add(new Pair("Lastname", lName));
			}
			if(!adminEmailTxt.getText().isEmpty()) {
				email = adminEmailTxt.getText();
				params.add(new Pair("Email", email));
			}
			if(!adminEmailPWTxt.getText().isEmpty()) {
				emailPW = adminEmailPWTxt.getText();
				params.add(new Pair("EmailPW", emailPW));
			}
//			check if everything is filled out
			if(params.size() < 4) {
				setWarning("Es fehlen Daten, um den Bibliothekar zu registrieren.", "Bitte füllen Sie alle Felder aus. ");
			}else {
//				add the librarian
				lc.addNewLibrarian(params);
				
//				test the email connection  and only continue if it works
				emc = new EmailController();
				Librarian librarian = lc.getLibrarian();
//				test the given email connection
				Boolean canSendEmail = emc.testLibrarianData(librarian.getEmail(), librarian.getSmtpHost(), librarian.getEmailPW());				
				while(!canSendEmail){
//					warning smtpHost wrong --> find one and give as input
					String message1 = "Es konnte keine Verbindung zu Ihrem Email-Host hergestellt werden."
										+ "Bitte überprüfen Sie die angegebene Email-Adresse und das Passwort"
										+ "oder geben Sie den passenden Smtp-Host an.";
					setWarningSmtp(message1);
					lc = new LenderController();
					Librarian librarianNew = lc.getLibrarian();
					canSendEmail = emc.testLibrarianData(librarianNew.getEmail(), librarianNew.getSmtpHost(), librarianNew.getEmailPW());
				}
				
//				add the librarian and go to startMenu
				showStartMenu();
				dialog.close();
			}
		}
	}
	
	/**
	 * warning for when not all fields are filled out
	 */
	public void setWarning(String headerMsg, String message) {
		Alert warning = new Alert(AlertType.WARNING, message, ButtonType.OK);
		warning.setTitle("ACHTUNG");
		warning.setHeaderText(headerMsg);
		warning.showAndWait();
		if(warning.getResult() == ButtonType.OK) {
			warning.close();
		}
	}


	/**
	 * warning if email connection fails
	 */
	public void setWarningSmtp(String headerMsg) {
		System.out.println("EmailController - setWarningSmtp");

		Alert warning = new Alert(AlertType.WARNING, "", ButtonType.OK);
		warning.setTitle("ACHTUNG");
		warning.setHeaderText(headerMsg);
		
		lc = new LenderController();
		Librarian librarian = lc.getLibrarian();
		
		Label email = new Label("Email: ");
		TextField emailTxt = new TextField();
		emailTxt.setText(librarian.getEmail());
		Label emailPW = new Label("Email-Passwort: ");
		TextField emailPWTxt = new TextField();		
		emailPWTxt.setText(librarian.getEmailPW());
		Label smtp = new Label("Smtp-Host: ");
		TextField smtpTxt = new TextField();
	
		GridPane grid = new GridPane();
		grid.add(email, 1, 1);
		grid.add(emailTxt, 2, 1);
		grid.add(emailPW, 1, 2);
		grid.add(emailPWTxt, 2, 2);
		grid.add(smtp, 1, 3);
		grid.add(smtpTxt, 2, 3);
		
		warning.getDialogPane().setContent(grid);		
		warning.showAndWait();
		
//		if ok is pressed
		if(warning.getResult() == ButtonType.OK) {
//			check if a new smtphost is given
			if(!smtpTxt.getText().isEmpty()) {
//				yes: change the librarian 
				ArrayList<Pair> changes = new ArrayList<>();
				changes.add(new Pair("Smtp", smtpTxt.getText()));
				lc.changeLibrarian(changes);
				warning.close();
			}
		}
//		check if new email is given
		if(!emailTxt.getText().equals(librarian.getEmail())) {
//			yes: set smtp and email 
			ArrayList<Pair> changes = new ArrayList<>();
			changes.add(new Pair("Email", emailTxt.getText()));
			
//			get parts of the email before and after the @
			String[] emailParts = emailTxt.getText().split(Pattern.quote("@"));
//			set smtp host String with last part of the email
			String smtpHost = "smtp." + emailParts[1];
			changes.add(new Pair("Smtp", smtpHost));
			
			lc.changeLibrarian(changes);
			warning.close();
		}
//		check if new emailPw is given
		if(!emailPWTxt.getText().equals(librarian.getEmailPW())) {
//			yes: set emailPW 
			ArrayList<Pair> changes = new ArrayList<>();
			changes.add(new Pair("EmailPW", emailPWTxt.getText()));
			lc.changeLibrarian(changes);
			warning.close();
		}
	}
	
}
