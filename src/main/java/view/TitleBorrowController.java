package view;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.print.attribute.standard.DateTimeAtCreation;

import org.apache.log4j.helpers.DateTimeDateFormat;
import org.eclipse.jdt.core.dom.SwitchCase;

import controller.BibController;
import controller.BorrowController;
import controller.LenderController;
import controller.MainBibliothek;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import models.Book;
import models.Lender;

public class TitleBorrowController {

	@FXML AnchorPane rootPane;
	@FXML Label titleLabel;
	@FXML Label givenTitle;
	@FXML ImageView givenImage;
	@FXML Label givenAuthor;
	@FXML Label givenPublisher;
	@FXML Label givenYear;
	@FXML TextField txtFiFirstName;
	@FXML TextField txtFiLastName;
	@FXML TextField txtFiEmail;
	@FXML Button borrowBtn;
	@FXML Button cancelBtn;
	@FXML Label lblReturnDate;
	@FXML TextArea txtArMessage;

	
	private MainBibliothek mainBib;
	private TitleBorrowController tbc;
	private BibController bc;
	private LenderController lc;
	private BorrowController borC;
	
	private List<Integer> resultIds;
	private ArrayList<Pair> oldParameters;
	private int titleId;
	
	private String email;
	private String firstName;
	private String lastName;
	private String message;
	private LocalDate returnDate;
	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}
	
	/**
	 * leads back to ShowTitle
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
		AnchorPane pane = (AnchorPane) loader.load();
		
		//id an ResultsView uebergeben
		ShowTitleController showTitle = loader.getController();
		showTitle.setOldParametersForReturning(resultIds, oldParameters);
		showTitle.fillView(titleId);
		
		Scene scene = new Scene(pane);
		rootPane.getChildren().setAll(pane);
		}	
	
	/**
	 * checks the input and sets borrowMedia and book.isBorrowed = true
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleBorrowButton(ActionEvent event) throws IOException{
		//borrower und datum bei titel eintragen
		System.out.println("TBoC - handleBorrowButton");
		ArrayList<Pair> params = new ArrayList<>();
		
		System.out.println("check if all fields are filled");
//		check if all textFields are filled
		if(!txtFiEmail.getText().isEmpty()) {
			email = txtFiEmail.getText();	
			params.add(new Pair("Email", email));
		}
		
		if(!txtFiFirstName.getText().isEmpty()) {
			firstName = txtFiFirstName.getText();
			params.add(new Pair("Firstname", firstName));
		}
		
		if(!txtFiLastName.getText().isEmpty()) {
			lastName = txtFiLastName.getText();
			params.add(new Pair("Lastname", lastName));
		}
		
		if(!txtArMessage.getText().isEmpty()) {
			message = txtArMessage.getText();
			params.add(new Pair("Message", message));
		}
			
		
//		all fields are filled out
		if(params.size() == 4) {
			System.out.println("All fields are filled");

			lc = new LenderController();
			System.out.println("get matching ids to name");
//			search for list of lenders --> by name (first + last)
			List<Integer> lenderIds = lc.findLenderId(firstName, lastName);

//			if there is only one lender with this name
			if(lenderIds.size() == 1) {		
				System.out.println("there is just 1 matching lender");

				borC = new BorrowController();
				bc = new BibController();
				
				System.out.println("get lenders id");
				int lenderId = lenderIds.get(0);

				System.out.println("check if input.email matches id.email");
//				check if email is the same
//				if NOT: ask if email should be changed or old one should be used
//				if YES: close dialog and continue
				if(!checkIfEmailDiffersFromInput(lenderId)) {
					System.out.println("emails do NOT differ");
					System.out.println("borrow the book");
//					set borrowMedia
					borC.borrowBook(titleId, lenderId, returnDate, message);
//					set book as borrowed
					bc.setBookToBorrowed(titleId);
						
					System.out.println("Go To ShowTitle");
//					go to ShowTitle
					FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
					AnchorPane pane = (AnchorPane) loader.load();
					ShowTitleController showTitle = loader.getController();
					showTitle.setOldParametersForReturning(resultIds, oldParameters);
					showTitle.fillView(titleId);
					Scene scene = new Scene(pane);
					rootPane.getChildren().setAll(pane);
				}else {
					System.out.println("Emails differ");
					System.out.println("Set changeEmailDialog");
					setChangeEmailDialog(lenderId);
				}
			}
			else if(lenderIds.size() == 0){
				System.out.println("there are no matching lenders");
				System.out.println("Set AddNewLenderDialog");
				setAddNewLenderDialog();
			}
			else {
				System.out.println("There are multiple lenders with this name");
//				show list with all possible lenders with this name --> let them choose
				System.out.println("Show a list of ids");
			}
		}else {
			System.out.println("Not all Fields are filled out");
//			nicht alle Felder sind ausgef�llt
			System.out.println("Set WarningFields");
			setWarningFields();
		}
	}
	
	
	/**
	 * happens first
	 * sets sideParameters of the title
	 * @param id
	 */
	public void fillView(int id) {
		System.out.println("TBoC - fillView");
		titleId = id;
		bc = new BibController();
		
		try {
			Book book = bc.getTheBook(id);
//			set parameters on the left side
			givenTitle.setText(book.getTitle().toUpperCase());
			givenAuthor.setText(book.getAuthor());
			givenPublisher.setText(book.getPublisher());
			givenYear.setText(String.valueOf(book.getYearOfPublication()));
			
//			set the returnDate --> 30 days from now
			returnDate = LocalDate.now().plusDays(30);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.LLLL.yyyy");
			lblReturnDate.setText("R�ckgabedatum: " + returnDate.format(formatter));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * checks if the email is different
	 * every lender can only have one email address
	 * @param id
	 */
	public Boolean checkIfEmailDiffersFromInput(int id) {
		System.out.println("TBoC - checkForDifferentEmail");
		Boolean differs = false;
//		check if email is the same
//		if NOT: ask if email should be changed or old one should be used
//		if YES: close dialog and continue
		
		lc = new LenderController();
		Lender lender = lc.getLender(id);
		if(!email.equals(lender.getEmail())) {
			differs = true;
		}
		return differs;
	}
	
	/**
	 * shows a dialog for changing the email
	 * @param id
	 */
	public void setChangeEmailDialog(int id) {
		System.out.println("TBoC - setChangeEmailDialog");
		lc = new LenderController();
		
//		Dialog<> dialog = new Dialog<>();
		Alert dialog = new Alert(AlertType.WARNING, "M�chten Sie die alte Email-Adresse ersetzen?", ButtonType.YES, ButtonType.NO);
		dialog.setTitle("Email anpassen");
		dialog.setHeaderText("Die Email-Adresse des Ausleihers unterscheidet sich von der eingegebenen.");
		
		Label emailLabel = new Label("Email ");
		TextField emailTxt = new TextField();
		
		GridPane grid = new GridPane();
//		Spalte, Zeile
		grid.add(emailLabel, 1, 1);
		grid.add(emailTxt, 2, 1);
		
		dialog.getDialogPane().setContent(grid);
		emailTxt.setText(email);
		
		dialog.showAndWait();
		if(dialog.getResult() == ButtonType.YES){
//			neue email uebernehmen
//			veraenderugnen speichern und uebergeben
			ArrayList<Pair> changes = new ArrayList<Pair>();
			changes.add(new Pair("Email", emailTxt.getText()));
			lc.changeLender(id, changes);
			
//			setze TextFeld auf neue Email
			txtFiEmail.setText(lc.getLender(id).getEmail());
			
		}else {
//			alte email behalten
//			setze TextFeld auf alte Email
			txtFiEmail.setText(lc.getLender(id).getEmail());
			dialog.close();
		}
	}
	
	/**
	 * shows a dialog for adding a new lender
	 */
	public void setAddNewLenderDialog() {
		System.out.println("TBoC - setAddNewLenderDialog");
		lc = new LenderController();

		Alert dialog = new Alert(AlertType.WARNING, "M�chten Sie den neuen Ausleiher speichern?", ButtonType.YES, ButtonType.NO);
		dialog.setTitle("Der Ausleiher existiert nicht.");
		dialog.setHeaderText("F�llen Sie bitte alle Felder aus und legen so einen neuen Ausleiher an.");
		
		Label firstNameLbl = new Label("Vorname ");
		Label lastNameLbl = new Label("Nachname ");
		Label emailLbl = new Label("Email ");
		
		TextField firstNameTxt = new TextField();
		TextField lastNameTxt = new TextField();
		TextField emailTxt = new TextField();
		
		GridPane grid = new GridPane();
		grid.add(firstNameLbl, 1, 1);
		grid.add(firstNameTxt, 2, 1);
		grid.add(lastNameLbl, 1, 2);
		grid.add(lastNameTxt, 2, 2);
		grid.add(emailLbl, 1, 3);
		grid.add(emailTxt, 2, 3);
		dialog.getDialogPane().setContent(grid);
		
		firstNameTxt.setText(firstName);
		lastNameTxt.setText(lastName);
		emailTxt.setText(email);
		
		dialog.showAndWait();
		
		if(dialog.getResult() == ButtonType.YES) {
			ArrayList<Pair> params = new ArrayList<>();
	
			params.add(new Pair("Firstname", firstNameTxt.getText()));
			params.add(new Pair("Lastname", lastNameTxt.getText()));
			params.add(new Pair("Email", emailTxt.getText()));
			
			lc.addNewLender(params);
			int newLenderId = lc.getLastLenderId();
		}else {
			dialog.close();
		}
	}
	
	/**
	 * shows a warning that all fields need to be filled
	 */
	public void setWarningFields() {
		Alert warning = new Alert(AlertType.WARNING, "Bitte geben Sie alle n�tigen Daten an. ", ButtonType.OK);
		warning.setTitle("ACHTUNG");
		warning.setHeaderText("Es sind nicht alle Felder ausgef�llt.");
		warning.showAndWait();
		if(warning.getResult() == ButtonType.OK) {
			warning.close();
		}
	}
	
	/**
	 * gets the parameters of showTitle to return them later
	 * @param ids
	 * @param searchParams
	 */
	public void setOldParametersForReturning(List<Integer> ids, ArrayList<Pair> searchParams) {
		resultIds = ids;
		oldParameters = searchParams;
	}
	
	public TitleBorrowController getController() {
		return this.tbc;
	}
	
}
