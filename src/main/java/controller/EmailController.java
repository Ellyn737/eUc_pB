package controller;

import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import models.Book;
import models.BorrowMedia;
import models.Lender;
import models.Librarian;


/**
 * 
 * @author ellyn
 *
 * checks the date and if reminders need to be sent
 */
public class EmailController {
	
	private LocalDate today;
	private Date returnDate;
	
	private String defaultMessage;
	private String givenMessage;
	
	private BorrowController boc;
	private LenderController lc;
	private BibController bc;
	
	
	public void checkOverdueReturns() {
		System.out.println("EmailController - checkOverdueReturns");
		boc = new BorrowController();
		bc = new BibController();
		lc = new LenderController();
		
//		get todays date and the librarian
		today = LocalDate.now();
		Librarian librarian = lc.getLibrarian();
		
//		check if returnDates are overdue 
		List<BorrowMedia> bms = boc.getBorrowingByDate(today);

		for(BorrowMedia bm: bms) {
//			check if isReturned is false
			if(!bm.getIsReturned()) {
	//			get the lender
				int lenderId = bm.getIdLender();
				Lender lender = lc.getLender(lenderId);
				
	//			get the book
				int bookId = bm.getIdMedia();
				Book book = bc.getTheBook(bookId);
				
	//			set message
				String message = bm.getMessage();
				
	//			send email
				sendRememberingEmail(lender, librarian, message);
				
	//			set information alert
				String message1 = "Die Ausleihfrist wurde von "+ lender.getFirstName() + " " + lender.getLastName() +"überschritten.";
				String message2 = "Eine Erinnerungsemail wurde versandt.";
				setWarningOverdue(message1, message2);
			}else {
				String message1 = "Zur Zeit werden keine Ausleihfristen überschritten.";
				String message2 = "Alles ist gut.";
				setWarningOverdue(message1, message2);
			}
		}
	
	}
	
	public void sendRememberingEmail(Lender lender, Librarian librarian, String message) {
		System.out.println("EmailController - sendRememberingEmail");

		Email email = EmailBuilder.startingBlank()
			    .from(librarian.getFirstName() + " " + librarian.getLastName(), librarian.getEmail())
			    .to(lender.getFirstName() + " " + lender.getLastName(), lender.getEmail())
			    .withSubject("Buchrückgabe")
			    .withPlainText(message)
			    .buildEmail();

			Mailer mailer = MailerBuilder
					.withSMTPServer("smtp.mail.de", 587, librarian.getEmail(), librarian.getEmailPW())
					.withTransportStrategy(TransportStrategy.SMTP_TLS)
					.withSessionTimeout(10 * 1000)
					.withDebugLogging(true).buildMailer();
			
//			mailer.testConnection();
			mailer.sendMail(email);
	}
	
	/**
	 * Alert --> are there books that are overdue?
	 */
	public void setWarningOverdue(String headerMsg, String otherMsg) {
		System.out.println("EmailController - setWarningOverdue");

		Alert warning = new Alert(AlertType.WARNING, otherMsg, ButtonType.OK);
		warning.setTitle("ACHTUNG");
		warning.setHeaderText(headerMsg);
		warning.showAndWait();
		if(warning.getResult() == ButtonType.OK) {
			warning.close();
		}
	}
	
	
}
