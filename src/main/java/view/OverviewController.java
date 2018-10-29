package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.BibController;
import controller.MainBibliothek;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class OverviewController implements Initializable{
	
	@FXML private Label titleLabel;
	@FXML private Button authorBtn;
	@FXML private Button genreBtn;
	@FXML private Button publisherBtn;
	@FXML private Button yearBtn;
	@FXML private Button ratingBtn;
	@FXML private Button languageBtn;
	@FXML private Button backBtn;
	@FXML Menu sbMenu;
	@FXML Menu rMenu;
	
	private MainBibliothek mainBib;

	public void setMain(MainBibliothek mainBib){
		this.mainBib = mainBib;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		System.out.println("ANTC - initialize");
		
	}
	
	//AuthorButton
	@FXML private void handleAuthorButton(ActionEvent event) throws IOException	{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/OverviewAuthor.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
	}
	//GenreButton
	@FXML private void handleGenreButton(ActionEvent event) throws IOException	{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/OverviewGenre.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
	}
	//PublisherButton
	@FXML private void handlePublisherButton(ActionEvent event) throws IOException	{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/OverviewPublisher.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
	}
	//YearButton
	@FXML private void handleYearButton(ActionEvent event) throws IOException	{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/OverviewYear.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
	}
	//EditionButton
	@FXML private void handleRatingButton(ActionEvent event) throws IOException	{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/OverviewRating.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
	}
	//LanguageButton
	@FXML private void handleLanguageButton(ActionEvent event) throws IOException	{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/OverviewLanguage.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
	}
	//BackButton
	@FXML private void handleBackButton(ActionEvent event) throws IOException{
		System.out.println("ANTC - handleBackButton");
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
}