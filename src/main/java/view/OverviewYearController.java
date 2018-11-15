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
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.Node;
import javafx.event.ActionEvent;



public class OverviewYearController implements Initializable{
	
	@FXML private Button backBtn;
	@FXML private ListView<String> listView;
    @FXML private ListView<String> listViewTitle;
    @FXML private Label yearLabel;
    @FXML private Text yearText;
    @FXML private Text numberText;
    @FXML private Label yearLabelTitle; 
	@FXML private AnchorPane rootPane;
    
	ObservableList<String> resultList = FXCollections.observableArrayList();
	ObservableList<String> resultTitleList = FXCollections.observableArrayList();
	private StatisticsController sc;
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		System.out.println("OYC - initialize");
		
		statisticYear();
	}
	
	//BackButton
	@FXML private void handleBackButton(ActionEvent event) throws IOException{
		System.out.println("OYC - handleBackButton");
		AnchorPane startPane = FXMLLoader.load(getClass().getResource("../view/Overview.fxml"));
		rootPane.getChildren().setAll(startPane);
		}	
	
    /*
     * Durch Klicken eines Jahres, Alle Titel holen
     */
    public void yearSelected(MouseEvent event) {
    	System.out.println("OYeC - MouseEvent");
    	
    	String pick = listView.getSelectionModel().getSelectedItem();
    	resultTitleList = FXCollections.observableArrayList();
    	
    	String pickedYear = deleteCharAt(listView.getSelectionModel().getSelectedItem(), 4);
    	yearText.setText(pickedYear);
    	
		sc = new StatisticsController();
		List<String> yearList = sc.getpickedYear(pickedYear);
		
		String numberResult = yearList.size() + "";
    	numberText.setText(numberResult);
		
//    	Titel in die List einsetzen
		for(int i = 0; i<=yearList.size(); i++) {
			String result = yearList.get(i);
			resultTitleList.add(result);
			listViewTitle.setItems(resultTitleList);
		}
    }
	
    /*
     * setzen von Listen aller benutzten Jahre
     */
	public void statisticYear() {
		System.out.println("OYeC - statisticYear");
		ArrayList<Integer> allreadyCounted = new ArrayList<>();

		sc = new StatisticsController();
		List<Integer> allYears = sc.getAllYears();
		
//		Anfang von Auflistung top Autoren
//		Für jeden Namen in der Liste
		for(int i = 0; i<allYears.size(); i++) {
//			Setze counter auf 0
			int doubleYears = 0;
//			Wenn der Name noch nicht vorkam
			if(!allreadyCounted.contains(allYears.get(i))) {
//			Geh die List mit Autoren Namen durch
				for(int j = 0; j<allYears.size(); j++) {
//					Wenn der Name gleich ist
					if(allYears.get(i).equals(allYears.get(j))) {
//						Zähle counter hoch
						doubleYears++;
					}
				}
				String result = allYears.get(i) + ":  " + doubleYears;
			
				resultList.add(result);
				
				allreadyCounted.add(allYears.get(i));
			}
		}		
//		Setze in eine geordnete Liste (sortiert nach Namen)
		SortedList<String> sortedList = new SortedList(resultList);
		listView.setItems(sortedList.sorted());
	}
	
	/*
	 * Parts aus einem String löschen
	 */
    private String deleteCharAt(String pick, int i) {
		// TODO Auto-generated method stub
		return pick.substring(0, i) + pick.substring(i + 4);
	}
}