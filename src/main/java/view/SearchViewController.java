package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.BibController;
import controller.MainBibliothek;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Pair;

public class SearchViewController {

	@FXML Label titleLabel;
	@FXML TextField txtFiTitle;
	@FXML TextField txtFiAuthor;
	@FXML TextField txtFiPublisher;
	@FXML TextField txtFiYear;
	@FXML TextField txtFiEdition;
	@FXML TextField txtFiExemplar;
	@FXML SplitMenuButton menuGenre;
	@FXML RadioButton radioBtnBorrowed;
	@FXML Button cancelBtn;
	@FXML Button searchBtn;
	
	
	private int bookID;
	
	private String title;
	private String author;
	private String publisher;
	private String year;
	private String genre;
	private String content;
	private String comment;
	private String edition;
	private String exemplar;
	private String isBorrowed;
	
	private MainBibliothek mainBib;
	private BibController bc;
	private ResultsViewController resultsView;

	
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
	
	@FXML private void handleSearchButton(ActionEvent event) throws IOException{
		//ausserdem suchparameter weiter und an db geben
		bc = new BibController();
	
		
		title = txtFiTitle.getText().trim();
		author = txtFiAuthor.getText().trim();
		publisher = txtFiPublisher.getText().trim();
		year = txtFiYear.getText().trim();
		genre = menuGenre.getText().trim();
		edition = txtFiEdition.getText().trim();
		exemplar = txtFiExemplar.getText().trim();
		
		if(radioBtnBorrowed.isPressed()) {
			isBorrowed = "false";
		}else {
			isBorrowed = "true";
		}
				
//		Suchparameter in Array uebergeben
/*		ArrayList<String> parameters = new ArrayList();
 * 
 * 		parameters.add(title);
		parameters.add(autor);
		parameters.add(verlag);
		parameters.add(jahr);
		parameters.add(genre);
		parameters.add(auflage);
		parameters.add(exemplar);
		parameters.add(ausgeliehen);
 */
		
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
			
			//Liste mit ids an ResultsView uebergeben
			resultsView.setIds(ids);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
		
		
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/ResultsView.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
	
}
