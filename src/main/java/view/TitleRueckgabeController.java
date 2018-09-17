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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class TitleRueckgabeController {
	@FXML Label titleLabel;
	@FXML Label givenTitle;
	@FXML ImageView givenImage;
	@FXML Label givenAutor;
	@FXML Label givenVerlag;
	@FXML Label givenJahr;
	@FXML TextField txtFiBorrower;
	@FXML TextField txtFiDateBack;
	@FXML Button giveBackBtn;
	@FXML Button cancelBtn;
	
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
	
	@FXML private void handleGiveBackButton(ActionEvent event) throws IOException{
		//datum checken und auf IsThere setzen, nutzer austragen bei titel
		
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/ShowTitle.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}
}
