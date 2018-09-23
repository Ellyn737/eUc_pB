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

public class SearchViewController {

	@FXML Label titleLabel;
	@FXML TextField txtFiTitle;
	@FXML TextField txtFiAutor;
	@FXML TextField txtFiVerlag;
	@FXML TextField txtFiJahr;
	@FXML TextField txtFiAuflage;
	@FXML TextField txtFiExemplar;
	@FXML SplitMenuButton menuGenre;
	@FXML RadioButton radioBtnIsThere;
	@FXML Button cancelBtn;
	@FXML Button searchBtn;
	
	
	private int buchID;
	
	private String title;
	private String autor;
	private String verlag;
	private String jahr;
	private String genre;
	private String inhalt;
	private String kommentar;
	private String auflage;
	private String exemplar;
	private String inBib;
	
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
	
	@FXML private void handleSearchButton(ActionEvent event) throws IOException{
		//ausserdem suchparameter weiter und an db geben
		bc = new BibController();
		
		title = txtFiTitle.getText();
		autor = txtFiAutor.getText();
		verlag = txtFiVerlag.getText();
		jahr = txtFiJahr.getText();
		genre = menuGenre.getText();
		auflage = txtFiAuflage.getText();
		exemplar = txtFiExemplar.getText();
		
		if(radioBtnIsThere.isPressed()) {
			inBib = "false";
		}else {
			inBib = "true";
		}
		
		//Suchparameter in Array uebergeben
		ArrayList<String> parameters = new ArrayList();
		parameters.add(title);
		parameters.add(autor);
		parameters.add(verlag);
		parameters.add(jahr);
		parameters.add(genre);
		parameters.add(auflage);
		parameters.add(exemplar);
		parameters.add(inBib);
		
		try {
			
//			List mit Ids holen, die zu den Suchparametern passen
			List<Integer> ids = bc.findeBuchID(parameters);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//Liste mit ids an ResultsView uebergeben
		
		
		
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/ResultsView.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
	
}
