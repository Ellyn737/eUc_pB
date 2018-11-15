package view;

import java.io.IOException; 
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controller.BibController;
import controller.MainBibliothek;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;



public class OverviewAuthorController {
	
	private String author;
	BibController bc;
	
	
	@FXML AnchorPane rootPane;
	@FXML Button backBtn;
	@FXML Button searchBtn;
	@FXML TextField givenAuthor;
	@FXML ListView<String> listView;
	
	@FXML private void handleSearchBtn(ActionEvent event) {
//		if a author is searched
		if(!givenAuthor.getText().isEmpty()) {
//			get input String 
			author = givenAuthor.getText();
			
			bc = new BibController();
//			make list with searchParameters for bc.findBookId
			ArrayList<Pair> searchParameter = new ArrayList<>();
			searchParameter.add(new Pair("author", author));
//			get the bookIds
			List<Integer> matchingAuthorIds = bc.findBookId(searchParameter);
//			set String to show in listView
			String showAuthorsResult = author + ": " + matchingAuthorIds.size();
//			set list for listView
			ObservableList<String> results = FXCollections.observableArrayList();
//			add String
			results.add(showAuthorsResult);
//			add resuts to listView
			listView.setItems(results);
		}else {
//			WARNUNG
		}
		
		
	}

	//BackButton
	@FXML private void handleBackButton(ActionEvent event) throws IOException{
		System.out.println("OAuthorC - handleBackButton");
		AnchorPane startPane = FXMLLoader.load(getClass().getResource("../view/Overview.fxml"));
		rootPane.getChildren().setAll(startPane);
		}	
}

