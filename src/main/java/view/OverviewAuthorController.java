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
import javafx.collections.transformation.SortedList;
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
import javafx.scene.text.Text;
import javafx.scene.effect.Reflection;
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
	
	private BibController bc;
	private StatisticsController sc;
	
	@FXML private AnchorPane rootPane;
    @FXML private Label titleLabel;
    @FXML private Button searchBtn;
    @FXML private TextField givenAuthor;
    @FXML private Text authorText;
    @FXML private Label numberLabel;
    @FXML private ListView<String> listView;
    @FXML private Button backBtn;
    
	ObservableList<String> resultList = FXCollections.observableArrayList();
	
    private String showNumberResult;
    private String showAuthorResult;
	private String author;

	/*
	 * setzen von Liste der Autoren
	 */
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		System.out.println("OAuC - initialize");
		
		statisticAuthor();
	}
	
//	BackButton
	@FXML private void handleBackButton(ActionEvent event) throws IOException{
		System.out.println("OAuC - handleBackButton");
		AnchorPane startPane = FXMLLoader.load(getClass().getResource("../view/Overview.fxml"));
		rootPane.getChildren().setAll(startPane);
		
		}	
	
	@FXML private void handleSearchBtn(ActionEvent event) throws IOException{
		System.out.println("OAuC - handleSearchButton");

//	 	Bei einem eingegebenen Autor
		if(!givenAuthor.getText().isEmpty()) {
			author = givenAuthor.getText();
			
			bc = new BibController();
//			make list with searchParameters for bc.findBookId
			ArrayList<Pair> searchParameter = new ArrayList<>();
			searchParameter.add(new Pair("author", author));
//			get the bookIds
			List<Integer> matchingAuthorIds = bc.findBookId(searchParameter);
//			String setzen für Label/Text
			showNumberResult = Integer.toString(matchingAuthorIds.size());
			showAuthorResult = author;
			
//			Angezeigte Texte überschreiben
			authorText.setText(showAuthorResult);
			numberLabel.setText(showNumberResult);

		}else {
//			WARNUNG
		}
	}
	
	/*
	 * Auflistung der Autoren und Anzahl
	 */
	public void statisticAuthor() {
		System.out.println("OAuC - statisticAuthor");
		
			ArrayList<String> allreadyCounted = new ArrayList<>();

			sc = new StatisticsController();
			List <String> allAuthors = sc.getAllAuthors();
			
//			 Anfang von Auflistung der Autoren
//			 Für jeden Namen in der Liste
			
			for(int i = 0; i<allAuthors.size(); i++) {
//				Setze counter auf 0
				int doubleAuthor = 0;
				
//				Wenn der Namenoch nicht vorkam
				if(!allreadyCounted.contains(allAuthors.get(i))) {
//				Geh die List mit Autoren Namen durch
					for(int j = 0; j<allAuthors.size(); j++) {
//						Wenn der Name gleich ist
						if(allAuthors.get(i).equals(allAuthors.get(j))) {
//							Zähle counter hoch
							doubleAuthor++;
						}
					}
//					Setze Ergebnis String
					String result = allAuthors.get(i) + ":  " + doubleAuthor;
					resultList.add(result);
				
//					Füge den analysierten Namen der List mit Namen zu, die nicht nochmal durchgeguckt wurden
					allreadyCounted.add(allAuthors.get(i));
				}
				
			}
//			Setze in eine geordnete Liste (sortiert nach Namen)
			SortedList<String> sortedList = new SortedList(resultList);
			listView.setItems(sortedList.sorted());
	}		

}

