package view;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import controller.BibController;
import controller.MainBibliothek;
import controller.SingletonFactory;
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
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import models.Ausleiher;
import models.Bewertung;
import models.Buch;
import models.Media;
import models.MediumAusleihen;
import javafx.stage.Stage;

public class AddNewTitleController {
	
	@FXML Label titleLabel;
	@FXML Button addImageBtn;
	@FXML ImageView image;
	@FXML TextField txtFiTitle;
	@FXML TextField txtFiAutor;
	@FXML TextField txtFiVerlag;
	@FXML TextField txtFiJahr;
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
	
	private MainBibliothek mainBib;
	private BibController bc;
	
	
	private SessionFactory factory;
	
	
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
		jahr = Integer.valueOf(txtFiJahr.getText());
		genre = menuGenre.getText();
		
		//speichern in einer Bewertung
		inhalt = txtArInhalt.getText();
		kommentar = txtArKommentar.getText();
		
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
