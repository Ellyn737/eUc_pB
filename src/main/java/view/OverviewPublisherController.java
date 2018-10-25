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



public class OverviewPublisherController implements Initializable{
	
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
	

	//BackButton
	@FXML private void handleBackButton(ActionEvent event) throws IOException{
		System.out.println("ANTC - handleBackButton");
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/Overview.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
}

