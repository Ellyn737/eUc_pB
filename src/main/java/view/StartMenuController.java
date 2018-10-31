package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import controller.LenderController;
import controller.MainBibliothek;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


/**
 * manages the menu 
 * 
 * @author ellyn
 *
 */
public class StartMenuController {
	
	@FXML private AnchorPane rootPane;
	@FXML private Label titleLabel;
	@FXML private Button searchBtn;
	@FXML private Button addBtn;
	@FXML private Button statisticsBtn;
	@FXML private Button exitBtn;
	@FXML private Button changeLibrBtn; 
	
	private MainBibliothek mainBib;
	private LenderController lc;
	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
//		System.out.println(System.getProperties());
	}
	
	/**
	 * on changeLibrarian
	 * change librarian Name, Email or EmailPassword
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleChangeLibrarianButton(ActionEvent event) throws IOException{
		setChangeAdminDialog();
	}
	
	/**
	 * on search go to SearchView
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleSearchButton(ActionEvent event) throws IOException{
		AnchorPane searchPane = FXMLLoader.load(getClass().getResource("../view/SearchView.fxml"));
		rootPane.getChildren().setAll(searchPane);
		}	
	
	/**
	 * on add go to AddNewTitle
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleAddButton(ActionEvent event) throws IOException{
		AnchorPane addPane = FXMLLoader.load(getClass().getResource("../view/AddNewTitle.fxml"));
		rootPane.getChildren().setAll(addPane);
		}	
	
	/**
	 * on statistics go to Overview
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleStatisticsButton(ActionEvent event) throws IOException{
		AnchorPane addPane = FXMLLoader.load(getClass().getResource("../view/Overview.fxml"));
		rootPane.getChildren().setAll(addPane);
		}	
	
	/**
	 * on exit close the program
	 * @param event
	 * @throws IOException
	 */
		@FXML private void handleExitButton(ActionEvent event) throws IOException{
//		Platform.exit();
		System.exit(0);
		}	
	
	/**
	 * Set the title of the program with the librarians name
	 * 
	 * @param title
	 */
	public void setTitleOfStartMenu(String title) {
		titleLabel.setText(title);
	}
	
	/**
	 * changes the data for the librarian
	 */
	public void setChangeAdminDialog() {
		Alert dialog = new Alert(AlertType.WARNING, "Bitte geben Sie die erforderlichen Veränderungen ein.", ButtonType.OK);
		dialog.setTitle("ÄNDERUNGEN FÜR DEN BIBLIOTHEKAR");
		dialog.setHeaderText("Sie möchten die Daten für den Bibliothekar ändern.");
		
//		deactivate the x in the right upper corner
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
			ArrayList<Pair> changes = new ArrayList<Pair>();
//			if textfield is not empty
			if(!adminFNameTxt.getText().isEmpty()) {
//				set variables 
				changes.add(new Pair("Firstname", adminFNameTxt.getText()));
			}
			if(!adminLNameTxt.getText().isEmpty()) {
				changes.add(new Pair("Lastname", adminLNameTxt.getText()));
			}
			if(!adminEmailTxt.getText().isEmpty()) {
				changes.add(new Pair("Email", adminEmailTxt.getText()));
			}
			if(!adminEmailPWTxt.getText().isEmpty()) {
				changes.add(new Pair("EmailPW", adminEmailPWTxt.getText()));
			}
//			check if everything is filled out
			if(changes.size() < 1) {
				String message1 = "Es fehlen Daten, um den Bibliothekar zu registrieren.";
				String message2 = "Bitte füllen Sie alle Felder aus. ";
				setWarning(message1, message2);
			}else {
				lc.changeLibrarian(changes);
				String message1 = "Die Änderungen wurden gespeichert.";
				String message2 = "Drücken Sie auf OK und fahren Sie fort.";
				setWarning(message1, message2);
//				close dialog
				dialog.close();
			}
		}
	}
	
	/**
	 * warning for when not all fields are filled out
	 */
	public void setWarning(String message1, String message2) {
		Alert warning = new Alert(AlertType.WARNING, message2, ButtonType.OK);
		warning.setTitle("ACHTUNG");
		warning.setHeaderText(message1);
		warning.showAndWait();
		if(warning.getResult() == ButtonType.OK) {
			warning.close();
		}
	}
	
	}
