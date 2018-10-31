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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.Book;

/**
 * gets information from SearchView
 * lists results of the search in a ListView
 * 
 * @author ellyn
 *
 */
public class ResultsViewController {
	@FXML AnchorPane rootPane;
	@FXML Label titleLabel;
	@FXML Button cancelBtn;
	@FXML Label searchParametersLabel;
	@FXML TextArea txtArSearchParams;
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
	private String ratingString;
	
	private List<Integer> theIds;
	ArrayList<Pair> oldParameters;
		
	private MainBibliothek mainBib;
	private BibController bc;
	private ResultsViewController resContr;
	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}

	/**
	 * on cancel go to StartMenu
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		System.out.println("ResultsViewController - handleCancelButton");
		AnchorPane startPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
		rootPane.getChildren().setAll(startPane);
		}


	/**
	 * gets data from SearchView
	 * calls setSearchParameters and setListView
	 * 
	 * @param ids
	 * @param searchParams
	 */
	public void fillListAndView(List<Integer> ids, ArrayList<Pair> searchParams) {
		System.out.println("ResultsViewController - FillListAndView");
		
		theIds = ids;
		oldParameters = searchParams;

		setSearchParameters(searchParams);
		setListView(ids);
		
	}
	
	/**
	 * sets given searchParameters at the side in a Label
	 * 
	 * @param parameters
	 */
	public void setSearchParameters(ArrayList<Pair> parameters) {
		System.out.println("ResultsViewController - setSearchParameters");

		String result = "";
	
//		analyses the given parameters and sets the string accordingly
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
				case "rating":
					ratingString = parameters.get(i).getValue().toString();
					result += "Bewertungssterne: " + ratingString + "\r\n";
					break;
					
			}
				
		}
		
//		sets the text
		txtArSearchParams.setText(result);
		
	}
	
	/**
	 * calls getBookDataForList and sets resultString in listView
	 * 
	 * @param ids
	 */
	public void setListView(List<Integer> ids) {
		System.out.println("ResultsViewController - setListView");
		
		ObservableList<String> list = FXCollections.observableArrayList();
		
//		get book data
		for(int i = 0; i < ids.size(); i++) {					
	 		list.add(getTheBookDataForList(ids.get(i)));
		}
		
//		set book data into the listView
		listView.setItems(list);	

		
//		listView on MouseClicked
		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				try {
//					get the id with the index
					Integer listIndex = listView.getSelectionModel().getSelectedIndex(); 
					Integer clickedId = theIds.get(listIndex);
					
					FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
					AnchorPane pane = (AnchorPane) loader.load();
					
//					give id to showTitle
					ShowTitleController showTitle = loader.getController();
					showTitle.fillView(clickedId);
					showTitle.setOldParametersForReturning(theIds, oldParameters);
					
					Scene scene = new Scene(pane);
					rootPane.getChildren().setAll(pane);

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

	}
	
	/**
	 * gets the data for the searched book
	 * sets a string to be shown in the listView
	 * 
	 * @param id
	 * @return
	 */
	public String getTheBookDataForList(int id) {
		System.out.println("ResultsViewController - getTheBookDataForList");
		
		String resultListString = "";
		String isB = "";
		
		try {
			bc = new BibController();
			
//			get the book with the bookid
			Book book = bc.getTheBook(id);
		
//			set variables
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

//			set string
			resultListString += title + ", " + author + ", " + year + ", " + publisher + ", " + isB;
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			return resultListString;
			
		}	

	/**
	 * makes data transfer between different controllers possible 
	 * 
	 * @return
	 */
	public ResultsViewController getController() {
		return this.resContr;
	}

	
}
