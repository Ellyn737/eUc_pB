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
import javafx.stage.Stage;

public class ResultsViewController {

	@FXML Label titleLabel;
	@FXML Button cancelBtn;
	@FXML Label searchParametersLabel;
	@FXML Label givenTitle;
	@FXML Label givenAutor;
	@FXML Label givenVerlag;
	@FXML Label givenJahr;
	@FXML Label givenGenre;
	@FXML Label givenSubgenre;
	@FXML RadioButton givenIsThere;
	
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
	
	//funktion zum öffnen bei klicken auf titel
}
