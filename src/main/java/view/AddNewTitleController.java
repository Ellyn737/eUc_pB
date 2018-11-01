package view;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Rating;

import controller.BibController;
import controller.BorrowController;
import controller.LenderController;
import controller.MainBibliothek;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.Librarian;

/**
 * controller for the view AddNewTitle
 * 
 * @author ellyn
 *
 */
public class AddNewTitleController implements Initializable{
	
	@FXML AnchorPane rootPane;
	@FXML Label titleLabel;
	@FXML Button addImageBtn;
	@FXML ImageView image;
	@FXML TextField txtFiTitle;
	@FXML TextField txtFiSubTitle;
	@FXML TextField txtFiAuthor;
	@FXML TextField txtFiPublisher;
	@FXML TextField txtFiYear;
	@FXML TextField txtFiEdition;
	@FXML SplitMenuButton menuGenre;
	@FXML RadioButton radioBtnBorrowed;
	@FXML TextArea txtArContent;
	@FXML TextArea txtArComment;
	@FXML Button cancelBtn;
	@FXML Button addTitleBtn;
	@FXML Menu sbMenu;
	@FXML Menu rMenu;
	@FXML Rating ratingStars;
	
	private Boolean isBorrowed = false;
	private String title;
	private String subTitle;
	private String author;
	private String publisher;
	private int year;
	private String genre;
	private String subGenre;
	private String content;
	private String comment;
	private int edition;
	private int rating;
	private Boolean isRated = false;
	
	private MainBibliothek mainBib;
	private BibController bc;
	private LenderController lc;
	private BorrowController boc;
	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}
	
	
	/**
	 * sets subgenre, isBorrowed and rating on click
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("AddNewTitleController - initialize");

//		sachbuch auf OnAction
		ObservableList<MenuItem> sbItems = sbMenu.getItems();
		/**
		 * set subgenre to collected subgenre
		 * set genre to the clicked mennu (sb, r)
		 */
		for(MenuItem item: sbItems) {
			item.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					String genreItem = item.getText();
					menuGenre.setText(genreItem);
					genre = "Sachbuch";
					subGenre = genreItem;
				}
		
			});
		}
		
//		novels onAction
		ObservableList<MenuItem> rItems = rMenu.getItems();
		
		for(MenuItem rItem: rItems) {
			rItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					String genreItem = rItem.getText();
					menuGenre.setText(genreItem);
					genre = "Roman";
					subGenre = genreItem;
				}
		
			});
		}
		
//		isBorrowed onClicked
		radioBtnBorrowed.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(radioBtnBorrowed.isSelected()) {
					isBorrowed = true;
				}else {
					isBorrowed = false;
				}				
			}
		});
		
//		set rating as whole stars and with a default value of 0 stars
		ratingStars.setPartialRating(false);
		ratingStars.setRating(0);
//		set ratingStars onClicked
		ratingStars.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				isRated = true;
				rating = (int) ratingStars.getRating();
			}
		});
		
		
		
	}
	
	/**
	 * on cancel go back to StartMenu
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		System.out.println("AddNewTitleController - handleCancelButton");
		AnchorPane startPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
		rootPane.getChildren().setAll(startPane);
		}	
	
	/**
	 * on addTitle add a new book
	 * go to ShowTitle
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleAddTitleButton(ActionEvent event) throws IOException{
		System.out.println("AddNewTitleController - handleAddTitleButton");
		
//		not necessary: rating
		int numberOfNecessaryFields = 9;
		List txtFields = new ArrayList<>();

//		check if all necessary fields are filled out
		if(!txtFiTitle.getText().isEmpty()) {
			title = txtFiTitle.getText();
			txtFields.add(title);
		}
		if(!txtFiAuthor.getText().isEmpty()) {
			author = txtFiAuthor.getText();
			txtFields.add(author);
		}
		if(!txtFiPublisher.getText().isEmpty()) {
			publisher = txtFiPublisher.getText();
			txtFields.add(publisher);
		}
		if(!txtFiYear.getText().isEmpty()) {
			try {
				year = Integer.parseInt(txtFiYear.getText());
				txtFields.add(year);
			}catch(NumberFormatException ex) {
				String message1 = "Es wird eine Zahl benötigt.";
				String message2 = "Bitte geben Sie als Auflage oder Erscheinungsjahr eine Zahl an.";
				setWarning(message1, message2);			}
		}
		if(!txtFiEdition.getText().isEmpty()) {
			try {
				edition = Integer.parseInt(txtFiEdition.getText());
				txtFields.add(edition);
			}catch(NumberFormatException ex) {
				String message1 = "Es wird eine Zahl benötigt.";
				String message2 = "Bitte geben Sie als Auflage oder Erscheinungsjahr eine Zahl an.";
				setWarning(message1, message2);
			}
		}
		if(!txtArContent.getText().isEmpty()) {
			content = txtArContent.getText();
			txtFields.add(content);
		}
		if(!txtArComment.getText().isEmpty()) {
			comment = txtArComment.getText();
			txtFields.add(comment);
		}
		if(genre != null) {
			txtFields.add("genre");
		}
		if(subGenre != null) {
			txtFields.add("subGenre");
		}

		if(txtFields.size() == numberOfNecessaryFields) {
			addBook();
		}else {
			String message1 = "Das Buch ist nicht vollständig.";
			String message2 = "Bitte geben Sie alle erforderlichen Daten an.";
			setWarning(message1, message2);
		}
		

		
	}


	@FXML private void handleAddImageButton(ActionEvent event) throws IOException{
		//add image to title in db
		
		
		
	}

	/**
	 * sets the variables for the new book
	 * calls BibController
	 */
	public void addBook() {
		System.out.println("AddNewTitleController - addBook");
		bc = new BibController();
		try {

//			setze variablen für das Buch
			bc.setTitle(title);
			bc.setAuthor(author);
			bc.setPublisher(publisher);
			bc.setYear(year);
			bc.setContent(content);
			bc.setComment(comment);
			bc.setEdition(edition);
			bc.setIsBorrowed(isBorrowed);
			bc.setGenre(genre);
			bc.setSubGenre(subGenre);
			
			if(isRated) {
				bc.setRating(rating);
			}
			
			if(!txtFiSubTitle.getText().isEmpty()) {
				subTitle = txtFiSubTitle.getText();
				bc.setSubTitle(subTitle);
			}
			
//			füge das buch der bib hinzu
			bc.addToBib();			
			
//			set Parameters to show at the side and give list with this id
			int id = bc.getLastId();

//			if the book is added as borrowed
			if(isBorrowed) {
				System.out.println("isBorrowed ist true");
				lc = new LenderController();
				Librarian librarian = lc.getLibrarian();
				System.out.println("Das ist der Bibliothekar: " + librarian.getFirstName());
//				show alert --> which lender? to when?
				setAddBorrowedBookDialog(title, librarian.getFirstName(), id);
			}
			
			List<Integer> ids = new ArrayList<>();
			ids.add(id);
			
			ArrayList<Pair> searchParams = new ArrayList<>();
			searchParams.add(new Pair("title", title));
			
			//zu ShowTitle
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
			ShowTitleController showTitle = loader.getController();
			
			showTitle.fillView(id);
			showTitle.setOldParametersForReturning(ids, searchParams);

			Scene scene = new Scene(pane);
			rootPane.getChildren().setAll(pane);
		
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void setAddBorrowedBookDialog(String bookTitle, String librarianName, int bookID) {
		System.out.println("AddNewTitleController - setAddNewBorrowedBookDialog");
		Alert dialog = new Alert(AlertType.WARNING, "Bitte füllen Sie die entsprechenden Felder aus.", ButtonType.OK);
		dialog.setTitle("DAS BUCH IST AUSGELIEHEN");
		dialog.setHeaderText("Der Ausleiher und ein  Rückgabedatum müssen festgelegt werden.");
		
//		deactivate the x in the right upper corner
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.setOnCloseRequest(event ->{
			event.consume();
		});
		
		Label lenderFName = new Label("Vorname: ");
		TextField lenderFNameTxt = new TextField();
		Label lenderLName = new Label("Nachname: ");
		TextField lenderLNameTxt = new TextField();
		Label lenderEmail = new Label("Email: ");
		TextField lenderEmailTxt = new TextField();
		Label lenderMsg = new Label("Erinnerungsnachricht: ");
		TextArea lenderMsgTxt = new TextArea();
		Label returnDate = new Label("Rückgabedatum: ");
		DatePicker returnDateTxt = new DatePicker();
		
		returnDateTxt.setValue(LocalDate.now().plusDays(30));
		
		String defaultMessage = "Hallo"
				+ ",\r\ndu hast dir das Buch '" + bookTitle + "' von mir ausgeliehen.\r\n"
				+ "Sei so lieb und bring es zurück.\r\n"
				+ "Liebe Grüße\r\n" + librarianName; 
		lenderMsgTxt.setText(defaultMessage);
		
		GridPane grid = new GridPane();
		grid.add(lenderFName, 1, 1);
		grid.add(lenderFNameTxt, 2, 1);
		grid.add(lenderLName, 1, 2);
		grid.add(lenderLNameTxt, 2, 2);
		grid.add(lenderEmail, 1, 3);
		grid.add(lenderEmailTxt, 2, 3);
		grid.add(lenderMsg, 1, 4);
		grid.add(lenderMsgTxt, 2, 4);
		grid.add(returnDate, 1, 5);
		grid.add(returnDateTxt, 2, 5);
		
		dialog.getDialogPane().setContent(grid);
		dialog.showAndWait();
		
		ArrayList<Pair> params = new ArrayList<>();
		if(dialog.getResult() == ButtonType.OK){
//			check if all fields are set
			int necessaryFields = 0;
			if(!lenderFNameTxt.getText().isEmpty()) {
				params.add(new Pair("Firstname", lenderFNameTxt.getText()));
				necessaryFields++;
			}
			if(!lenderLNameTxt.getText().isEmpty()) {
				params.add(new Pair("Lastname", lenderLNameTxt.getText()));
				necessaryFields++;
			}
			if(!lenderEmailTxt.getText().isEmpty()) {
				params.add(new Pair("Email", lenderEmailTxt.getText()));
				necessaryFields++;
			}
			if(!lenderMsgTxt.getText().isEmpty()) {
				necessaryFields++;
			}
			
//			if all fields are filled out
			if(necessaryFields == 4) {
//				check if lender exists
				lc = new LenderController();
				boc = new BorrowController();
//				get lenderid by email
				ArrayList<Integer> lenderIDs = lc.findLenderIdByEmail(lenderEmailTxt.getText());
				if(lenderIDs.size() == 1) {
					System.out.println("lenderIDs ist 1");
//					get the id
					int lenderID = lenderIDs.get(0);
//					if yes: add borrowing
					boc.borrowBook(bookID, lenderID, returnDateTxt.getValue(), lenderMsgTxt.getText());
					dialog.close();
				}else if(lenderIDs.size() <= 0) {
					System.out.println("lenderIDs ist 0");

//					check if lender name can be found
					List<Integer> lenderNameIDs = lc.findLenderIdByName(lenderFNameTxt.getText(), lenderLNameTxt.getText());
					if(lenderNameIDs.size() >= 1) {
						System.out.println("lenderNameIDs ist größer gleich 1");

//						set warning new email?--> change this lender
						int lenderNameID = lenderNameIDs.get(0); 
						setChangeLenderWarning(lenderNameID);
						boc.borrowBook(bookID, lenderNameID, returnDateTxt.getValue(), lenderMsgTxt.getText());
						dialog.close();
					}
					else {
						System.out.println("lenderNameIDs ist kleiner als 1");

//						if not: add new lender + new borrowing	
						lc.addNewLender(params);
						int lastLenderID = lc.getLastLenderId();
						boc.borrowBook(bookID, lastLenderID, returnDateTxt.getValue(), lenderMsgTxt.getText());
						dialog.close();
					}
				}
	
			}
			
		}
		
	}
	
	public void setChangeLenderWarning(int lenderID) {
		System.out.println("AddNewTitleController - setChangeLenderWarning");

		Alert dialog = new Alert(AlertType.WARNING, "Bitte geben Sie die richtige Email-Adresse an.", ButtonType.OK);
		dialog.setTitle("FEHLER BEIM SUCHEN DES AUSLEIHERS");
		dialog.setHeaderText("Die Email des Ausleihers stimmt nicht.");
		
//		deactivate the x in the right upper corner
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.setOnCloseRequest(event ->{
			event.consume();
		});
		
		Label newEmail = new Label("Neue Email-Adresse: ");
		TextField newEmailTxt = new TextField();
		
		GridPane grid = new GridPane();
		grid.add(newEmail, 1, 1);
		grid.add(newEmailTxt, 2, 1);
		
		dialog.getDialogPane().setContent(grid);
		dialog.showAndWait();
		
		if(dialog.getResult() == ButtonType.OK){
			if(!newEmailTxt.getText().isEmpty()) {
	//			change lender email
				lc = new LenderController();
				ArrayList<Pair> changedEmail = new ArrayList<>();
				changedEmail.add(new Pair("Email", newEmailTxt.getText()));
				lc.changeLender(lenderID, changedEmail);
				dialog.close();
			}
		}
		
	}
	
	/**
	 * sets a warning
	 */
	public void setWarning(String message1, String message2) {
		Alert warning = new Alert(AlertType.WARNING, message2, ButtonType.OK);
		warning.setTitle("ACHTUNG");
		warning.setHeaderText(message1);
		warning.showAndWait();
	}

}
