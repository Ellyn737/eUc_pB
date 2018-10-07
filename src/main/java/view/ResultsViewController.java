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
import javafx.stage.Stage;
import models.Book;

public class ResultsViewController implements Initializable {

	@FXML Label titleLabel;
	@FXML Button cancelBtn;
	@FXML Label searchParametersLabel;
	@FXML Label givenTitle;
	@FXML Label givenAuthor;
	@FXML Label givenPublisher;
	@FXML Label givenYear;
	@FXML Label givenGenre;
	@FXML Label givenSubgenre;
	@FXML RadioButton radioBtnBorrowed;
	@FXML ListView<ReusablePaneController> list;
	
	
//	ReusablePaneController pane;
	private ReusablePaneController pane;
	private ObservableList<ReusablePaneController> panes = FXCollections.observableArrayList();
	private List<Integer> ids = new ArrayList<>();
	
	private String title;
	private String author;
	private String publisher;
	private int year;
	private String genre;
	private boolean isBorrowed;
	
	
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
	
	
	//funktion zum öffnen bei klicken auf titel

	
	/*
	 * When the view starts --> smth is done
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

//		für jede id aus ids
		for(int i = 0; i < ids.size(); i++) {
//			hole das Buch
			Book buch = bc.getBookData(ids.get(i));
			
//			setze variablen
			title = buch.getTitle();
			author = buch.getAuthor();
			publisher = buch.getPublisher();
			year = buch.getYearOfPublication();
			genre = buch.getGenre();
			isBorrowed = buch.getIsBorrowed();
			
//			setze variablen in ReusablePane
			pane.setTitle(title);
			pane.setAuthor(author);
			pane.setPublisher(publisher);
			pane.setYear(year);
			pane.setGenre(genre);
			pane.setIsBorrowed(isBorrowed);
			
//			panel an die liste mit panels uebergeben
			panes.add(pane);
			

		}
		
		try {
			
			ReusablePaneController rpc = new ReusablePaneController();
//			load the fxml file
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ReusablePane.fxml"));
			loader.setController(rpc);
			Parent root = loader.load();
			
//			fülle ListView in ResultsView mit ReusablePanes
			list.setItems(panes);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
	}	
	
	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}


	
}
