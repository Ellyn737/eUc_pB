package view;

import java.io.IOException; 


import controller.MainBibliothek;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class OverviewController {
	
	@FXML private Label titleLabel;

	@FXML private Button authorBtn;
	
	@FXML private Button genreBtn;
	
	@FXML private Button publisherBtn;
	
	@FXML private Button yearBtn;
	
	@FXML private Button editionBtn;
	
	@FXML private Button languageBtn;
	
	@FXML private Button backBtn;

	private MainBibliothek mainBib;	

	public void setMain(MainBibliothek mainBib){
		this.mainBib = mainBib;
	}
	
	//AuthorButton
	@FXML private void handleAuthorButton(ActionEvent event) throws IOException	{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/Overview_Author.fxml"));
		Scene SearchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.show();
	}
	//GenreButton
	@FXML private void handleGenreButton(ActionEvent event) throws IOException	{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/Overview_Genre.fxml"));
		Scene SearchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.show();
	}
	//PublisherButton
	@FXML private void handlePublisherButton(ActionEvent event) throws IOException	{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/Overview_Publisher.fxml"));
		Scene SearchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.show();
	}
	//YearButton
	@FXML private void handleYearButton(ActionEvent event) throws IOException	{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/Overview_Year.fxml"));
		Scene SearchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.show();
	}
	//EditionButton
	@FXML private void handleEditionButton(ActionEvent event) throws IOException	{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/Overview_Language.fxml"));
		Scene SearchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.show();
	}
	//LanguageButton
	@FXML private void handleLanguageButton(ActionEvent event) throws IOException	{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/StarteMenu.fxml"));
		Scene SearchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.show();
	}
	//BackButton
	@FXML private void handleBackButton(ActionEvent event) throws IOException	{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/StarteMenu.fxml"));
		Scene SearchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.show();
	}
}