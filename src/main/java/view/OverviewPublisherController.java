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


public class OverviewPublisherController {
	
	@FXML private Button backBtn;
	

	//BackButton
	@FXML private void handleBackButton(ActionEvent event) throws IOException	{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/StarteMenu.fxml"));
		Scene SearchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.show();
		}
	}
