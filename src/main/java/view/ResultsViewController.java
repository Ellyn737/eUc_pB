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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.Book;

public class ResultsViewController{

	@FXML Label titleLabel;
	@FXML Button cancelBtn;
	@FXML Label searchParametersLabel;
	@FXML TextArea txtArSearchParams;
	@FXML ListView<String> listView;
	
//	ReusablePaneController pane;
//	private List<Integer> ids;
	
	private String title;
	private String author;
	private String publisher;
	private int year;
	private String yearString;
	private String genre;
	private String editionString;
	private String exemplarString;
	private boolean isBorrowed;
	private String isBorrowedString;
		
	private ResultsViewController resContr;
	
	private MainBibliothek mainBib;
	private BibController bc;
	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}

	
	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}


	/**
	 * empfaengt die Daten von SearchView
	 * ruft setSearchParameters und setListView auf
	 * 
	 * @param ids
	 * @param searchParams
	 */
	public void fillListAndView(List<Integer> ids, ArrayList<Pair> searchParams) {
		System.out.println("RVC - In FillListAndView");
		
		System.out.println("Setze die gesuchten Parameter");
		setSearchParameters(searchParams);
		
		System.out.println("Setzte die ListView");
		setListView(ids);
		
		
	}
	
	/**
	 * setzt die suchParameter am Rand als String
	 * 
	 * @param parameters
	 */
	public void setSearchParameters(ArrayList<Pair> parameters) {
		System.out.println("RVC - In setSearchParameters");

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
						isBorrowedString = "ja";
					}else {
						isBorrowedString = "nein";
					}
					result += "Ausgeliehen: " + isBorrowedString + "\r\n";
					break;
			}
		}
			
		System.out.println(result);
		txtArSearchParams.setText(result);
		
	}
	
	/**
	 * ruft getBookDataForList auf und setzt Ergebnisstring in ListView
	 * 
	 * @param ids
	 */
	public void setListView(List<Integer> ids) {
		System.out.println("RVC - In setListView");
		System.out.println(ids);
		
//		panel an die liste mit panels uebergeben
		ObservableList<String> list = FXCollections.observableArrayList();
		
//		hole die buchdaten und setze sie in die listView
		for(int i = 0; i < ids.size(); i++) {					
			System.out.println("Setze Ergebnisstring in Liste für id: " + ids.get(i));
	 		list.add(getTheBookDataForList(ids.get(i)));
		}
		
		System.out.println("Setze Liste");
		listView.setItems(list);	
		System.out.println(list);

		System.out.println("setze MouseEvent");
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
//				Integer listIndex = listView.getSelectionModel().getSelectedIndex(); 
//				System.out.println("index: " + listIndex);
				
				String listString = listView.getSelectionModel().getSelectedItem();
				System.out.println("ausgewählter String: " + listString);
				
				
			}
		});

	}
	
	/**
	 * holt die daten für das gesuchte Buch und setzt einen String, der in der ListView angezeigt werden kann
	 * 
	 * @param id
	 * @return
	 */
	public String getTheBookDataForList(int id) {
		System.out.println("RVC - getTheBookDataForPanel");
		
		String resultListString = "";
		String isB = "";
			
			System.out.println(id);
			bc = new BibController();
			Book book = bc.getBookData(id);
			
//			setze variablen
			title = book.getTitle();
			System.out.println(title);
			
			author = book.getAuthor();
			System.out.println(author);
			
			publisher = book.getPublisher();
			System.out.println(publisher);
			
			year = book.getYearOfPublication();
			System.out.println(year);
			
			isBorrowed = book.getIsBorrowed();
			System.out.println(isBorrowed);
			
			if(isBorrowed) {
				isB = "ausgeliehen";
			}else {
				isB = "verfügbar";
			}

//			setze String
			resultListString += title + ", " + author + ", " + year + ", " + publisher + ", " + isB + " [" + id + "]";
			System.out.println(resultListString);
			
			return resultListString;
			
		}	

	/**
	 * ermöglicht die uebergabe von daten von einem anderen FXController an diesen
	 * 
	 * @return
	 */
	public ResultsViewController getController() {
		return this.resContr;
	}

	
}
