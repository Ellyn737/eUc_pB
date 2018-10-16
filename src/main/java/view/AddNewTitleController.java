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
	@FXML TextField txtFiAuthor;
	@FXML TextField txtFiPublisher;
	@FXML TextField txtFiYear;
	@FXML TextField txtFiEdition;
	@FXML SplitMenuButton menuGenre;
	@FXML RadioButton radioBtnBorrowed;
	@FXML TextArea txtArContent;
	@FXML TextArea txtArComment;
	@FXML Button cancelBtn;
	@FXML Button addTitleBtn;
	
	private Boolean isBorrowed;
	private String title;
	private String author;
	private String publisher;
	private int year;
	private String genre;
	private String content;
	private String comment;
	private int edition;
	
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
		author = txtFiAuthor.getText();
		publisher = txtFiPublisher.getText();
		year = Integer.parseInt(txtFiYear.getText());
		genre = menuGenre.getText();
		content = txtArContent.getText();
		comment = txtArComment.getText();
		edition = Integer.parseInt(txtFiEdition.getText());
		
		if(radioBtnBorrowed.isPressed()) {
			isBorrowed = false;
		}else {
			isBorrowed = true;
		}
		
		//values an bc uebergeben zur db-uebergabe
		bc.setAuthor(author);
		bc.setTitle(title);
		bc.setPublisher(publisher);
		bc.setYear(year);
		bc.setGenre(genre);
		bc.setContent(content);
		bc.setComment(comment);
		bc.setIsBorrowed(isBorrowed);
		bc.setEdition(edition);
		
		//buch an db uebergeben
		bc.addToBib();
		
		//zu ShowTitle
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
		Parent root = (Parent) loader.load();
		
		//id an ResultsView uebergeben
		ShowTitleController showTitle = loader.getController();
		int id = bc.getLastId();
		showTitle.fillView(id);
		
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		
		}	
	
	@FXML private void handleAddImageButton(ActionEvent event) throws IOException{
		//add image to title in db
		
		
		
	}
	
}
