package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import controller.BibController;
import controller.MainBibliothek;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AddNewTitleController implements Initializable{
	
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
	@FXML Menu sbMenu;
	@FXML Menu rMenu;
	
	private Boolean isBorrowed = false;
	private String title;
	private String author;
	private String publisher;
	private int year;
	private String genre;
	private String subGenre;
	private String content;
	private String comment;
	private int edition;
	
	private MainBibliothek mainBib;
	private BibController bc;
		
	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}
	
	
	/**
	 * setzen von genre und subgenre bei klick
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("ANTC - initialize");

		ObservableList<MenuItem> sbItems = sbMenu.getItems();
		/**
		 * subgenre wird auf das ausgewählte subgenre gesetzt
		 * genre wird entsprechend des angeklickten menues gesetzt (sb, r)
		 */
		for(MenuItem item: sbItems) {
			item.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					String genreItem = item.getText();
					menuGenre.setText(genreItem);
					genre = "Sachbuch";
					subGenre = genreItem;
					System.out.println(genre);
					System.out.println(subGenre);
				}
		
			});
		}
		
		ObservableList<MenuItem> rItems = rMenu.getItems();
		
		for(MenuItem rItem: rItems) {
			rItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					String genreItem = rItem.getText();
					menuGenre.setText(genreItem);
					genre = "Roman";
					subGenre = genreItem;
					System.out.println(genre);
					System.out.println(subGenre);
				}
		
			});
		}
		
//		setzen von isBorrowed on click
		radioBtnBorrowed.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(radioBtnBorrowed.isSelected()) {
					isBorrowed = true;
					System.out.println("RadioButton wurde gedrückt");
				}else {
					isBorrowed = false;
					System.out.println("RadioButton wurde nicht gedrückt");
				}				
			}
		});
		
	}
	
	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		System.out.println("ANTC - handleCancelButton");
		Parent searchPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
		Scene searchScene = new Scene(searchPane);
		
		Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
		window.setScene(searchScene);
		window.show();
		}	
	
	@FXML private void handleAddTitleButton(ActionEvent event) throws IOException{
		System.out.println("ANTC - handleAddTitleButton");

		bc = new BibController();
		
		//add the title to db
		//hole Strings mit Textfeldinhalten
		title = txtFiTitle.getText();
		author = txtFiAuthor.getText();
		publisher = txtFiPublisher.getText();
		year = Integer.parseInt(txtFiYear.getText());
		content = txtArContent.getText();
		comment = txtArComment.getText();
		edition = Integer.parseInt(txtFiEdition.getText());
		
		System.out.println("Vor der Uebergabe an BC");
		System.out.println("Genre: " +genre);
		System.out.println("Subgenre: " +subGenre);
		System.out.println("isBorrowed: " + isBorrowed);
		
		//values an bc uebergeben zur db-uebergabe
		bc.setAuthor(author);
		bc.setTitle(title);
		bc.setPublisher(publisher);
		bc.setYear(year);
		bc.setGenre(genre);
		bc.setSubGenre(subGenre);
		bc.setContent(content);
		bc.setComment(comment);
		bc.setIsBorrowed(isBorrowed);
		bc.setEdition(edition);
		
		//buch an db uebergeben
		try {
			bc.addToBib();
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		//zu ShowTitle
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
		Parent root = (Parent) loader.load();
		
		//id an ResultsView uebergeben
		ShowTitleController showTitle = loader.getController();
		try {
			int id = bc.getLastId();
			showTitle.fillView(id);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
		
		}	
	
	@FXML private void handleAddImageButton(ActionEvent event) throws IOException{
		//add image to title in db
		
		
		
	}


}
