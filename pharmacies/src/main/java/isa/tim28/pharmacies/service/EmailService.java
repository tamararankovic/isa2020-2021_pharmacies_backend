package isa.tim28.pharmacies.service;

import java.util.List;

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
	public void sendMedicineEmailAsync(String name, String address, List<String> medicineCodes) throws MessagingException {
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
		System.out.println("Slanje emaila...");
		String content = "";
		if(medicineCodes.size() > 1) {
			content = "Pozdrav " + name + ",<br>"
		            + "Primili ste kodove za preuzimanje lekova:<br>"
		            + "<h3>" + medicineCodes + "</h3>"
		            + "Hvala Vam,<br>"
		            + "ISA, TIM 28.";
			
		}
		else content = "Pozdrav " + name + ",<br>"
	            + "Primili ste kod za preuzimanje leka:<br>"
	            + "<h3>" + medicineCodes + "</h3>"
	            + "Hvala Vam,<br>"
	            + "ISA, TIM 28.";
		
		
		MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    helper.setFrom(env.getProperty("spring.mail.username"));
	    helper.setTo(address);
	    helper.setSubject("Recept za preuzimanje leka");
	    helper.setText(content, true);
	    
		javaMailSender.send(message);

		System.out.println("Email poslat!");
		
	}

}
