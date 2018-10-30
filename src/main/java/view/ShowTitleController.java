package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.Rating;

import controller.BibController;
import controller.BorrowController;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.Book;
/**
 * shows the given title
 * values from ResultsViewController or AddNewTitleController
 * 
 * @author ellyn
 */
public class ShowTitleController {
	@FXML AnchorPane rootPane;
	@FXML Label titleLabel;
	
	@FXML Label givenTitle;
	@FXML Label givenSubTitle;
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
	@FXML Rating ratingStars;


	private MainBibliothek mainBib;
	private ShowTitleController showTitleC;
	private BibController bc;
	private BorrowController boc;
	
	private int titleId;
	private List<Integer> resultIds;
	ArrayList<Pair> oldParameters;
	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}

	/**
	 * on cancel go to StartMenu
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		System.out.println("ShowTitleController - handleCancelButton");
		AnchorPane startPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
		rootPane.getChildren().setAll(startPane);
		}	
	
	/**
	 * call alert
	 * on yes: delete title
	 * on no: showTitle
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleDeleteTitleButton(ActionEvent event) throws IOException{
		System.out.println("ShowTitleController - handleDeleteButton");
		try {
//			get book with bookid
			Book selection = bc.getTheBook(titleId);
//			get number of copys of this title
			int exemplars = selection.getExemplar();
//			set the alert and ask for confirmation
			Alert alert = new Alert(AlertType.CONFIRMATION, "Sind Sie sicher, dass sie " + selection.getTitle() + " löschen möchten?", ButtonType.YES, ButtonType.NO);
			alert.showAndWait();
			
//			on yes
			if(alert.getResult() == ButtonType.YES) {

//				analyse the number of other copys
//				if this is not the last copy
				if(exemplars > 1) {
//					go to ResultsView and show the rest
					FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultsView.fxml"));
					AnchorPane pane = (AnchorPane) loader.load();
					
					ResultsViewController resultsView = loader.getController();
					resultsView.fillListAndView(resultIds, oldParameters);
					
					Scene scene = new Scene(pane);
					rootPane.getChildren().setAll(pane);
				}else {					
//					else delete the borrowings of this title
					boc = new BorrowController();
					boc.deleteBorrowingsOfTitle(titleId);
					
//					delete the book
					bc.deleteFromBib(titleId);
					
//					go to StartMenu
					AnchorPane startPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
					rootPane.getChildren().setAll(startPane);
				}
			}
			
//			on no
			if(alert.getResult() == ButtonType.NO) {
//				go to ShowTitle
				FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
				AnchorPane pane = (AnchorPane) loader.load();
				
				ShowTitleController showTitle = loader.getController();
				showTitle.fillView(titleId);
				showTitle.setOldParametersForReturning(resultIds, oldParameters);
				
				Scene scene = new Scene(pane);
				rootPane.getChildren().setAll(pane);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}	
	
	
	/**
	 * on changeTitle go there
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleChangeTitleButton(ActionEvent event) throws IOException{
		System.out.println("ShowTitleController - handleChangeTitleButton");
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeTitle.fxml"));
		AnchorPane pane = (AnchorPane) loader.load();

//		give id to ChangeTitle
		ChangeTitleController changeTitle = loader.getController();
		changeTitle.fillView(titleId);
		changeTitle.setOldParametersForReturning(resultIds, oldParameters);
		
		Scene scene = new Scene(pane);
		rootPane.getChildren().setAll(pane);
		
		}	
	
	/**
	 * on Borrow go to TitleBorrowController or TitleReturnController
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleBorrowButton(ActionEvent event) throws IOException{
		System.out.println("ShowTitleController - handleBorrowButton");

//		check if isBorrowed is selected
//		on yes
		if(radioBtnBorrowed.isSelected()) {
//			go to TitleReturn
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TitleReturn.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
//			give it the needed variables
			TitleReturnController trc = loader.getController();
			trc.setOldParametersForReturning(resultIds, oldParameters);
			trc.fillView(titleId);
			
			Scene scene = new Scene(pane);
			rootPane.getChildren().setAll(pane);
		}
//		on no
		else {
//			go to TitleBorrow
			FXMLLoader loader = new FXMLLoader(getClass().getResource("TitleBorrow.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
//			give it the needed variables
			TitleBorrowController tbc = loader.getController();
			tbc.setOldParametersForReturning(resultIds, oldParameters);
			tbc.fillView(titleId);
			
			Scene scene = new Scene(pane);
			rootPane.getChildren().setAll(pane);
		}	
	}

	/**
	 * takes the given bookid
	 * gets the book and fills labels
	 * 
	 * @param id
	 */
	public void fillView(int id) {
		System.out.println("ShowTitleController - fillView");
		
		titleId = id;
		try {
			bc = new BibController();

			Book book = bc.getTheBook(id);
			
//			set variables into the fields
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
			
//			checks if there is a subtitle
			if(book.getSubTitle() != null) {
				givenSubTitle.setText(book.getSubTitle());
			}else {
				givenSubTitle.setText("");				
			}

//			checks if it is borrowed
			if(book.getIsBorrowed()) {
				radioBtnBorrowed.setSelected(true);
				borrowBtn.setText("RÜCKGABE");
			}else {
				radioBtnBorrowed.setSelected(false);
				borrowBtn.setText("AUSLEIHEN");
			}
			
//			sets rating
			ratingStars.setPartialRating(false);
			if(book.getStars() != null) {
				ratingStars.setRating(book.getStars());
			}else {
				ratingStars.setRating(0);
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
		
		
	/**
	 * @param ids
	 * @param searchParams
	 */
	public void setOldParametersForReturning(List<Integer> ids, ArrayList<Pair> searchParams) {
		resultIds = ids;
		oldParameters = searchParams;
	}
	
	/**
	 * makes datatransfer possible between controllers
	 * 
	 * @return
	 */
	public ShowTitleController getController() {
		return this.showTitleC;
	}
	
}
