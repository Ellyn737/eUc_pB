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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
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
	@FXML ImageView image;
	@FXML Button deleteTitleBtn;
	@FXML Button changeTitleBtn;
	@FXML Button cancelBtn;
	@FXML Button borrowBtn;

	private MainBibliothek mainBib;
	private ShowTitleController showTitleC;
	private BibController bc;
	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}

	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		System.out.println("STC - handleCancelButton");

		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
	
	@FXML private void handleDeleteTitleButton(ActionEvent event) throws IOException{
		System.out.println("STC - handleDeleteButton");

		//show warning
		//delete title from db then go to menu
		
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
	
	@FXML private void handleChangeTitleButton(ActionEvent event) throws IOException{
		System.out.println("STC - handleChangeTitleButton");

		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/ChangeTitle.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
	
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
		
		bc = new BibController();
		try {
			Book book = bc.getBookData(id);
			
			System.out.println("Setze variablen in die Felder");
			titleLabel.setText(book.getTitle().toUpperCase());
			givenTitle.setText(book.getTitle());
			givenAuthor.setText(book.getAuthor());
			givenPublisher.setText(book.getPublisher());
			givenYear.setText(String.valueOf(book.getYearOfPublication()));
			givenGenre.setText(book.getGenre());
			givenSubgenre.setText(book.getSubGenre());
			givenContent.setText(book.getContent());
			givenComment.setText(book.getComment());
			
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
