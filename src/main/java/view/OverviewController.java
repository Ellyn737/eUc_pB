package view;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

import controller.BibController;
import controller.MainBibliothek;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class OverviewController implements Initializable{
	
	@FXML AnchorPane rootPane;
	@FXML private Label titleLabel;
	@FXML private Button authorBtn;
	@FXML private Button genreBtn;
	@FXML private Button publisherBtn;
	@FXML private Button yearBtn;
	@FXML private Button ratingBtn;
	@FXML private Button backBtn;
	
	private MainBibliothek mainBib;

	public void setMain(MainBibliothek mainBib){
		this.mainBib = mainBib;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resource) {
		System.out.println("OC - initialize");
		
	}
	
//	AuthorButton
	@FXML private void handleAuthorButton(ActionEvent event) throws IOException{
		
		AnchorPane addPane = FXMLLoader.load(getClass().getResource("../view/OverviewAuthor.fxml"));
		rootPane.getChildren().setAll(addPane);
		}
//	GenreButton
		@FXML private void handleGenreButton(ActionEvent event) throws IOException{
			AnchorPane addPane = FXMLLoader.load(getClass().getResource("../view/OverviewGenre.fxml"));
			rootPane.getChildren().setAll(addPane);
	}
//	PublisherButton
	@FXML private void handlePublisherButton(ActionEvent event) throws IOException{
		AnchorPane addPane = FXMLLoader.load(getClass().getResource("../view/OverviewPublisher.fxml"));
		rootPane.getChildren().setAll(addPane);
		}
//	YearButton
	@FXML private void handleYearButton(ActionEvent event) throws IOException{
		AnchorPane addPane = FXMLLoader.load(getClass().getResource("../view/OverviewYear.fxml"));
		rootPane.getChildren().setAll(addPane);
		}
//	RatingButton
	@FXML private void handleRatingButton(ActionEvent event) throws IOException{
		AnchorPane addPane = FXMLLoader.load(getClass().getResource("../view/OverviewRating.fxml"));
		rootPane.getChildren().setAll(addPane);
		}
//	BackButton
	@FXML private void handleBackButton(ActionEvent event) throws IOException{
		AnchorPane addPane = FXMLLoader.load(getClass().getResource("../view/StartMenu.fxml"));
		rootPane.getChildren().setAll(addPane);
		}
}