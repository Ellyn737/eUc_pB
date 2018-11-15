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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class OverviewPublisherController implements Initializable{
	
	private BibController bc;
	private StatisticsController sc;
	
	@FXML AnchorPane rootPane;
	@FXML private Button backBtn;
	@FXML Button searchBtn;
	@FXML TextField givenPublisher;
    @FXML private Label searchLabel;
	@FXML ListView<String> listView;
    @FXML private ListView<String> listViewSearch;
    @FXML private Text publisherText;
	
	private ObservableList<String> resultList;
    private String showPublisherResult;
	private String publisher;

	@Override
	public void initialize(URL location, ResourceBundle resource) {
		System.out.println("OPuC - initialize");
		
		statisticPublisher();
	}
	
	@FXML private void handleSearchBtn(ActionEvent event) throws IOException{
		System.out.println("OPuC - handleSearchBtn");

		searchLabel.setText("Bücher:");
		searchPublisher();
	}
	
//	BackButton
	@FXML private void handleBackButton(ActionEvent event) throws IOException{
		System.out.println("OPuC - handleBackButton");
		AnchorPane startPane = FXMLLoader.load(getClass().getResource("../view/Overview.fxml"));
		rootPane.getChildren().setAll(startPane);
	}	

	/*
	 * setzen von Liste der Autoren
	 */
	public void statisticPublisher() {
		System.out.println("OPuC - statisticAuthor");
	
		ArrayList<String> allreadyCounted = new ArrayList<>();

		resultList = FXCollections.observableArrayList();

		sc = new StatisticsController();
		List <String> allPublisher = sc.getAllPublisher();
		
//		Anfang von Auflistung top Autoren
//		Für jeden Namen in der Liste
		for(int i = 0; i<allPublisher.size(); i++) {
//			Setze counter auf 0
			int doublePublisher = 0;
//			Wenn der Namenoch nicht vorkam
			if(!allreadyCounted.contains(allPublisher.get(i))) {
//			Geh die List mit Autoren Namen durch
				for(int j = 0; j<allPublisher.size(); j++) {
//					Wenn der Name gleich ist
					if(allPublisher.get(i).equals(allPublisher.get(j))) {
//						Zähle counter hoch
						doublePublisher++;
					}
				}
//				Setze Ergebnis String
				String result = allPublisher.get(i) + ":  " + doublePublisher;
				
				resultList.add(result);
							
//				Füge den analysierten Namen der Liste mit Namen zu, die nicht nochmal durchgeguckt wurden
				allreadyCounted.add(allPublisher.get(i));
				System.out.println(result);
			}	
		}
		SortedList<String> sortedList = new SortedList(resultList);

//		Setze in Liste
		listView.setItems(sortedList.sorted());
		
	}		
	
	/*
	 * BuchTitel der gesuchten Verlage
	 */
	public void searchPublisher(){
		System.out.println("OPuC - searchPublisher");

		ArrayList<String> allreadyCounted = new ArrayList<>();
		
//		 	if a author is searched
			if(!givenPublisher.getText().isEmpty()) {
				
//				get input String 
				publisher = givenPublisher.getText();
				
				bc = new BibController();
//				make list with searchParameters for bc.findBookId
				ArrayList<Pair> searchParameter = new ArrayList<>();
				searchParameter.add(new Pair("publisher", publisher));
//				get the bookIds
				List<Integer> matchingPublisherIds = bc.findBookId(searchParameter);
//				set String to show n Label
				String showAuthorResult = publisher;
//				set new Label
				publisherText.setText(showAuthorResult);
			}else {
//				WARNUNG
			}
			
			resultList = FXCollections.observableArrayList();
			List <Pair> pairList = new ArrayList<>();
			
			sc = new StatisticsController();
			List<String> allTitle = sc.searchTitle(publisher);
			
//			Anfang von Auflistung top Autoren
//			Für jeden Namen in der Liste
			for(int i = 0; i<allTitle.size(); i++) {
//				Setze counter auf 0
				int doubleAuthor = 0;
//				Wenn der Namenoch nicht vorkam
				if(!allreadyCounted.contains(allTitle.get(i))) {
//				Geh die List mit Autoren Namen durch
					for(int j = 0; j<allTitle.size(); j++) {
//						Wenn der Name gleich ist
						if(allTitle.get(i).equals(allTitle.get(j))) {
//							Zähle counter hoch
							doubleAuthor++;
						}
					}
//					Setze Ergebnis String
					String result = allTitle.get(i);
					
					resultList.add(result);
					
					pairList.add(new Pair(doubleAuthor, result));
	
//					Füge den analysierten Namen der Liste mit Namen zu, die nicht nochmal durchgeguckt wurden
					allreadyCounted.add(allTitle.get(i));
					}
			}
			listViewSearch.setItems(resultList);
	}
}