package view;

import java.io.IOException; 
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import controller.BibController;
import controller.MainBibliothek;
import controller.StatisticsController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.Node;
import javafx.event.ActionEvent;



public class OverviewYearController implements Initializable{
	
	@FXML 
	private Button backBtn;
	@FXML 
	AnchorPane rootPane;
    @FXML
    private BarChart yearChart;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;

	private ArrayList <Pair> resultList = new ArrayList<>();
	
	private MainBibliothek mainBib;
	private StatisticsController sc;

	public void setMain(MainBibliothek mainBib){
		this.mainBib = mainBib;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		System.out.println("OYC - initialize");
		sc = new StatisticsController();
		List<Integer> allYears = sc.getAllYears();
		
		ArrayList<Integer> allreadyCounted = new ArrayList<>();
		
		
		//Anfang von Auflistung top Autoren
		//Für jeden Namen in der Liste
		for(int i = 0; i<allYears.size(); i++) {
			//Setze counter auf 0
			int doubleYears = 0;
			//Wenn der Name noch nicht vorkam
			if(!allreadyCounted.contains(allYears.get(i))) {
			//Geh die List mit Autoren Namen durch
				for(int j = 0; j<allYears.size(); j++) {
					//Wenn der Name gleich ist
					if(allYears.get(i) == allYears.get(j)) {
						//Zähle counter hoch
						doubleYears++;
						
					}
				}
				
				

				//Setze eergebnis String in Liste
//				resultList.add(result);
				
				//Füge den analysierten Namen der List mit Namen zu, die nicht nochmal durchgeguckt wurden
				allreadyCounted.add(allYears.get(i));
				
				resultList.add(new Pair(allYears.get(i).toString(), doubleYears));
		}
			
		}
		XYChart.Series set1 = new XYChart.Series<>();

		for(int i = 0; i<resultList.size();i++) {
			set1.getData().add(new XYChart.Data<>(resultList.get(i).getKey(), resultList.get(i).getValue()));
				}
		
		yearChart.getData().addAll(set1);
		
		
		
		System.out.println(resultList);
//		ratingChart.getData().addAll(set1);
	}
	

	//BackButton
	@FXML private void handleBackButton(ActionEvent event) throws IOException{
		System.out.println("OYC - handleBackButton");
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/Overview.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
}

