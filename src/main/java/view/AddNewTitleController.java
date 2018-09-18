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
	@FXML SplitMenuButton menuGenre;
	@FXML RadioButton radioBtnIsThere;
	@FXML TextArea txtArInhalt;
	@FXML TextArea txtArKommentar;
	@FXML Button cancelBtn;
	@FXML Button addTitleBtn;
	
	public Boolean inBib;
	public String title;
	public String autor;
	public String verlag;
	public int jahr;
	public String genre;
	public String inhalt;
	public String kommentar;
	
	public MainBibliothek mainBib;
	
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
			//add the title to db
			//hole Strings mit Textfeldinhalten
			title = txtFiTitle.getText();
			autor = txtFiAutor.getText();
			verlag = txtFiVerlag.getText();
			jahr = Integer.valueOf(txtFiJahr.getText());
			genre = menuGenre.getText();
			inhalt = txtArInhalt.getText();
			kommentar = txtArKommentar.getText();
			
			if(radioBtnIsThere.isPressed()) {
				inBib = true;
			}else {
				inBib = false;
			}
		}	
	
	@FXML private void handleAddImageButton(ActionEvent event) throws IOException{
		//add image to title in db
	}

	public Boolean getInBib() {
		return inBib;
	}


	public String getTitle() {
		return title;
	}


	public String getAutor() {
		return autor;
	}


	public String getVerlag() {
		return verlag;
	}


	public int getJahr() {
		return jahr;
	}


	public String getGenre() {
		return genre;
	}


	public String getInhalt() {
		return inhalt;
	}

	public String getKommentar() {
		return kommentar;
	}

	
	
	
	


	
}
