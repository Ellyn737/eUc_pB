package view;

import java.io.IOException;

import controller.BibController;
import controller.MainBibliothek;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import models.Book;
/**
 * 
 * @author ellyn
 *
 *Werte werden von ResultsViewController oder AddNewTitleController uebergeben
 */
public class ShowTitleController {

	@FXML Label titleLabel;
	
	@FXML Label givenTitle;
	@FXML Label givenAuthor;
	@FXML Label givenPublisher;
	@FXML Label givenYear;
	@FXML Label givenGenre;
	@FXML Label givenSubgenre;
	@FXML RadioButton radioBtnBorrowed;
	@FXML Label givenContent;
	@FXML Label givenComment;
	@FXML Label givenExemplar;
	@FXML Label givenEdition;
	@FXML ImageView image;
	@FXML Button deleteTitleBtn;
	@FXML Button changeTitleBtn;
	@FXML Button cancelBtn;
	@FXML Button borrowBtn;

	private MainBibliothek mainBib;
	private ShowTitleController showTitleC;
	private BibController bc;
	
	private int titleId;
	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}

	/**
	 * zurück zum StartMenu
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		System.out.println("STC - handleCancelButton");

		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
	
	/**
	 * alert aufrufen und nach Bestätigung löschen des Titels
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleDeleteTitleButton(ActionEvent event) throws IOException{
		System.out.println("STC - handleDeleteButton");

		//show warning
		//delete title from db then go to menu
		Book selection;
		try {
			selection = bc.getTheBook(titleId);
			Alert alert = new Alert(AlertType.CONFIRMATION, "Sind Sie sicher, dass sie " + selection.getTitle() + " löschen möchten?", ButtonType.YES, ButtonType.NO);
			alert.showAndWait();
			
			if(alert.getResult() == ButtonType.YES) {
				bc.deleteFromBib(titleId);
				Parent searchPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
				Scene searchScene = new Scene(searchPane);
				
				Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
				window.setScene(searchScene);
				window.show();
			}
			
//			nothing happens on no
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}	
	
	/**
	 * id an ChangeTitleView uebergeben und dorthin wechseln
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleChangeTitleButton(ActionEvent event) throws IOException{
		System.out.println("STC - handleChangeTitleButton");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeTitle.fxml"));
		Parent root = (Parent) loader.load();
		
		//id an ResultsView uebergeben
		ChangeTitleController changeTitle = loader.getController();
		changeTitle.fillView(titleId);
		
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		}	
	
	/**
	 * id an TitelAusleihe uebergeben und dahin wechseln
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleBorrowButton(ActionEvent event) throws IOException{
		System.out.println("STC - handleBorrowButton");

		//if IsThere --> go to TitleRückgabe, else TitleAusleihe
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/TitleAusleihe.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	

	/**
	 * uebernimmt die id des buches, das angezeigt werden soll
	 * holt das passende buch und füllt die labels...
	 * 
	 * @param id
	 */
	public void fillView(int id) {
		System.out.println("ST - fillView");
		
		titleId = id;
		
		bc = new BibController();
		try {
			Book book = bc.getTheBook(titleId);
			
			System.out.println("Setze variablen in die Felder");
			titleLabel.setText(book.getTitle().toUpperCase());
			givenTitle.setText(book.getTitle());
			givenAuthor.setText(book.getAuthor());
			givenPublisher.setText("Verlag: " + book.getPublisher());
			givenYear.setText("Erscheinungsjahr: " + String.valueOf(book.getYearOfPublication()));
			givenGenre.setText(book.getGenre());
			givenSubgenre.setText(book.getSubGenre());
			givenContent.setText(book.getContent());
			givenComment.setText(book.getComment());
			givenExemplar.setText("Exemplar: " + String.valueOf(book.getExemplar()));
			givenEdition.setText("Auflage: " + String.valueOf(book.getEdition()));
			
			System.out.println("hole isBorrowed");
			if(book.getIsBorrowed()) {
				radioBtnBorrowed.setSelected(true);
				System.out.println("Buch ist ausgeliehen");
			}else {
				radioBtnBorrowed.setSelected(false);
				System.out.println("Buch ist verfügbar");
			}
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		//es fehlen noch bild, bewertung

		
		
		
	}
	
	/**
	 * ermöglicht die uebergabe von daten von einem anderen FXController an diesen
	 * 
	 * @return
	 */
	public ShowTitleController getController() {
		return this.showTitleC;
	}
	
}
