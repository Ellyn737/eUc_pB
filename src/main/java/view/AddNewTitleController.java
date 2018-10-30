package view;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Rating;

import controller.BibController;
import controller.MainBibliothek;
import javafx.application.Platform;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;

/**
 * controller for the view AddNewTitle
 * 
 * @author ellyn
 *
 */
public class AddNewTitleController implements Initializable{
	
	@FXML AnchorPane rootPane;
	@FXML Label titleLabel;
	@FXML Button addImageBtn;
	@FXML ImageView image;
	@FXML TextField txtFiTitle;
	@FXML TextField txtFiSubTitle;
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
	@FXML Rating ratingStars;
	
	private Boolean isBorrowed = false;
	private String title;
	private String subTitle;
	private String author;
	private String publisher;
	private int year;
	private String genre;
	private String subGenre;
	private String content;
	private String comment;
	private int edition;
	private int rating;
	private Boolean isRated = false;
	
	private MainBibliothek mainBib;
	private BibController bc;
	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}
	
	
	/**
	 * sets subgenre, isBorrowed and rating on click
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("AddNewTitleController - initialize");

//		sachbuch auf OnAction
		ObservableList<MenuItem> sbItems = sbMenu.getItems();
		/**
		 * set subgenre to collected subgenre
		 * set genre to the clicked mennu (sb, r)
		 */
		for(MenuItem item: sbItems) {
			item.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					String genreItem = item.getText();
					menuGenre.setText(genreItem);
					genre = "Sachbuch";
					subGenre = genreItem;
				}
		
			});
		}
		
//		novels onAction
		ObservableList<MenuItem> rItems = rMenu.getItems();
		
		for(MenuItem rItem: rItems) {
			rItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					String genreItem = rItem.getText();
					menuGenre.setText(genreItem);
					genre = "Roman";
					subGenre = genreItem;
				}
		
			});
		}
		
//		isBorrowed onClicked
		radioBtnBorrowed.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(radioBtnBorrowed.isSelected()) {
					isBorrowed = true;
					System.out.println("RadioButton wurde gedr�ckt");
				}else {
					isBorrowed = false;
					System.out.println("RadioButton wurde nicht gedr�ckt");
				}				
			}
		});
		
//		set rating as whole stars and with a default value of 0 stars
		ratingStars.setPartialRating(false);
		ratingStars.setRating(0);
//		set ratingStars onClicked
		ratingStars.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				isRated = true;
				rating = (int) ratingStars.getRating();
			}
		});
		
		
		
	}
	
	/**
	 * on cancel go back to StartMenu
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		System.out.println("AddNewTitleController - handleCancelButton");
		AnchorPane startPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
		rootPane.getChildren().setAll(startPane);
		}	
	
	/**
	 * on addTitle add a new book
	 * go to ShowTitle
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleAddTitleButton(ActionEvent event) throws IOException{
		System.out.println("AddNewTitleController - handleAddTitleButton");
		
//		not necessary: rating
		int numberOfNecessaryFields = 9;
		List txtFields = new ArrayList<>();

//		check if all necessary fields are filled out
		System.out.println("Setze Variablen");
		if(!txtFiTitle.getText().isEmpty()) {
			title = txtFiTitle.getText();
			txtFields.add(title);
		}
		if(!txtFiAuthor.getText().isEmpty()) {
			author = txtFiAuthor.getText();
			txtFields.add(author);
		}
		if(!txtFiPublisher.getText().isEmpty()) {
			publisher = txtFiPublisher.getText();
			txtFields.add(publisher);
		}
		if(!txtFiYear.getText().isEmpty()) {
			try {
				year = Integer.parseInt(txtFiYear.getText());
				txtFields.add(year);
			}catch(NumberFormatException ex) {
				String message1 = "Es wird eine Zahl ben�tigt.";
				String message2 = "Bitte geben Sie als Auflage oder Erscheinungsjahr eine Zahl an.";
				setWarning(message1, message2);			}
		}
		if(!txtFiEdition.getText().isEmpty()) {
			try {
				edition = Integer.parseInt(txtFiEdition.getText());
				txtFields.add(edition);
			}catch(NumberFormatException ex) {
				String message1 = "Es wird eine Zahl ben�tigt.";
				String message2 = "Bitte geben Sie als Auflage oder Erscheinungsjahr eine Zahl an.";
				setWarning(message1, message2);
			}
		}
		if(!txtArContent.getText().isEmpty()) {
			content = txtArContent.getText();
			txtFields.add(content);
		}
		if(!txtArComment.getText().isEmpty()) {
			comment = txtArComment.getText();
			txtFields.add(comment);
		}
		if(genre != null) {
			txtFields.add("genre");
		}
		if(subGenre != null) {
			txtFields.add("subGenre");
		}

		if(txtFields.size() == numberOfNecessaryFields) {
			System.out.println("Alles ausgef�llt");
			addBook();
		}else {
			String message1 = "Das Buch ist nicht vollst�ndig.";
			String message2 = "Bitte geben Sie alle erforderlichen Daten an.";
			setWarning(message1, message2);
		}
		

		
	}


	@FXML private void handleAddImageButton(ActionEvent event) throws IOException{
		//add image to title in db
		
		
		
	}

	/**
	 * sets the variables for the new book
	 * calls BibController
	 */
	public void addBook() {
		System.out.println("AddNewTitleController - addBook");
		bc = new BibController();
		try {

//			setze variablen f�r das Buch
			bc.setTitle(title);
			bc.setAuthor(author);
			bc.setPublisher(publisher);
			bc.setYear(year);
			bc.setContent(content);
			bc.setComment(comment);
			bc.setEdition(edition);
			bc.setIsBorrowed(isBorrowed);
			bc.setGenre(genre);
			bc.setSubGenre(subGenre);
			
			if(isRated) {
				bc.setRating(rating);
			}
			
			if(!txtFiSubTitle.getText().isEmpty()) {
				subTitle = txtFiSubTitle.getText();
				bc.setSubTitle(subTitle);
			}
			
//			f�ge das buch der bib hinzu
			bc.addToBib();			
			
//			set Parameters to show at the side and give list with this id
			int id = bc.getLastId();
			System.out.println(id);
			
			List<Integer> ids = new ArrayList<>();
			ids.add(id);
			
			ArrayList<Pair> searchParams = new ArrayList<>();
			searchParams.add(new Pair("title", title));
			
			//zu ShowTitle
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
			ShowTitleController showTitle = loader.getController();
			
			showTitle.fillView(id);
			showTitle.setOldParametersForReturning(ids, searchParams);

			Scene scene = new Scene(pane);
			rootPane.getChildren().setAll(pane);
		
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * sets a warning
	 */
	public void setWarning(String message1, String message2) {
		Alert warning = new Alert(AlertType.WARNING, message2, ButtonType.OK);
		warning.setTitle("ACHTUNG");
		warning.setHeaderText(message1);
		warning.showAndWait();
	}

}
