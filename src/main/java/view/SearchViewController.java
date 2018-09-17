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
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchViewController {

	@FXML Label titleLabel;
	@FXML TextField txtFiTitle;
	@FXML TextField txtFiAutor;
	@FXML TextField txtFiVerlag;
	@FXML TextField txtFiJahr;
	@FXML SplitMenuButton menuGenre;
	@FXML RadioButton radioBtnIsThere;
	@FXML Button cancelBtn;
	@FXML Button searchBtn;
	
	public MainBibliothek mainBib;
	
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
	
	@FXML private void handleSearchButton(ActionEvent event) throws IOException{
		//ausserdem suchparameter weiter und an db geben
		
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/ResultsView.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
	

	
}
