package view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import controller.BibController;
import controller.MainBibliothek;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;

public class SearchViewController implements Initializable {

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
	
	private MainBibliothek mainBib;
	private BibController bc;
	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}

	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		System.out.println("SVC - handleCancelButton");

		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
	
	@FXML private void handleSearchButton(ActionEvent event) throws IOException{
		System.out.println("SVC - handleSaveButton");
		//ausserdem suchparameter weiter und an db geben
		bc = new BibController();
	
		
		title = txtFiTitle.getText().trim();
		author = txtFiAuthor.getText().trim();
		publisher = txtFiPublisher.getText().trim();
		year = txtFiYear.getText().trim();
		edition = txtFiEdition.getText().trim();
		exemplar = txtFiExemplar.getText().trim();
		
		if(radioBtnBorrowed.isPressed()) {
			isBorrowed = "0";
		}else {
			isBorrowed = "1";
		}
		
		
//		ArrayListe mit key und value anlegen
		ArrayList<Pair> parameters = new ArrayList<Pair>();
		
		
		if(!title.isEmpty()) {
			Pair titlePair = new Pair("title", title);
			parameters.add(titlePair);
			System.out.println(title);
		}
		
		if(!author.isEmpty()) {
			Pair authorPair = new Pair("author", author);
			parameters.add(authorPair);
			System.out.println(author);
		}
		
		if(!publisher.isEmpty()) {
			Pair publisherPair = new Pair("publisher", publisher);
			parameters.add(publisherPair);		
			System.out.println(publisher);	
		}
		
		if(!year.isEmpty()) {
			Pair yearPair = new Pair("year", year);
			parameters.add(yearPair);
			System.out.println(year);
		}
		
		if(!genre.isEmpty()) {
			Pair genrePair = new Pair("genre", genre);
			parameters.add(genrePair);
			System.out.println(genre);
			if(!subGenre.isEmpty()) {
				Pair subGenrePair = new Pair("subGenre", subGenre);
				parameters.add(subGenrePair);
				System.out.println(subGenre);
			}
		}
		
		if(!edition.isEmpty()) {
			Pair editionPair = new Pair("edition", edition);
			parameters.add(editionPair);	
			System.out.println(edition);
			}
		
		if(!exemplar.isEmpty()) {
			Pair exemplarPair = new Pair("exemplar", exemplar);
			parameters.add(exemplarPair);
			System.out.println(exemplar);
		}
		
		if(!isBorrowed.isEmpty()) {
			Pair isBorrowedPair = new Pair("isBorrowed", isBorrowed);
			parameters.add(isBorrowedPair);
			System.out.println(isBorrowed);
		}
		
		for(int j = 0; j < parameters.size();j++) {
			System.out.println(parameters.get(j));
		}
	
		try {
			
//			List mit Ids holen, die zu den Suchparametern passen
			List<Integer> ids = bc.findBookId(parameters);
			System.out.println("Ids: " + ids);

		
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultsView.fxml"));
			Parent root = (Parent) loader.load();
			
			//Liste mit ids an ResultsView uebergeben
			ResultsViewController resultsView = loader.getController();
			resultsView.fillListAndView(ids, parameters);
			
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * genre  und subgenre auswerten
	 * @param location
	 * @param resources
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("SVC - initialize");
		
		ObservableList<MenuItem> genreItems = menuGenre.getItems();
		for(MenuItem genreItem: genreItems ) {
			genreItem.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					
					String genreIt = genreItem.getText();
					menuGenre.setText(genreIt);
					genre = genreIt;
					System.out.println(genre);
					
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
					System.out.println(subGenre);
					
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
					System.out.println(subGenre);
					
				}
			});
		}
		
	}	
	
	
}
