package view;

import java.io.IOException;

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
/**
 * 
 * @author ellyn
 *
 *Werte werden von ResultsViewController oder AddNewTitleController uebergeben
 */
public class ShowTitleController {

	@FXML Label titleLabel;
	@FXML Label givenTable;
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
	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}

	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
	
	@FXML private void handleDeleteTitleButton(ActionEvent event) throws IOException{
		//show warning
		//delete title from db then go to menu
		
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
	
	@FXML private void handleChangeTitleButton(ActionEvent event) throws IOException{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/ChangeTitle.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
	
	@FXML private void handleBorrowButton(ActionEvent event) throws IOException{
		//if IsThere --> go to TitleRückgabe, else TitleAusleihe
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/TitleAusleihe.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
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
