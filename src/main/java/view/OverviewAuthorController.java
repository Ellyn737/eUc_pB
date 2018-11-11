package view;

import java.io.IOException; 





import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javafx.beans.property.ReadOnlyStringWrapper;


import org.hibernate.Session;

import controller.BibController;
import controller.MainBibliothek;
import controller.SingletonFactory;
import controller.StatisticsController;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Pair;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import models.Book;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.collections.ObservableList;
import javafx.util.Pair;
import models.Book;
import models.Media;



public class OverviewAuthorController implements Initializable{
	
	private String author;
	BibController bc;
	StatisticsController sc;

	
	@FXML
    private AnchorPane rootPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Button searchBtn;
    @FXML
    private TextField givenAuthor;
    @FXML
    private Label authorLabel;
    @FXML
    private Label numberLabel;
    @FXML
    private ListView<String> listView;
    @FXML
    private Button backBtn;
    
	
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		System.out.println("OAuC - initialize");
		
		statisticAuthor();
	}
	
	@FXML private void handleSearchBtn(ActionEvent event) throws IOException{
//	 	if a author is searched
		if(!givenAuthor.getText().isEmpty()) {
//			get input String 
			author = givenAuthor.getText();
			
			bc = new BibController();
//			make list with searchParameters for bc.findBookId
			ArrayList<Pair> searchParameter = new ArrayList<>();
			searchParameter.add(new Pair("author", author));
//			get the bookIds
			List<Integer> matchingAuthorIds = bc.findBookId(searchParameter);
//			set String to show n Label
			String showNumberResult = Integer.toString(matchingAuthorIds.size());
			String showAuthorResult = author;
//			set new Label
			authorLabel.setText(showAuthorResult);
			numberLabel.setText(showNumberResult);

		}else {
//			WARNUNG
		}
		
		
		
}
	public void statisticAuthor() {
		System.out.println("BibController - statisticAuthor");
		
		
			ArrayList<String> allreadyCounted = new ArrayList<>();
//			ObservableList<String> resultListName = FXCollections.observableArrayList();
//			ObservableList<Integer> resultListNumber = FXCollections.observableArrayList();
			ObservableList<String> resultList = FXCollections.observableArrayList();
			List <Pair> pairList = new ArrayList<>();
			sc = new StatisticsController();
			List <String> allAuthors = sc.getAllAuthors();
			
			//Anfang von Auflistung top Autoren
			//Für jeden Namen in der Liste
			for(int i = 0; i<allAuthors.size(); i++) {
				//Setze counter auf 0
				int doubleAuthor = 0;
				//Wenn der Namenoch nicht vorkam
				if(!allreadyCounted.contains(allAuthors.get(i))) {
				//Geh die List mit Autoren Namen durch
					for(int j = 0; j<allAuthors.size(); j++) {
						//Wenn der Name gleich ist
						if(allAuthors.get(i).equals(allAuthors.get(j))) {
							//Zähle counter hoch
							doubleAuthor++;
							
						}
					}
					//Setze Ergebnis String
					String result = allAuthors.get(i) + ":  " + doubleAuthor;
					
//					String authorResult = allAuthors.get(i);
//					resultListName.add(authorResult);
//					int numberResult = doubleAuthor;
//					resultListNumber.add(numberResult);
					//Setze eergebnis String in Liste
					resultList.add(result);
					
					pairList.add(new Pair(doubleAuthor, result));
					
					
					
					//Füge den analysierten Namen der List mit Namen zu, die nicht nochmal durchgeguckt wurden
					allreadyCounted.add(allAuthors.get(i));
					System.out.println(result);
			}
				
			}
			
//			System.out.println(resultListName);
//			System.out.println(resultListNumber);
//			
		//	Arrays.sort(pairList.get);
			
			
			//Setze in Liste
			listView.setItems(resultList);
			
			
	}		
		
	

	//BackButton
	@FXML private void handleBackButton(ActionEvent event) throws IOException{
		System.out.println("OAC - handleBackButton");
		AnchorPane startPane = FXMLLoader.load(getClass().getResource("../view/Overview.fxml"));
		rootPane.getChildren().setAll(startPane);
		
		}	
}

