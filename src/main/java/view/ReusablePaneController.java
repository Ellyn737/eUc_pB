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
	@FXML TextField txtFiAutor;
	@FXML TextField txtFiJahr;
	@FXML TextField txtFiVerlag;
	@FXML TextField txtFiExemplar;
	@FXML TextField txtFiAuflage;
	@FXML SplitMenuButton menuGenre;
	@FXML RadioButton radioBtnAusgeliehen;
	
	
	
	//Felder die im Pane enthalten sind und die titel anzeigen
	//Verwendung in ResultView, evtl uach bei showTitle
	private int id;
	private String title;
	private String autor;
	private String verlag;
	private int jahr;
	private int auflage;
	private int exemplar;
	private String genre;
	private boolean ausgeliehen;
	
	private String jahrString;
	private String exemplarString;
	private String auflageString;
	
	
	public ReusablePaneController() {
		
		jahrString = Integer.toString(jahr);
		exemplarString = Integer.toString(exemplar);
		auflageString = Integer.toString(auflage);
		
//		setze Variablen in die TextFelder
		txtFiTitle.setText(title);
		txtFiAutor.setText(autor);
		txtFiJahr.setText(jahrString);
		txtFiVerlag.setText(verlag);
		txtFiExemplar.setText(exemplarString);
		txtFiAuflage.setText(auflageString);
		menuGenre.setText(genre);
		
		if(ausgeliehen) {
			radioBtnAusgeliehen.setSelected(true);
		}else {
			radioBtnAusgeliehen.setSelected(false);
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

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getVerlag() {
		return verlag;
	}

	public void setVerlag(String verlag) {
		this.verlag = verlag;
	}

	public int getJahr() {
		return jahr;
	}

	public void setJahr(int jahr) {
		this.jahr = jahr;
	}

	public int getAuflage() {
		return auflage;
	}

	public void setAuflage(int auflage) {
		this.auflage = auflage;
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

	public boolean isAusgeliehen() {
		return ausgeliehen;
	}

	public void setAusgeliehen(boolean ausgeliehen) {
		this.ausgeliehen = ausgeliehen;
	}
	
}
