package view;

import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controller.MainBibliothek;
import controller.StatisticsController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Pair;


public class OverviewGenreController implements Initializable{
	
	
	
	@FXML 
	private Button backBtn;	
	@FXML 
	private Button non_fictionBtn;	
	@FXML 
	private Button novelBtn;
	@FXML 
	private AnchorPane rootPane;
	@FXML 
	private SplitMenuButton menuGenre;
    @FXML
    private PieChart pieChartGenre;
    @FXML
    private Label genreValue;
	
	private MainBibliothek mainBib;
	private StatisticsController sc;
	
	public void setMain(MainBibliothek mainBib){
		this.mainBib = mainBib;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		System.out.println("OGC - initialize");
		pieChartGenre.setTitle("GenreStatistiken");
		
		statisticGenre();
		}
	
	//SachbuchButton
    @FXML
    void handlenNon_fictionButton(ActionEvent event) throws IOException{
		System.out.println("OGC - handleNon_fictionButton");
		pieChartGenre.setTitle("Sachbücher");
		
		statisticSubGenreS();

    }
    
	//RomanButton
    @FXML
    void handleNovelButton(ActionEvent event) throws IOException{
		System.out.println("OGC - handleNovelButton");
		pieChartGenre.setTitle("Romane");
		
		statisticSubGenreR();
    }
    
	//BackButton
	@FXML private void handleBackButton(ActionEvent event) throws IOException{
		System.out.println("OGC - handleBackButton");
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/Overview.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
	
    @FXML
    void showValues(MouseEvent event) {
    	System.out.println("OGC - MoueseEvent");

		for (final PieChart.Data data : pieChartGenre.getData()) {
		    data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
		        new EventHandler<MouseEvent>() {
		            @Override public void handle(MouseEvent e) {
		                genreValue.setText(String.valueOf(data.getPieValue()));
		             } 
		        });
			}
    }
	
	public void statisticGenre() {
		System.out.println("BibController - statisticGenre");
		sc = new StatisticsController();
		List <Pair> pairList = new ArrayList<>();


		List<String> allGenre = sc.getAllGenre();
		
		ArrayList <String> allreadyCounted = new ArrayList<>();
		ObservableList<String> resultList = FXCollections.observableArrayList();

		
		//Anfang von Auflistung top Autoren
		//Für jeden Namen in der Liste
		for(int i = 0; i<allGenre.size(); i++) {
			//Setze counter auf 0
			int doubleGenre = 0;
			//Wenn der Name noch nicht vorkam
			if(!allreadyCounted.contains(allGenre.get(i))) {
			//Geh die List mit Autoren Namen durch
				for(int j = 0; j<allGenre.size(); j++) {
					//Wenn der Name gleich ist
					if(allGenre.get(i).equals(allGenre.get(j))) {
						//Zähle counter hoch
						doubleGenre++;
						
					}
				}
				String result = allGenre.get(i);
				//Füge den analysierten Namen der List mit Namen zu, die nicht nochmal durchgeguckt wurden
				resultList.add(result);
				
				pairList.add(new Pair(allGenre, result));
				allreadyCounted.add(allGenre.get(i));
				
				pieChartGenre.getData().add(new Data(result, doubleGenre));
			}
		}
		
		

	} 
	public void statisticSubGenreR() {
		System.out.println("BibController - statisticSubGenreRoman");
		pieChartGenre.getData().clear();
		sc = new StatisticsController();

		List<String> allSubGenreR = sc.getAllSubGenreR();
		
		ArrayList <String> allreadyCounted = new ArrayList<>();
		ObservableList<String> resultList = FXCollections.observableArrayList();

		
		//Anfang von Auflistung top Autoren
		//Für jeden Namen in der Liste
		for(int i = 0; i<allSubGenreR.size(); i++) {
			//Setze counter auf 0
			int doubleGenre = 0;
			//Wenn der Name noch nicht vorkam
			if(!allreadyCounted.contains(allSubGenreR.get(i))) {
			//Geh die List mit Autoren Namen durch
				for(int j = 0; j<allSubGenreR.size(); j++) {
					//Wenn der Name gleich ist
					if(allSubGenreR.get(i).equals(allSubGenreR.get(j))) {
						//Zähle counter hoch
						doubleGenre++;
						
					}
				}
				String result = allSubGenreR.get(i);
				//Füge den analysierten Namen der List mit Namen zu, die nicht nochmal durchgeguckt wurden
				resultList.add(result);
				
				allreadyCounted.add(allSubGenreR.get(i));
				System.out.println(result);
				
				pieChartGenre.getData().add(new Data(allSubGenreR.get(i), doubleGenre));
			}
		}
	} 
	public void statisticSubGenreS() {
		System.out.println("BibController - statisticGenreSachbuch");
		pieChartGenre.getData().clear();
		
		sc = new StatisticsController();
		List <Pair> pairList = new ArrayList<>();


		List<String> allSubGenreS = sc.getAllSubGenreS();
		
		ArrayList <String> allreadyCounted = new ArrayList<>();
		ObservableList<String> resultList = FXCollections.observableArrayList();

		
		//Anfang von Auflistung top Autoren
		//Für jeden Namen in der Liste
		for(int i = 0; i<allSubGenreS.size(); i++) {
			//Setze counter auf 0
			int doubleGenre = 0;
			//Wenn der Name noch nicht vorkam
			if(!allreadyCounted.contains(allSubGenreS.get(i))) {
			//Geh die List mit Autoren Namen durch
				for(int j = 0; j<allSubGenreS.size(); j++) {
					//Wenn der Name gleich ist
					if(allSubGenreS.get(i).equals(allSubGenreS.get(j))) {
						//Zähle counter hoch
						doubleGenre++;
						
					}
				}
				String result = allSubGenreS.get(i);
				//Füge den analysierten Namen der List mit Namen zu, die nicht nochmal durchgeguckt wurden
				resultList.add(result);
				
				pairList.add(new Pair(allSubGenreS, result));
				allreadyCounted.add(allSubGenreS.get(i));
				System.out.println(result);
				
				pieChartGenre.getData().add(new Data(result, doubleGenre));
			}
		}
	} 
}

