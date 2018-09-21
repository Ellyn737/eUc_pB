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
	@FXML SplitMenuButton genreMenu;
	@FXML RadioButton radioBtnIsThere;
	@FXML TextArea txtArInhalt;
	@FXML TextArea txtArKommentar;
	@FXML Button cancelBtn;
	@FXML Button saveChangeBtn;
		
	private int buchId;
	
	private String title;
	private String autor;
	private String verlag;
	private int jahr;
	private String genre;
	private String inhalt;
	private String kommentar;
	
	private MainBibliothek mainBib;
	private BibController bc = new BibController();
	
	SessionFactory factory = new Configuration().configure("hibernate.cfg.remote.xml").addPackage("models").
			addAnnotatedClass(Media.class).addAnnotatedClass(Buch.class).
			addAnnotatedClass(Ausleiher.class).addAnnotatedClass(Bewertung.class).
			addAnnotatedClass(MediumAusleihen.class).buildSessionFactory();
	
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
		
			title = txtFiTitle.getText();
			autor = txtFiAutor.getText();
			verlag = txtFiVerlag.getText();
			jahr = Integer.parseInt(txtFiJahr.getText());
			genre = genreMenu.getText();
			inhalt = txtArInhalt.getText();
			kommentar = txtArKommentar.getText();
			
			//get buchId
			
			
			//get buchData from buchId
			try {
				Session session = factory.getCurrentSession();
				session.beginTransaction();
				//aendere Daten
				
				Buch buch = (Buch)session.get(Buch.class, buchId);
			
				if(title != "") {
					buch.setTitle(title);
					System.out.println("neuer Titel: " + title);
				}
				
				if(genre != "") {
					buch.setGenre(genre);
					System.out.println("neues Genre: " + genre);
				}
				
				if(jahr != 0) {
					buch.setErscheinungsjahr(jahr);
					System.out.println("neues Erscheinungsjahr: " + jahr);
				}
				
				if(autor != "") {
				buch.setAutor(autor);
				System.out.println("neuer Autor: " + autor);
				}
				
				if(verlag != "") {
					buch.setVerlag(verlag);
					System.out.println("neuer Verlag: " + verlag);
				}
				
				//inBIb hier nicht aendern
				if(jahr == 0 && genre == "" && title == "" && autor == "" && verlag == ""){
					System.out.println("Es wurde nichts verändert.");
				}
				
				session.getTransaction().commit();
			}catch (Exception e) {
				System.out.println("Fehler!");

			}
			
			//zu ShowTitle
			Parent titlePane = FXMLLoader.load(getClass().getResource("../view/ShowTitle.fxml"));
			Scene titleScene = new Scene(titlePane);
			
			Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
			window.setScene(titleScene);
			window.show();
			
			
			/*
			//values an bibController uebergeben
			bc.setTitle(title);
			bc.setAutor(autor);
			bc.setVerlag(verlag);
			bc.setJahr(jahr);
			bc.setGenre(genre);
			bc.setInhalt(inhalt);
			bc.setKommentar(kommentar);
			
			//buchId holen
			int buchId = bc.findeBuchID(autor, title);
			//veraenderungen speichern
			bc.titelBearbeiten(buchId);
			*/
		}	
	
	@FXML private void handleChangeImageButton(ActionEvent event) throws IOException{
		//change image of title in db
	}	
}
