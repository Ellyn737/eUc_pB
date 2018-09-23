package view;

import java.io.IOException;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AddNewTitleController {
	
	@FXML Label titleLabel;
	@FXML Button addImageBtn;
	@FXML ImageView image;
	@FXML TextField txtFiTitle;
	@FXML TextField txtFiAutor;
	@FXML TextField txtFiVerlag;
	@FXML TextField txtFiJahr;
	@FXML TextField txtFiAuflage;
	@FXML SplitMenuButton menuGenre;
	@FXML RadioButton radioBtnIsThere;
	@FXML TextArea txtArInhalt;
	@FXML TextArea txtArKommentar;
	@FXML Button cancelBtn;
	@FXML Button addTitleBtn;
	
	private Boolean inBib;
	private String title;
	private String autor;
	private String verlag;
	private int jahr;
	private String genre;
	private String inhalt;
	private String kommentar;
	private int auflage;
	
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
	
	@FXML private void handleAddTitleButton(ActionEvent event) throws IOException{
		bc = new BibController();
		//add the title to db
		//hole Strings mit Textfeldinhalten
		title = txtFiTitle.getText();
		autor = txtFiAutor.getText();
		verlag = txtFiVerlag.getText();
		jahr = Integer.parseInt(txtFiJahr.getText());
		genre = menuGenre.getText();
		inhalt = txtArInhalt.getText();
		kommentar = txtArKommentar.getText();
		auflage = Integer.parseInt(txtFiAuflage.getText());
		
		if(radioBtnIsThere.isPressed()) {
			inBib = false;
		}else {
			inBib = true;
		}
		
		//values an bc uebergeben zur db-uebergabe
		bc.setAutor(autor);
		bc.setTitle(title);
		bc.setVerlag(verlag);
		bc.setJahr(jahr);
		bc.setGenre(genre);
		bc.setInhalt(inhalt);
		bc.setKommentar(kommentar);
		bc.setInBib(inBib);
		bc.setAuflage(auflage);
		
		//buch an db uebergeben
		bc.aufnehmenInBib();
	
		//zu ShowTitle
		Parent titlePane = FXMLLoader.load(getClass().getResource("../view/ShowTitle.fxml"));
		Scene titleScene = new Scene(titlePane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(titleScene);
		window.show();
		
		}	
	
	@FXML private void handleAddImageButton(ActionEvent event) throws IOException{
		//add image to title in db
		
		
		
	}
	
}
