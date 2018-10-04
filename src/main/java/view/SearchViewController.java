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
	@FXML TextField txtFiAutor;
	@FXML TextField txtFiVerlag;
	@FXML TextField txtFiJahr;
	@FXML TextField txtFiAuflage;
	@FXML TextField txtFiExemplar;
	@FXML SplitMenuButton menuGenre;
	@FXML RadioButton radioBtnAusgeliehen;
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
	private String ausgeliehen;
	
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
		autor = txtFiAutor.getText().trim();
		verlag = txtFiVerlag.getText().trim();
		jahr = txtFiJahr.getText().trim();
		genre = menuGenre.getText().trim();
		auflage = txtFiAuflage.getText().trim();
		exemplar = txtFiExemplar.getText().trim();
		
		if(radioBtnAusgeliehen.isPressed()) {
			ausgeliehen = "false";
		}else {
			ausgeliehen = "true";
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
		ArrayList<Pair> parameter = new ArrayList<Pair>();
		
		
		if(!title.isEmpty()) {
			Pair titlePair = new Pair("title", title);
			parameter.add(titlePair);
			System.out.println(title);
		}
		
		if(!autor.isEmpty()) {
			Pair autorPair = new Pair("autor", autor);
			parameter.add(autorPair);
			System.out.println(autor);
		}
		
		if(!verlag.isEmpty()) {
			Pair verlagPair = new Pair("verlag", verlag);
			parameter.add(verlagPair);		
			System.out.println(verlag);	
		}
		
		if(!jahr.isEmpty()) {
			Pair jahrPair = new Pair("jahr", jahr);
			parameter.add(jahrPair);
			System.out.println(jahr);
		}
		
		if(!genre.isEmpty()) {
			Pair genrePair = new Pair("genre", genre);
			parameter.add(genrePair);
			System.out.println(genre);
		}
		
		if(!auflage.isEmpty()) {
			Pair auflagePair = new Pair("auflage", auflage);
			parameter.add(auflagePair);	
			System.out.println(auflage);
			}
		
		if(!exemplar.isEmpty()) {
			Pair exemplarPair = new Pair("exemplar", exemplar);
			parameter.add(exemplarPair);
			System.out.println(exemplar);
		}
		
		if(!ausgeliehen.isEmpty()) {
			Pair ausgeliehenPair = new Pair("ausgeliehen", ausgeliehen);
			parameter.add(ausgeliehenPair);
			System.out.println(ausgeliehen);
		}
		
		for(int j = 0; j < parameter.size();j++) {
			System.out.println(parameter.get(j));
		}
	
		try {
			
//			List mit Ids holen, die zu den Suchparametern passen
			List<Integer> ids = bc.findeBuchID(parameter);
			
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
