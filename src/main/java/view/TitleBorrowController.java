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

/**
 * manages the borrowing of books
 * 
 * @author ellyn
 *
 */
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
	@FXML Label messageLbl;

	
	private MainBibliothek mainBib;
	private TitleBorrowController tbc;
	private BibController bc = new BibController();
	private LenderController lc = new LenderController();
	private BorrowController borC = new BorrowController();
	
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
		
//		give bookid and old results and searchParameters to ResultsView
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
//		add borrower and date to title
		System.out.println("TitleBorrowController - handleBorrowButton");
		ArrayList<Pair> params = new ArrayList<>();
		
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
			
		
//		if all fields are filled out
		if(params.size() == 4) {
//			search for list of lenders --> by name (first + last)
			List<Integer> lenderNameIds = lc.findLenderIdByName(firstName, lastName);
			
//			if there is only one lender with this name
			if(lenderNameIds.size() == 1) {		
//				get lenderid
				int lenderId = lenderNameIds.get(0);

//				check if input.email matches id.email
//				if YES: set borrowing and go to ShowTitle
				if(!checkIfEmailDiffersFromInput(lenderId)) {
//					set borrowMedia
					borC.borrowBook(titleId, lenderId, returnDate, message);
						
//					go to ShowTitle
					FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
					AnchorPane pane = (AnchorPane) loader.load();
					ShowTitleController showTitle = loader.getController();
					
					showTitle.setOldParametersForReturning(resultIds, oldParameters);
					showTitle.fillView(titleId);
					
					Scene scene = new Scene(pane);
					rootPane.getChildren().setAll(pane);
				}
//				if NOT: ask if email should be changed or old one should be used
				else {
					setChangeEmailDialog(lenderId);
				}
			}
//			if there are no matching lenders
			else if(lenderNameIds.size() == 0){
//				get lenderids with matching emails
				List<Integer> lenderEmailIds = lc.findLenderIdByEmail(email);
//				get lenderids with matching firstNames
				ArrayList<Integer> firstNameIds = lc.findLenderIdByFirstName(firstName);
				
//				check if there are lenderids that are the same
				ArrayList<Integer> emailAndFirstNameMatchIds = new ArrayList<>();
				int matchingNameAndEmailId = -1;
				
				for(int id: firstNameIds) {
					if(lenderEmailIds.contains(id)) {
						emailAndFirstNameMatchIds.add(id);
						matchingNameAndEmailId = id;
					}
				}
				
//				if yes:
				if(emailAndFirstNameMatchIds.size() > 0) {
//					ask if lastName should be changed
					setChangeLenderDialog(matchingNameAndEmailId);
				}
//				if no:
				else {
//					ask if new lender should be added
					setAddNewLenderDialog();
				}
				
			}
		}
//		if not all fields are filled out
		else {
//			set warning
			setWarningFields();
		}
	}
	
	
	/**
	 * happens first
	 * sets sideParameters of the title
	 * 
	 * @param id
	 */
	public void fillView(int id) {
		System.out.println("TitleBorrowController - fillView");
		titleId = id;
		
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
			lblReturnDate.setText("Rückgabedatum: " + returnDate.format(formatter));
			
//			set default Email
			String librarianName = lc.getLibrarian().getFirstName();
			
			String defaultMessage = "Hallo"
			+ ",\r\ndu hast dir das Buch '" + book.getTitle() + "' von mir ausgeliehen."
			+ "\r\nDie Ausleihfrist lief bis zum " + returnDate.format(formatter) + ". "
			+ "Sei so lieb und bring es zurück.\r\n"
			+ "Liebe Grüße\r\n" + librarianName; 
			
//			set defaultEmail into messageTxtField
			txtArMessage.setText(defaultMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * checks if the email is different
	 * every lender can only have one email address
	 * 
	 * @param id
	 */
	public Boolean checkIfEmailDiffersFromInput(int id) {
		System.out.println("TitleBorrowController - checkForDifferentEmail");
		Boolean differs = false;
//		check if email is the same
//		if NOT: ask if email should be changed or old one should be used
//		if YES: close dialog and continue
		
		Lender lender = lc.getLender(id);
		if(!email.equals(lender.getEmail())) {
			differs = true;
		}
		return differs;
	}
	
	/**
	 * shows a dialog for changing the email
	 * 
	 * @param id
	 */
	public void setChangeEmailDialog(int id) {
		System.out.println("TitleBorrowController - setChangeEmailDialog");
		
		Alert dialog = new Alert(AlertType.WARNING, "Möchten Sie die alte Email-Adresse ersetzen?", ButtonType.YES, ButtonType.NO);
		dialog.setTitle("Email anpassen");
		dialog.setHeaderText("Die Email-Adresse des Ausleihers unterscheidet sich von der eingegebenen.");
		
		Label emailLabel = new Label("Email ");
		TextField emailTxt = new TextField();
		
		GridPane grid = new GridPane();
//		column, row
		grid.add(emailLabel, 1, 1);
		grid.add(emailTxt, 2, 1);
		
		dialog.getDialogPane().setContent(grid);
		emailTxt.setText(email);
		
		dialog.showAndWait();
		if(dialog.getResult() == ButtonType.YES){
			ArrayList<Pair> changes = new ArrayList<Pair>();
//			if field is filled out
			if(!emailTxt.getText().isEmpty()) {
//				add changed email to lender
				changes.add(new Pair("Email", emailTxt.getText()));
				lc.changeLender(id, changes);
				
//				set textfield to new email
				txtFiEmail.setText(lc.getLender(id).getEmail());
			}
//			if not all fields are filled out
			else {
//				set warning
				setWarningFields();
			}
		}
//		if they dod not press yes
		else {
//			keep old email
//			set textfield to old email
			txtFiEmail.setText(lc.getLender(id).getEmail());
			dialog.close();
		}
	}
	
	/**
	 * sets a warning for changing the lender
	 * 	
	 * @param id
	 */
	public void setChangeLenderDialog(int id) {
		System.out.println("TitleBorrowController - setChangeLenderDialog");
		
		Alert dialog = new Alert(AlertType.WARNING, "Möchten Sie einen neuen Nachnamen eingeben?", ButtonType.YES, ButtonType.NO);
		dialog.setTitle("Nachname anpassen");
		dialog.setHeaderText("Der Nachname des Ausleihers unterscheidet sich von dem eingegebenen.");
		
		Label lNLbl = new Label("Nachname ");
		TextField lNTxt = new TextField();
		
		GridPane grid = new GridPane();
		grid.add(lNLbl, 1, 1);
		grid.add(lNTxt, 2, 1);
		
		dialog.getDialogPane().setContent(grid);
		lNTxt.setText(lastName);
		
		dialog.showAndWait();
//		if yes is pressed
		if(dialog.getResult() == ButtonType.YES){
			ArrayList<Pair> changes = new ArrayList<Pair>();
//			if field is filled out
			if(!lNTxt.getText().isEmpty()) {
				
//				update the lender with a new lastName
				changes.add(new Pair("Lastname", lNTxt.getText()));
				lc.changeLender(id, changes);
				
//				set textfield to new lastName
				txtFiLastName.setText(lc.getLender(id).getLastName());
			}
//			if not all fields are filled out
			else {
//				set a warning
				setWarningFields();
			}
		}
//		if yes is not pressed
		else {
//			use old lastName
			txtFiLastName.setText(lc.getLender(id).getLastName());
			dialog.close();
		}
	}
	
	/**
	 * shows a dialog for adding a new lender
	 */
	public void setAddNewLenderDialog() {
		System.out.println("TitleBorrowController - setAddNewLenderDialog");

		Alert dialog = new Alert(AlertType.WARNING, "Möchten Sie den neuen Ausleiher speichern?", ButtonType.YES, ButtonType.NO);
		dialog.setTitle("Der Ausleiher existiert nicht.");
		dialog.setHeaderText("Füllen Sie bitte alle Felder aus und legen so einen neuen Ausleiher an.");
		
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
		
//		if yes is pressed
		if(dialog.getResult() == ButtonType.YES) {
			ArrayList<Pair> params = new ArrayList<>();
	
//			check if all fields are filled out
//			if yes: add Pairs to ArrayList
			if(!firstNameTxt.getText().isEmpty()) {
				params.add(new Pair("Firstname", firstNameTxt.getText()));
			}
//			if no: set warning
			else {
				setWarningFields();
			}
			if(!lastNameTxt.getText().isEmpty()) {
				params.add(new Pair("Lastname", lastNameTxt.getText()));
			}else {
				setWarningFields();
			}
			if(!emailTxt.getText().isEmpty()) {
				params.add(new Pair("Email", emailTxt.getText()));
			}else {
				setWarningFields();
			}
			
//			add a new lender in LenderController
			lc.addNewLender(params);
//			get its lenderid
			int newLenderId = lc.getLastLenderId();
			
		}
//		if yes is not pressed
		else {
//			do nothing but closing the dialog
			dialog.close();
		}
	}
	
	/**
	 * shows a warning that all fields need to be filled
	 */
	public void setWarningFields() {
		Alert warning = new Alert(AlertType.WARNING, "Bitte geben Sie alle nötigen Daten an. ", ButtonType.OK);
		warning.setTitle("ACHTUNG");
		warning.setHeaderText("Es sind nicht alle Felder ausgefüllt.");
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
