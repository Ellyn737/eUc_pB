package view;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import controller.LenderController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.util.Pair;

public class NewLenderViewController {

	@FXML AnchorPane rootPane;
	@FXML Label titleLabel;
	@FXML TextField txtFiFirstName;
	@FXML TextField txtFiLastName;
	@FXML TextField txtFiEmail;
	@FXML Button cancelButton;
	@FXML Button saveLenderButton;
	
	private String email;
	private String firstName;
	private String lastName;
	
	private int id;
	private List<Integer> resultIds;
	private ArrayList<Pair> oldParameters;
	private NewLenderViewController nlvc;
	private LenderController lc;
	
	@FXML private void handleCancelButton(ActionEvent event) throws IOException{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("TitleBorrow.fxml"));
		AnchorPane pane = (AnchorPane) loader.load();
		
		//id an ResultsView uebergeben
		TitleBorrowController titleborrow = loader.getController();
		titleborrow.fillView(id);
		titleborrow.setOldParametersForReturning(resultIds, oldParameters);
		
		Scene scene = new Scene(pane);
		rootPane.getChildren().setAll(pane);
	}
	
	@FXML private void handleSaveNewLenderButton(ActionEvent event) throws IOException{
		lc = new LenderController();		
		
		checkInput();
		
		
	}
	
	
	public void checkInput(){
		ArrayList<Pair> params = new ArrayList<>();
//		check if all textFields are filled
		if(!txtFiEmail.getText().isEmpty()) {
			email = txtFiEmail.getText();	
			params.add(new Pair("Email", email));
		}
		
		if(!txtFiFirstName.getText().isEmpty()) {
			firstName = txtFiFirstName.getText();
			params.add(new Pair("Firstname", firstName));
		}
		
		if(!txtFiLastName.getText().isEmpty()) {
			lastName = txtFiLastName.getText();
			params.add(new Pair("Lastname", lastName));
		}
		
		if(params.size() == 3) {
//			check if lender allready exists
			ArrayList<Integer> emailIds = lc.searchForSameLenderEmail(email);
			ArrayList<Integer> nameIds = lc.searchForSameLenderName(firstName, lastName);
			
			if(emailIds.size() > 0) {
//				Warning email allready exists
				setWarningEmailExists();
				
			}
			if(nameIds.size() > 0) {
//				Warning user allready exists
//				same user??? --> new lender or changeLender or cancel? 
				setWarningNameExists();
				
			}
			
			if(emailIds.size() == 0 && nameIds.size() == 0) {
				lc.addNewLender(params);
			}
		}
	}
	
	public void goToChangeLender() {
		
	}
	
	public void setWarningNameExists() {
		Alert warning = new Alert(AlertType.WARNING, "Möchten Sie den Ausleiher anpassen?", ButtonType.YES, ButtonType.NO);
		warning.setTitle("ACHTUNG");
		warning.setHeaderText("Der Name ist bereits vergeben.");
		warning.showAndWait();
		
		if(warning.getResult() == ButtonType.YES) {
//			show Dialog with TextField --> aendern der id
		}else {
//			go To TitleBorrowCOntroller
		}
	}
	
	public void setWarningEmailExists() {
		Alert warning = new Alert(AlertType.WARNING, "Möchten Sie den Ausleiher anpassen?", ButtonType.YES, ButtonType.NO);
		warning.setTitle("ACHTUNG");
		warning.setHeaderText("Die Email ist bereits vergeben.");
		warning.showAndWait();
		
		if(warning.getResult() == ButtonType.YES) {
//			show Dialog with TextField --> aendern der id

		}else {
//			go To TitleBorrowCOntroller

		}
	}
	
	public void setId(int id) {
		System.out.println("NLVC - setId");
		this.id = id;
	}
	
	public void setOldParametersForReturning(List<Integer> ids, ArrayList<Pair> searchParams) {		
		System.out.println("NLVC - setOldParametersForReturning");
		resultIds = ids;
		oldParameters = searchParams;
	}
	
	public NewLenderViewController getController() {
		return this.nlvc;
	}
	
}
