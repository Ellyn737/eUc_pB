package view;

import java.io.IOException; 
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controller.BibController;
import controller.MainBibliothek;
import controller.StatisticsController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;



public class OverviewRatingController implements Initializable{
	
	@FXML private Button backBtn;
	@FXML private AnchorPane rootPane;
    @FXML private Label titleLabel;
    @FXML private BarChart<String, Number> ratingChart;
    @FXML private CategoryAxis x;
    @FXML private NumberAxis y;

	private MainBibliothek mainBib;
	private StatisticsController sc;
	ArrayList <Pair> resultList = new ArrayList<>();

	public void setMain(MainBibliothek mainBib){
		this.mainBib = mainBib;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		System.out.println("ORC - initialize");
		
//		BarChart-Style setzen
		x.setTickLabelFill(Color.WHITE);
		y.setTickLabelFill(Color.WHITE);
		x.setTickLabelFont( new Font("Arial", 20));
		
		statisticsRating();
	}
	
//	BackButton
	@FXML private void handleBackButton(ActionEvent event) throws IOException{
		System.out.println("ORaC - handleBackButton");
		AnchorPane startPane = FXMLLoader.load(getClass().getResource("../view/Overview.fxml"));
		rootPane.getChildren().setAll(startPane);
	}	
	
	public void statisticsRating() {
		System.out.println("ORaC - statisticGenre");

		sc = new StatisticsController();
		List<Integer> allRatings = sc.getAllRatings();
		
		ArrayList<Integer> allreadyCounted = new ArrayList<>();
		
//		Anfang von Auflistung top Autoren
//		Für jeden Namen in der Liste
		for(int i = 0; i<allRatings.size(); i++) {
//			Setze counter auf 0
			int doubleRating = 0;
//			Wenn der Name noch nicht vorkam
			if(!allreadyCounted.contains(allRatings.get(i))) {
//			Geh die List mit Autoren Namen durch
				for(int j = 0; j<allRatings.size(); j++) {
//					Wenn der Name gleich ist
					if(allRatings.get(i) == allRatings.get(j)) {
//						Zähle counter hoch
						doubleRating++;
						
					}
				}		
//				Füge den analysierten Namen der List mit Namen zu, die nicht nochmal durchgeguckt wurden
				allreadyCounted.add(allRatings.get(i));
				
				resultList.add(new Pair(allRatings.get(i), doubleRating));
			}
		}
		XYChart.Series set1 = new XYChart.Series<>();
		
//		X-Achse(Sterne) setzen
        set1.getData().add(new XYChart.Data<String, Number>("1", 0));
        set1.getData().add(new XYChart.Data<String, Number>("2", 0));
        set1.getData().add(new XYChart.Data<String, Number>("3", 0));
        set1.getData().add(new XYChart.Data<String, Number>("4", 0));
        set1.getData().add(new XYChart.Data<String, Number>("5", 0));

//        Anzahl der Sterne holen
		for(int i = 0; i<resultList.size();i++) {
			int key = (Integer)resultList.get(i).getKey();
		
			switch(key) {
			case 1: 
				set1.getData().add(new XYChart.Data<>("1", resultList.get(i).getValue()));
				break;
			case 2:
				set1.getData().add(new XYChart.Data<>("2", resultList.get(i).getValue()));
				break;
			case 3:
				set1.getData().add(new XYChart.Data<>("3", resultList.get(i).getValue()));
				break;
			case 4:
				set1.getData().add(new XYChart.Data<>("4", resultList.get(i).getValue()));
				break;
			case 5:
				set1.getData().add(new XYChart.Data<>("5", resultList.get(i).getValue()));
				break;
			}
		}
		ratingChart.getData().addAll(set1);
	}
}