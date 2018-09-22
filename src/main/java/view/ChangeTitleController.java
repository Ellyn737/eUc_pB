package view;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
import models.Ausleiher;
import models.Bewertung;
import models.Buch;
import models.Media;
import models.MediumAusleihen;

public class ChangeTitleController {

	@FXML Label titleLabel;
	@FXML Button changeImageBtn;
	@FXML ImageView image;
	@FXML TextField txtFiTitle;
	@FXML TextField txtFiAutor;
	@FXML TextField txtFiVerlag;
	@FXML TextField txtFiJahr;
	@FXML TextField txtFiAuflage;
	@FXML TextField txtFiExemplar;
	@FXML SplitMenuButton genreMenu;
	@FXML RadioButton radioBtnIsThere;
	@FXML TextArea txtArInhalt;
	@FXML TextArea txtArKommentar;
	@FXML Button cancelBtn;
	@FXML Button saveChangeBtn;
		
	private int buchID;
	
	private String title;
	private String autor;
	private String verlag;
	private int jahr;
	private String genre;
	private String inhalt;
	private String kommentar;
	private int auflage;
	private int exemplar;
	
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
	
	@FXML private void handleSaveChangeButton(ActionEvent event) throws IOException{
			//save the changes to db
			bc = new BibController();
			
			title = txtFiTitle.getText();
			autor = txtFiAutor.getText();
			verlag = txtFiVerlag.getText();
			jahr = Integer.parseInt(txtFiJahr.getText());
			genre = genreMenu.getText();
			inhalt = txtArInhalt.getText();
			kommentar = txtArKommentar.getText();
			auflage = Integer.valueOf(txtFiAuflage.getText());
			exemplar = Integer.valueOf(txtFiExemplar.getText());
			
			//get buchId von ShowTitle
			
			
			//setze values
			bc.setAutor(autor);
			bc.setTitle(title);
			bc.setVerlag(verlag);
			bc.setJahr(jahr);
			bc.setGenre(genre);
			bc.setInhalt(inhalt);
			bc.setKommentar(kommentar);
			bc.setAuflage(auflage);
			bc.setExemplar(exemplar);
			//inBIb nur über Ausleihe ändern
			
			//daten zur auswertung an bc uebergeben
			bc.titelBearbeiten(buchID);
						
			//zu ShowTitle
			Parent titlePane = FXMLLoader.load(getClass().getResource("../view/ShowTitle.fxml"));
			Scene titleScene = new Scene(titlePane);
			
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(titleScene);
			window.show();
		
		}	
	
	@FXML private void handleChangeImageButton(ActionEvent event) throws IOException{
		//change image of title in db
	}	
}
