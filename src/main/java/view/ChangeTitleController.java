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
import models.Lender;
import models.Rating;
import models.Book;
import models.Media;
import models.BorrowMedia;

public class ChangeTitleController {

	@FXML Label titleLabel;
	@FXML Button changeImageBtn;
	@FXML ImageView image;
	@FXML TextField txtFiTitle;
	@FXML TextField txtFiAuthor;
	@FXML TextField txtFiPublisher;
	@FXML TextField txtFiYear;
	@FXML TextField txtFiEdition;
	@FXML TextField txtFiExemplar;
	@FXML SplitMenuButton genreMenu;
	@FXML TextArea txtArContent;
	@FXML TextArea txtArComment;
	@FXML Button cancelBtn;
	@FXML Button saveChangeBtn;
		
	private int bookID;
	
	private String title;
	private String author;
	private String publisher;
	private int year;
	private String genre;
	private String content;
	private String comment;
	private int edition;
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
			author = txtFiAuthor.getText();
			publisher = txtFiPublisher.getText();
			year = Integer.parseInt(txtFiYear.getText());
			genre = genreMenu.getText();
			content = txtArContent.getText();
			comment = txtArComment.getText();
			edition = Integer.parseInt(txtFiEdition.getText());
			exemplar = Integer.parseInt(txtFiExemplar.getText());
			
			//get buchId von ShowTitle
			
			
			//setze values
			bc.setAuthor(author);
			bc.setTitle(title);
			bc.setPublisher(publisher);
			bc.setYear(year);
			bc.setGenre(genre);
			bc.setContent(content);
			bc.setComment(comment);
			bc.setEdition(edition);
			bc.setExemplar(exemplar);
			//inBIb nur über Ausleihe ändern
			
			//daten zur auswertung an bc uebergeben
			bc.changeTitle(bookID);
						
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
