package view;

import java.io.IOException; 


import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controller.BibController;
import controller.MainBibliothek;
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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class OverviewPublisherController implements Initializable{
	
	private String publisher;
	private BibController bc;
	private StatisticsController sc;
	
	@FXML AnchorPane rootPane;
	@FXML private Button backBtn;
	@FXML Button searchBtn;
	@FXML TextField givenPublisher;
	@FXML ListView<String> listView;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		System.out.println("OPuC - initialize");
		
		statisticPublisher();
	}
	
	@FXML private void handleSearchBtn(ActionEvent event) throws IOException{
//		if a author is searched
		if(!givenPublisher.getText().isEmpty()) {
//			get input String 
			publisher = givenPublisher.getText();
			
			bc = new BibController();
//			make list with searchParameters for bc.findBookId
			ArrayList<Pair> searchParameter = new ArrayList<>();
			searchParameter.add(new Pair("publisher", publisher));
//			get the bookIds
			List<Integer> matchingPublisherIds = bc.findBookId(searchParameter);
//			set String to show in listView
			String showPublisherResult = publisher + ": " + matchingPublisherIds.size();
//			set list for listView
			ObservableList<String> results = FXCollections.observableArrayList();
//			add String
			results.add(showPublisherResult);
//			add resuts to listView
			listView.setItems(results);
		}else {
//			WARNUNG
		}
}


public void statisticPublisher() {
	System.out.println("BibController - statisticAuthor");
	
	
		ArrayList<String> allreadyCounted = new ArrayList<>();
//		ObservableList<String> resultListName = FXCollections.observableArrayList();
//		ObservableList<Integer> resultListNumber = FXCollections.observableArrayList();
		ObservableList<String> resultList = FXCollections.observableArrayList();
		List <Pair> pairList = new ArrayList<>();
		sc = new StatisticsController();
		List <String> allPublisher = sc.getAllPublisher();
		
		//Anfang von Auflistung top Autoren
		//Für jeden Namen in der Liste
		for(int i = 0; i<allPublisher.size(); i++) {
			//Setze counter auf 0
			int doublePublisher = 0;
			//Wenn der Namenoch nicht vorkam
			if(!allreadyCounted.contains(allPublisher.get(i))) {
			//Geh die List mit Autoren Namen durch
				for(int j = 0; j<allPublisher.size(); j++) {
					//Wenn der Name gleich ist
					if(allPublisher.get(i).equals(allPublisher.get(j))) {
						//Zähle counter hoch
						doublePublisher++;
						
					}
				}
				//Setze Ergebnis String
				String result = allPublisher.get(i) + ":  " + doublePublisher;
				
//				String authorResult = allAuthors.get(i);
//				resultListName.add(authorResult);
//				int numberResult = doubleAuthor;
//				resultListNumber.add(numberResult);
				//Setze eergebnis String in Liste
				resultList.add(result);
				
				pairList.add(new Pair(doublePublisher, result));
				
				
				
				//Füge den analysierten Namen der List mit Namen zu, die nicht nochmal durchgeguckt wurden
				allreadyCounted.add(allPublisher.get(i));
				System.out.println(result);
		}
			
		}
		
//		System.out.println(resultListName);
//		System.out.println(resultListNumber);
//		
	//	Arrays.sort(pairList.get);
		
		
		//Setze in Liste
		listView.setItems(resultList);
		
		
	}		


	
//BackButton
@FXML private void handleBackButton(ActionEvent event) throws IOException{
	System.out.println("OPC - handleBackButton");
	AnchorPane startPane = FXMLLoader.load(getClass().getResource("../view/Overview.fxml"));
	rootPane.getChildren().setAll(startPane);
	}	

}