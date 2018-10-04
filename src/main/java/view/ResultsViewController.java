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
import models.Buch;

public class ResultsViewController implements Initializable {

	@FXML Label titleLabel;
	@FXML Button cancelBtn;
	@FXML Label searchParametersLabel;
	@FXML Label givenTitle;
	@FXML Label givenAutor;
	@FXML Label givenVerlag;
	@FXML Label givenJahr;
	@FXML Label givenGenre;
	@FXML Label givenSubgenre;
	@FXML RadioButton radioBtnAusgeliehen;
	@FXML ListView<ReusablePaneController> list;
	
	
//	ReusablePaneController pane;
	private ReusablePaneController pane;
	private ObservableList<ReusablePaneController> panes = FXCollections.observableArrayList();
	private List<Integer> ids = new ArrayList<>();
	
	private String title;
	private String autor;
	private String verlag;
	private int jahr;
	private String genre;
	private boolean ausgeliehen;
	
	
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
			Buch buch = bc.holeBuchDaten(ids.get(i));
			
//			setze variablen
			title = buch.getTitle();
			autor = buch.getAutor();
			verlag = buch.getVerlag();
			jahr = buch.getErscheinungsjahr();
			genre = buch.getGenre();
			ausgeliehen = buch.getAusgeliehen();
			
//			setze variablen in ReusablePane
			pane.setTitle(title);
			pane.setAutor(autor);
			pane.setVerlag(verlag);
			pane.setJahr(jahr);
			pane.setGenre(genre);
			pane.setAusgeliehen(ausgeliehen);
			
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
