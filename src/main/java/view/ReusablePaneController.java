package view;

import java.io.IOException;
import java.text.MessageFormat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class ReusablePaneController extends AnchorPane {
	
	@FXML Pane titleShower;
	@FXML TextField txtFiTitle;
	@FXML TextField txtFiAuthor;
	@FXML TextField txtFiYear;
	@FXML TextField txtFiPublisher;
	@FXML TextField txtFiExemplar;
	@FXML TextField txtFiEdition;
	@FXML SplitMenuButton menuGenre;
	@FXML RadioButton radioBtnBorrowed;
	
	
	
	//Felder die im Pane enthalten sind und die titel anzeigen
	//Verwendung in ResultView, evtl uach bei showTitle
	private int id;
	private String title;
	private String author;
	private String publisher;
	private int year;
	private int edition;
	private int exemplar;
	private String genre;
	private boolean isBorrowed;
	
	private String yearString;
	private String exemplarString;
	private String editionString;
	
	
	public ReusablePaneController() {
		
		yearString = Integer.toString(year);
		exemplarString = Integer.toString(exemplar);
		editionString = Integer.toString(edition);
		
//		setze Variablen in die TextFelder
		txtFiTitle.setText(title);
		txtFiAuthor.setText(author);
		txtFiYear.setText(yearString);
		txtFiPublisher.setText(publisher);
		txtFiExemplar.setText(exemplarString);
		txtFiEdition.setText(editionString);
		menuGenre.setText(genre);
		
		if(isBorrowed) {
			radioBtnBorrowed.setSelected(true);
		}else {
			radioBtnBorrowed.setSelected(false);
		}
		
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReusablePane.fxml"));
		
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
		try {
			fxmlLoader.load();
		}catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getEdition() {
		return edition;
	}

	public void setEdition(int edition) {
		this.edition = edition;
	}

	public int getExemplar() {
		return exemplar;
	}

	public void setExemplar(int exemplar) {
		this.exemplar = exemplar;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public boolean getIsBorrowed() {
		return isBorrowed;
	}

	public void setIsBorrowed(boolean isBorrowed) {
		this.isBorrowed = isBorrowed;
	}
	
}
