package view;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import controller.BibController;
import controller.MainBibliothek;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
	
	private MainBibliothek mainBib;
	private BibController bc;
	private ChangeTitleController ctc;

	
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
			System.out.println("CTC - handleSaveChangeButton");
//			bc = new BibController();
			
			title = txtFiTitle.getText();
			subTitle = txtFiSubTitle.getText();
			author = txtFiAuthor.getText();
			publisher = txtFiPublisher.getText();
			year = Integer.parseInt(txtFiYear.getText());			
			content = txtArContent.getText();
			comment = txtArComment.getText();
			edition = Integer.parseInt(txtFiEdition.getText());
			
			//setze values
			bc.setAuthor(author);
			bc.setTitle(title);
			bc.setSubTitle(subTitle);
			bc.setPublisher(publisher);
			bc.setYear(year);
			System.out.println("Vor Übergabe an BC: " + genre + "/ " + subGenre);
			bc.setGenre(genre);
			bc.setSubGenre(subGenre);
			bc.setContent(content);
			bc.setComment(comment);
			bc.setEdition(edition);
//			inBIb nur über Ausleihe ändern
//			exemplar nur ueber hinzufügen oder löschen ändern
			
			try {
				bc.changeTitle(bookID);
				
				//zurück zu ShowTitle
				FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
				Parent root = (Parent) loader.load();
				
				//id an ResultsView uebergeben
				ShowTitleController showTitle = loader.getController();
				showTitle.fillView(bookID);
				
				Stage stage = new Stage();
				stage.setScene(new Scene(root));
				stage.show();
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
			
		
		}	
	
	@FXML private void handleChangeImageButton(ActionEvent event) throws IOException{
		//change image of title in db
		System.out.println("CTC - handleChangeImageButton");
	}	
	
	public void fillView(int id) {
		System.out.println("CTC - fillView");
		bc = new BibController();

		bookID = id;
		
		try {
			Book book = bc.getTheBook(bookID);
			
			txtFiTitle.setText(book.getTitle());
			txtFiAuthor.setText(book.getAuthor());
			txtFiPublisher.setText(book.getPublisher());
			txtFiYear.setText(String.valueOf(book.getYearOfPublication()));
			txtFiEdition.setText(String.valueOf(book.getEdition()));
			txtArContent.setText(book.getContent());
			txtArComment.setText(book.getComment());
			menuGenre.setText(book.getSubGenre().toString());
			if(book.getSubTitle() != null) {
				txtFiSubTitle.setText(book.getSubTitle());
			}else {
				txtFiSubTitle.setText("");
			}
			
			subGenre = book.getSubGenre();
			genre = book.getGenre();
			
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	/**
	 * ermöglicht die uebergabe von daten von einem anderen FXController an diesen
	 * 
	 * @return
	 */
	public ChangeTitleController getController() {
		return this.ctc;
	}
}
