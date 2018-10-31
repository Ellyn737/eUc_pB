package demo;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

public class TestEmail {

	public static void main(String[] args) {
		Email email = EmailBuilder.startingBlank()
			    .from("Ellyn Reese-Bünning", "e.reese@mail.de")
			    .to("Florian Bünning", "e.reese@mail.de")
			    .withSubject("Buchrückgabe")
			    .withPlainText("Hallo Flo, dies ist eine Testmessage. Kuss Ellyn")
			    .buildEmail();

			Mailer mailer = MailerBuilder
					.withSMTPServer("smtp.mail.de", 587, "e.reese@mail.de", "mondIN737")
					.withTransportStrategy(TransportStrategy.SMTP_TLS)
					.withSessionTimeout(10 * 1000)
					.withDebugLogging(true).buildMailer();
			
//			  
//	          
//	          .withProxy("socksproxy.host.com", 1080, "proxy user", "proxy password")
//	          .clearEmailAddressCriteria() // turns off email validation
//	          .withProperty("mail.smtp.sendpartial", true)
			
//			mailer.testConnection();
			mailer.sendMail(email);

	}

}
