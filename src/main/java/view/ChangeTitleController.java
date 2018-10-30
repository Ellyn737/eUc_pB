package view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.controlsfx.control.Rating;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import controller.BibController;
import controller.MainBibliothek;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.Lender;
import models.Book;
import models.Media;
import models.BorrowMedia;

/**
 * class for updating the book with new or changed data
 * 
 * @author ellyn
 *
 */
public class ChangeTitleController {
	@FXML AnchorPane rootPane;
	@FXML Label titleLabel;
	@FXML Button changeImageBtn;
	@FXML ImageView image;
	@FXML TextField txtFiTitle;
	@FXML TextField txtFiSubTitle;
	@FXML TextField txtFiAuthor;
	@FXML TextField txtFiPublisher;
	@FXML TextField txtFiYear;
	@FXML TextField txtFiEdition;
	@FXML SplitMenuButton menuGenre;
	@FXML Menu sbMenu;
	@FXML Menu rMenu;
	@FXML TextArea txtArContent;
	@FXML TextArea txtArComment;
	@FXML Button cancelBtn;
	@FXML Button saveChangeBtn;
	@FXML Rating ratingStars;

		
	private int bookID;
	
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
	private int stars;
	private Boolean ratingIsChanged = false;
	
	private MainBibliothek mainBib;
	private BibController bc;
	private ChangeTitleController ctc;
	
	private List<Integer> resultIds;
	private ArrayList<Pair> oldParameters;

	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}
	
	/**
	 * on cancel go to showTitle
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
		AnchorPane pane = (AnchorPane) loader.load();
		
//		give bookID and old searchParameters and results to ShowTitle
		ShowTitleController showTitle = loader.getController();
		showTitle.fillView(bookID);
		showTitle.setOldParametersForReturning(resultIds, oldParameters);
		
		Scene scene = new Scene(pane);
		rootPane.getChildren().setAll(pane);
		}	
	
	/**
	 * on save check the differences and update the book
	 * 
	 * change isBorrowed only with borrowing
	 * change exemplar only with adding or deleting
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML private void handleSaveChangeButton(ActionEvent event) throws IOException{
			System.out.println("ChangeTitleController - handleSaveChangeButton");
			bc = new BibController();
			
//			get values of input
			title = txtFiTitle.getText();
			subTitle = txtFiSubTitle.getText();
			author = txtFiAuthor.getText();
			publisher = txtFiPublisher.getText();
			year = Integer.parseInt(txtFiYear.getText());			
			content = txtArContent.getText();
			comment = txtArComment.getText();
			edition = Integer.parseInt(txtFiEdition.getText());
			
//			set values
			bc.setAuthor(author);
			bc.setTitle(title);
			bc.setSubTitle(subTitle);
			bc.setPublisher(publisher);
			bc.setYear(year);
			bc.setGenre(genre);
			bc.setSubGenre(subGenre);
			bc.setContent(content);
			bc.setComment(comment);
			bc.setEdition(edition);
			if(ratingIsChanged) {
				bc.setRating(stars);
			}
			
//			change book with id
			bc.changeTitle(bookID);
			
//			load ShowTitle
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
//			give id, old resultlist and searchParameters to Showtitle
			ShowTitleController showTitle = loader.getController();
			showTitle.fillView(bookID);
			showTitle.setOldParametersForReturning(resultIds, oldParameters);
			
			Scene scene = new Scene(pane);
			rootPane.getChildren().setAll(pane);

		}	
	
	@FXML private void handleChangeImageButton(ActionEvent event) throws IOException{

	}	
	
	/**
	 * fills the labels and other variables with the values of the given book
	 * 
	 * @param id
	 */
	public void fillView(int id) {
		try {
			System.out.println("ChangeTitleController - fillView");
			bc = new BibController();
	
			bookID = id;		
//			get the book by bookid
			Book book = bc.getTheBook(id);
			
//			set variables in the fields
			txtFiTitle.setText(book.getTitle());
			txtFiAuthor.setText(book.getAuthor());
			txtFiPublisher.setText(book.getPublisher());
			txtFiYear.setText(String.valueOf(book.getYearOfPublication()));
			txtFiEdition.setText(String.valueOf(book.getEdition()));
			txtArContent.setText(book.getContent());
			txtArComment.setText(book.getComment());
			menuGenre.setText(book.getSubGenre().toString());
			subGenre = book.getSubGenre();
			genre = book.getGenre();
			
			ratingStars.setPartialRating(false);
			ratingStars.setRating(book.getStars());
			
			if(book.getSubTitle() != null) {
				txtFiSubTitle.setText(book.getSubTitle());
			}else {
				txtFiSubTitle.setText("");
			}
			
			
//			set subgenre and genre onClick
			ObservableList<MenuItem> sbItems = sbMenu.getItems();
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
						String rGenreItem = rItem.getText();
						menuGenre.setText(rGenreItem);
						genre = "Roman";
						subGenre = rGenreItem;
						System.out.println(genre);
						System.out.println(subGenre);
					}
			
				});
			}
			
//			set ratingStars onClick
			ratingStars.setOnMouseClicked(new EventHandler<Event>() {
	
				@Override
				public void handle(Event event) {
					ratingIsChanged = true;
					stars = (int) ratingStars.getRating();
				}
			});
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * saves information to return it later
	 * 
	 * @param ids
	 * @param searchParams
	 */
	public void setOldParametersForReturning(List<Integer> ids, ArrayList<Pair> searchParams) {
		resultIds = ids;
		oldParameters = searchParams;
	}
	
	/**
	 * makes it possible to call methods of this class from another controller
	 * 
	 * @return
	 */
	public ChangeTitleController getController() {
		return this.ctc;
	}
}
