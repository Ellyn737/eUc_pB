package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Rating;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import controller.BibController;
import controller.MainBibliothek;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * manages the search options
 * 
 * @author ellyn
 *
 */
public class SearchViewController implements Initializable {

	@FXML AnchorPane rootPane;
	@FXML Label titleLabel;
	@FXML TextField txtFiTitle;
	@FXML TextField txtFiAuthor;
	@FXML TextField txtFiPublisher;
	@FXML TextField txtFiYear;
	@FXML TextField txtFiEdition;
	@FXML TextField txtFiExemplar;
	@FXML SplitMenuButton menuGenre;
	@FXML Menu sbMenu;
	@FXML Menu rMenu;
	@FXML RadioButton radioBtnBorrowed;
	@FXML Button cancelBtn;
	@FXML Button searchBtn;
	@FXML Rating ratingStars;
	
	
	private int bookID;
	private String title;
	private String author;
	private String publisher;
	private String year;
	private String genre;
	private String subGenre;
	private String content;
	private String comment;
	private String edition;
	private String exemplar;
	private String isBorrowed;
	private int rating;
	private Boolean ratingIsClicked = false;
	
	private MainBibliothek mainBib;
	private BibController bc;
	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}
	
	/**
	 * checks genre, subgenre, rating and isBorrowed
	 * 
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("SearchViewController - initialize");
		
//		set subgenre and genre onclick
		ObservableList<MenuItem> genreItems = menuGenre.getItems();
		for(MenuItem genreItem: genreItems ) {
			genreItem.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					
					String genreIt = genreItem.getText();
					menuGenre.setText(genreIt);
					genre = genreIt;					
				}
			});
		}
		
		ObservableList<MenuItem> sbSubGenreItems = sbMenu.getItems();
		for(MenuItem sbSubGenreItem: sbSubGenreItems ) {
			sbSubGenreItem.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					
					String subGenreIt = sbSubGenreItem.getText();
					menuGenre.setText(subGenreIt);
					subGenre = subGenreIt;					
				}
			});
		}
		
		ObservableList<MenuItem> rSubGenreItems = sbMenu.getItems();
		for(MenuItem rSubGenreItem: rSubGenreItems ) {
			rSubGenreItem.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					
					String subGenreIt = rSubGenreItem.getText();
					menuGenre.setText(subGenreIt);
					subGenre = subGenreIt;
					
				}
			});
		}
		
//		set isBorrowed onClicked
		radioBtnBorrowed.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(radioBtnBorrowed.isSelected()) {
//					1 == true
					isBorrowed = "1";
				}else {
//					0 == false
					isBorrowed = "0";
				}				
			}
		});
		
		
//		set rating onClicked
		ratingStars.setPartialRating(false);
		ratingStars.setRating(0);
		
		ratingStars.setOnMouseClicked(new EventHandler<Event>() {
		
			@Override
			public void handle(Event event) {
				ratingIsClicked = true;
				rating = (int) ratingStars.getRating();
			}
		});
		
	}	

	/**
	 * on cancel go to StartMenu
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		System.out.println("SearchViewController - handleCancelButton");
		AnchorPane startPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
		rootPane.getChildren().setAll(startPane);
		}	
	
	/**
	 * on search get searchParameters
	 * call bibController
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleSearchButton(ActionEvent event) throws IOException{
		System.out.println("SearchViewController - handleSearchButton");
		bc = new BibController();
	
//		get searchParameters
		title = txtFiTitle.getText().trim();
		author = txtFiAuthor.getText().trim();
		publisher = txtFiPublisher.getText().trim();
		year = txtFiYear.getText().trim();
		edition = txtFiEdition.getText().trim();
		exemplar = txtFiExemplar.getText().trim();
		
//		generate ArrayListe with key and value
		ArrayList <Pair> parameters = new ArrayList<Pair>();
		
//		analyse input and add Pair with key and value
		if(!title.isEmpty()) {
			Pair titlePair = new Pair("title", title);
			parameters.add(titlePair);
		}
		
		if(!author.isEmpty()) {
			Pair authorPair = new Pair("author", author);
			parameters.add(authorPair);
		}
		
		if(!publisher.isEmpty()) {
			Pair publisherPair = new Pair("publisher", publisher);
			parameters.add(publisherPair);		
		}
		
		if(!year.isEmpty()) {
			Pair yearPair = new Pair("year", year);
			parameters.add(yearPair);
		}
		
		System.out.println(genre);
		if(genre != null) {
			Pair genrePair = new Pair("genre", genre);
			parameters.add(genrePair);
		}
		
		System.out.println(subGenre);
		if(subGenre != null) {
			Pair subGenrePair = new Pair("subGenre", subGenre);
			parameters.add(subGenrePair);
		}

		if(!edition.isEmpty()) {
			Pair editionPair = new Pair("edition", edition);
			parameters.add(editionPair);	
		}
		
		if(!exemplar.isEmpty()) {
			Pair exemplarPair = new Pair("exemplar", exemplar);
			parameters.add(exemplarPair);
		}
		
		if(isBorrowed != null) {
			Pair isBorrowedPair = new Pair("isBorrowed", isBorrowed);
			parameters.add(isBorrowedPair);
		}
	
		if(ratingIsClicked) {
			parameters.add(new Pair("rating", rating));
		}
		
//		if parameters were not set
		if(parameters.size() <= 0) {
//			showWarning
			String message1 = "Es wurden keine Suchangaben gemacht.";
			String message2 = "Bitte füllen Sie mindestens einen Suchparameter aus.";
			setWarning(message1, message2);
		}else {
//			else get matching bookids
			List<Integer> ids = bc.findBookId(parameters);
//			go to findBook
			findBook(ids, parameters);
		}
	}


	/**
	 * 
	 * @param ids
	 * @param searchParameters
	 */
	public void findBook(List<Integer> ids, ArrayList<Pair> searchParameters) {
		System.out.println("SearchViewController - findBook");

//			checks if ids were found
			if(ids.size() >= 1) {
//				yes --> give ids and searchParameters to ResultsView and show them there
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultsView.fxml"));
					AnchorPane pane = (AnchorPane) loader.load();
	
					ResultsViewController resultsView = loader.getController();
					resultsView.fillListAndView(ids, searchParameters);
					
					Scene scene = new Scene(pane);
					rootPane.getChildren().setAll(pane);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else {
//				--> no setWarning
				String message1 = "Es wurden keine passenden Titel gefunden.";
				String message2 = "Bitte überprüfen Sie Ihre Sucheingabe.";
				setWarning(message1, message2);
			}
	}
	
	/**
	 * sets a warning
	 */
	public void setWarning(String message1, String message2) {
		System.out.println("SearchViewController - setWarning");
//		Alert fuer moegliche fehlende Eingaben
		Alert warning = new Alert(AlertType.WARNING, message2, ButtonType.OK);
		warning.setTitle("ACHTUNG");
		warning.setHeaderText(message1);
		warning.showAndWait();
	}
	
}
