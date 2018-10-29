package view;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import controller.BibController;
import controller.BorrowController;
import controller.LenderController;
import controller.MainBibliothek;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import models.Book;
import models.BorrowMedia;
import models.Lender;


public class TitleReturnController {
	@FXML AnchorPane rootPane;
	@FXML Label titleLabel;
	@FXML Label givenTitle;
	@FXML ImageView givenImage;
	@FXML Label givenAuthor;
	@FXML Label givenPublisher;
	@FXML Label givenYear;
	@FXML Label borrowerLbl;
	@FXML Label returnDateLbl;
	@FXML Label messageLbl;
	@FXML Button giveBackBtn;
	@FXML Button cancelBtn;
	
	
	private MainBibliothek mainBib;
	private BibController bc;
	private TitleReturnController trc;
	private BorrowController boC;
	private LenderController lc;

	private List<Integer> resultIds;
	private ArrayList<Pair> oldParameters;
	private int titleId;
	private LocalDate returnDate;
	
	public void setMain(MainBibliothek mainBib) {
		this.mainBib = mainBib;
	}
	
	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
		AnchorPane pane = (AnchorPane) loader.load();
		
		ShowTitleController showTitle = loader.getController();
		showTitle.fillView(titleId);
		showTitle.setOldParametersForReturning(resultIds, oldParameters);
		
		Scene scene = new Scene(pane);
		rootPane.getChildren().setAll(pane);
		}	
	
	@FXML private void handleGiveBackButton(ActionEvent event) throws IOException{
		//datum checken und auf IsThere setzen, nutzer austragen bei titel
		boC = new BorrowController();
		
		boC.returnBook(titleId);
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowTitle.fxml"));
		AnchorPane pane = (AnchorPane) loader.load();
		
		//id an ResultsView uebergeben
		ShowTitleController showPane = loader.getController();
		
		showPane.fillView(titleId);
		showPane.setOldParametersForReturning(resultIds, oldParameters);
		
		Scene scene = new Scene(pane);
		rootPane.getChildren().setAll(pane);
		}
	
	public void fillView(int id) {
		System.out.println("TReC - fillView");
		titleId = id;
		bc = new BibController();

		Book book = bc.getTheBook(titleId);
//		set parameters on the left side
		System.out.println("Setze Variablen");
		givenTitle.setText(book.getTitle().toUpperCase());
		givenAuthor.setText(book.getAuthor());
		givenPublisher.setText(book.getPublisher());
		givenYear.setText(String.valueOf(book.getYearOfPublication()));
		
		boC = new BorrowController();
//		get the last borrowing of the title
		System.out.println("Hole BorrowMedia");
		BorrowMedia borrowing = boC.getLastBorrowingOfTitle(titleId);
		
		System.out.println(borrowing.toString());
		
		System.out.println("Setze returnDate und die Tage drüber");
//		get returnDate and compare to now
		returnDate = borrowing.getReturnDate().toLocalDate();
		long daysOver = boC.getDaysOverReturnDate(returnDate);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.LLLL.yyyy");
		returnDateLbl.setText("Rückgabedatum: " + returnDate.format(formatter) + "\r\nDas Buch ist " + daysOver + " Tage drüber.");

		System.out.println("Hole ausleiher");
		lc = new LenderController();
//		set lender
		Lender lender = lc.getLender(borrowing.getIdLender());
		System.out.println("Setze ausleiher");
		borrowerLbl.setText("Von: " + lender.getFirstName()  + " " + lender.getLastName());
		
//		set message
		System.out.println("Setze message");
		String msg = borrowing.getMessage();
		messageLbl.setText(msg);
		
	}
	
	public void setOldParametersForReturning(List<Integer> ids, ArrayList<Pair> searchParams) {
		resultIds = ids;
		oldParameters = searchParams;
	}
	
	public TitleReturnController getController() {
		return this.trc;
	}
}
