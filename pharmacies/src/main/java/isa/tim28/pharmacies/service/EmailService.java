package isa.tim28.pharmacies.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import isa.tim28.pharmacies.model.User;


@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;
	
	@Async
	public void sendMailAsync(User user, String siteURL) throws MessagingException {
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
		System.out.println("Slanje emaila...");
		
		String content = "Pozdrav " + user.getName() + ",<br>"
	            + "Molimo Vas da posetite link ispod kako biste potvrdili Vasu registraciju:<br>"
	            + "<h3><a href=\"[[URL]]\" target=\"_self\">POTVRDI</a></h3>"
	            + "Hvala Vam,<br>"
	            + "ISA, TIM 28.";
		String verifyURL = siteURL + "/auth/verify?id=" + user.getId();
		
		content = content.replace("[[URL]]", verifyURL);
		
		MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    helper.setFrom(env.getProperty("spring.mail.username"));
	    helper.setTo(user.getEmail());
	    helper.setSubject("Molimo Vas da potvrdite registraciju.");
	    helper.setText(content, true);
	    
		javaMailSender.send(message);

		System.out.println("Email poslat!");
		
	}
	
	@Async
	public void sendMedicineEmailAsync(String name, String address, String medicineCodes) throws MessagingException {
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
		System.out.println("Slanje emaila...");
		String content = "Pozdrav " + name + ",<br>"
	            + "Prepisani su Vam lekovi:<br>"
	            + "<h3>" + medicineCodes + "</h3>"
	            + "Hvala Vam,<br>"
	            + "ISA, TIM 28.";
		
		
		MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    helper.setFrom(env.getProperty("spring.mail.username"));
	    helper.setTo(address);
	    helper.setSubject("Recept");
	    helper.setText(content, true);
	    
		javaMailSender.send(message);

		System.out.println("Email poslat!");	
	}
	
	@Async
	public void sendReservationReceivedEmailAsync(String name, String address, long reservationId) throws MessagingException {
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
		System.out.println("Slanje emaila...");
		String content = "Pozdrav " + name + ",<br>"
	            + "Rezervisani lek je preuzet." + "<br>"
	            + "Broj rezervacije: " + reservationId + "<br>"
	            + "Hvala Vam,<br>"
	            + "ISA, TIM 28.";
		
		
		MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    helper.setFrom(env.getProperty("spring.mail.username"));
	    helper.setTo(address);
	    helper.setSubject("Recept");
	    helper.setText(content, true);
	    
		javaMailSender.send(message);

		System.out.println("Email poslat!");
	}
	
	public void sendEmailToSupplier(String email, String name, String text, long offerId) throws MessagingException {
		String content = "Poštovani/Poštovana " + name + ",<br>"
	            + "Vaša ponuda je " + text + "<br>"
	            + "Broj ponude: " + offerId + "<br>"
	            + "Radujemo se budućem poslovanju,<br>"
	            + "ISA, TIM 28.";
		
		
		MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    helper.setFrom(env.getProperty("spring.mail.username"));
	    helper.setTo(email);
	    helper.setSubject("Odgovor na ponudu za narudžbinu");
	    helper.setText(content, true);
	    
		javaMailSender.send(message);
	}

}
