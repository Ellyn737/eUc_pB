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



public class StartMenuController {
	
	@FXML private Label titleLabel;
	
	@FXML private Button searchBtn;
	
	@FXML private Button addBtn;
	
	@FXML private Button statisticsBtn;
	
	@FXML private Button exitBtn;
	
	public MainBibliothek mainBib;
	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}
	
	@FXML private void handleSearchButton(ActionEvent event) throws IOException{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/SearchView.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
	
	@FXML private void handleAddButton(ActionEvent event) throws IOException{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/AddNewTitle.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
	
	@FXML private void handleExitButton(ActionEvent event) throws IOException{
			//close the app
		}	
	
	@FXML private void handleStatisticsButton(ActionEvent event) throws IOException{
			// Go To Statistics
		}	
	
	}
