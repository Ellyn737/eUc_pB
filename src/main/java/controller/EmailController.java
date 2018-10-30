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

import models.Book;
import models.BorrowMedia;
import models.Lender;


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
		boc = new BorrowController();
		bc = new BibController();
		lc = new LenderController();
		
//		get todays date and the librarian
		today = LocalDate.now();
		Lender librarian = lc.getLender(1);
		
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
			}else {
//				show Alert --> No books need to be returned
			}
		}
		
		
	}
	
	public void sendRememberingEmail(Lender lender, Lender librarian, String message) {
		Email email = EmailBuilder.startingBlank()
			    .from(librarian.getFirstName() + " " + librarian.getLastName(), librarian.getEmail())
			    .to(lender.getFirstName() + " " + lender.getLastName(), lender.getEmail())
			    .withSubject("Buchrückgabe")
			    .withPlainText(message)
			    .buildEmail();

			Mailer mailer = MailerBuilder
					.withDebugLogging(true).buildMailer();
			
//			  .withSMTPServer("smtp.host.com", 587, "user@host.com", "password")
//	          .withTransportStrategy(TransportStrategy.SMTP_TLS)
//	          .withProxy("socksproxy.host.com", 1080, "proxy user", "proxy password")
//	          .withSessionTimeout(10 * 1000)
//	          .clearEmailAddressCriteria() // turns off email validation
//	          .withProperty("mail.smtp.sendpartial", true)
			
			
			mailer.sendMail(email);
	}
	
	
}
