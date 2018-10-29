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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;



public class OverviewAuthorController implements Initializable{
	
	@FXML private Button backBtn;
	

	@FXML AnchorPane rootPane;
	
	@FXML ListView<String> listView;
	
	private String title;
	private String author;
	private String publisher;
	private int year;
	private String yearString;
	private String genre;
	private String subGenre;
	private String editionString;
	private String exemplarString;
	private boolean isBorrowed;
	private String isBorrowedString;
	
	private List<Integer> theIds;
	ArrayList<Pair> oldParameters;
	
	private MainBibliothek mainBib;
	private BibController bc;

	public void setMain(MainBibliothek mainBib){
		this.mainBib = mainBib;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		System.out.println("ANTC - initialize");
		
	}
	
	/**
	 * setzt die suchParameter am Rand als String
	 * 
	 * @param parameters
	 */
	public void setSearchParameters(ArrayList<Pair> parameters) {
		System.out.println("RVC - setSearchParameters");

		String result = "";
	
		for(int i = 0; i < parameters.size(); i++) {
			String key = parameters.get(i).getKey().toString();
			switch(key) {
				case "title":
					title = parameters.get(i).getValue().toString();
					result += "Titel: " + title + "\r\n";
					break;
				case "author":
					author = parameters.get(i).getValue().toString();
					result += "Autor: " + author + "\r\n";
					break;			
				case "publisher":
					publisher = parameters.get(i).getValue().toString();
					result += "Verlag: " + publisher + "\r\n";
					break;	
				case "year":
					yearString = parameters.get(i).getValue().toString();
					result += "Erscheinungsjahr: " + yearString + "\r\n";
					break;
				case "genre":
					genre = parameters.get(i).getValue().toString();
					result += "Genre: " + genre + "\r\n";
					break;
				case "subGenre":
					subGenre = parameters.get(i).getValue().toString();
					result += "Subgenre: " + subGenre + "\r\n";
					break;
				case "edition":
					editionString = parameters.get(i).getValue().toString();
					result += "Auflage: " + editionString + "\r\n";
					break;
				case "exemplar":
					exemplarString = parameters.get(i).getValue().toString();
					result += "Exemplar: " + exemplarString + "\r\n";
					break;
				case "isBorrowed":
					isBorrowedString = parameters.get(i).getValue().toString();
					if(isBorrowedString == "0") {
						isBorrowedString = "nein";
					}else {
						isBorrowedString = "ja";
					}
					result += "Ausgeliehen: " + isBorrowedString + "\r\n";
					break;
			}
		}
			
		System.out.println(result);
		
	}
	
	/**
	 * ruft getBookDataForList auf und setzt Ergebnisstring in ListView
	 * 
	 * @param ids
	 */
	public void setListView() {
		System.out.println("RVC - setListView");
		System.out.println(theIds);
		
//		panel an die liste mit panels uebergeben
		ObservableList<String> list = FXCollections.observableArrayList();
		
//		hole die buchdaten und setze sie in die listView
		for(int i = 0; i < theIds.size(); i++) {					
			System.out.println("Setze Ergebnisstring in Liste für id: " + theIds.get(i));
	 		list.add(getTheBookDataForList(theIds.get(i)));
		}
		
		System.out.println("Setze Liste");
		listView.setItems(list);	
		System.out.println(list);

		System.out.println("setze MouseEvent");
		/**
		 * beim Mausklick auf ein item in der Liste
		 * den index holen und damit die Id holen
		 * id an ShowTitleController geben und ShowTitle anzeigen
		 */
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
					Integer listIndex = listView.getSelectionModel().getSelectedIndex(); 
					Integer clickedId = theIds.get(listIndex);
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
					AnchorPane pane = (AnchorPane) loader.load();
					
					//id an ResultsView uebergeben
					ShowTitleController showTitle = loader.getController();
					showTitle.fillView(clickedId);
					showTitle.setOldParametersForReturning(theIds, oldParameters);
					
					Scene scene = new Scene(pane);
					rootPane.getChildren().setAll(pane);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

	}
	

	//BackButton
	@FXML private void handleBackButton(ActionEvent event) throws IOException{
		System.out.println("ANTC - handleBackButton");
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/Overview.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
}

