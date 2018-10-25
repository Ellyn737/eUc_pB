package view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import models.Rating;
import models.Book;
import models.Media;
import models.BorrowMedia;

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
	
	private MainBibliothek mainBib;
	private BibController bc;
	private ChangeTitleController ctc;
	
	private List<Integer> resultIds;
	private ArrayList<Pair> oldParameters;

	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}
	
	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ResultsView.fxml"));
		AnchorPane pane = (AnchorPane) loader.load();
		
		//id an ResultsView uebergeben
		ResultsViewController resultsView = loader.getController();
		resultsView.fillListAndView(resultIds, oldParameters);
		
		Scene scene = new Scene(pane);
		rootPane.getChildren().setAll(pane);
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
			bc.setGenre(genre);
			bc.setSubGenre(subGenre);
			bc.setContent(content);
			bc.setComment(comment);
			bc.setEdition(edition);
//			inBIb nur �ber Ausleihe �ndern
//			exemplar nur ueber hinzuf�gen oder l�schen �ndern
			
			try {
				bc.changeTitle(bookID);
				
				FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
				AnchorPane pane = (AnchorPane) loader.load();
				
				//id an ResultsView uebergeben
				ShowTitleController showTitle = loader.getController();
				showTitle.fillView(bookID);
				
				Scene scene = new Scene(pane);
				rootPane.getChildren().setAll(pane);
				
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
	
	public void setOldParametersForReturning(List<Integer> ids, ArrayList<Pair> searchParams) {
		resultIds = ids;
		oldParameters = searchParams;
	}
	
	/**
	 * erm�glicht die uebergabe von daten von einem anderen FXController an diesen
	 * 
	 * @return
	 */
	public ChangeTitleController getController() {
		return this.ctc;
	}
}
