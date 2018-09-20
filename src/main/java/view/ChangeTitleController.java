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
		
	public int buchId;
	
	public String title;
	public String autor;
	public String verlag;
	public int jahr;
	public String genre;
	public String inhalt;
	public String kommentar;
	
	public MainBibliothek mainBib;
	public BibController bc = new BibController();
	
	
	
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
			
		}	
	
	@FXML private void handleChangeImageButton(ActionEvent event) throws IOException{
		//change image of title in db
	}	
}
