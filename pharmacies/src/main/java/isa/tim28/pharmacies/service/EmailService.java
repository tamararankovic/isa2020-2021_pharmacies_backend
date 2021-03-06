package isa.tim28.pharmacies.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
	public void sendResponseOnComplaint(String patientFullName, String answer, String address, String complainedOn) throws MessagingException {
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
		System.out.println("Slanje emaila...");
		
		String content = "Pozdrav " + patientFullName + ",<br>"
	            + "Odgovor na vasu zalbu o " + complainedOn + " je sledeci:<br>"
	            + "<h3>" + answer + "</h3>"
	            + "Hvala Vam,<br>"
	            + "ISA, TIM 28.";
		
		MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    helper.setFrom(env.getProperty("spring.mail.username"));
	    helper.setTo(address);
	    helper.setSubject("Odgovor na zalbu");
	    helper.setText(content, true);
	    
		javaMailSender.send(message);

		System.out.println("Email poslat!");	
	}
	
	@Async
	public void sendERecepyConfirmation(String patientFullName, String medicineCodes, String address) throws MessagingException {
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: " + Thread.currentThread().getId());
		System.out.println("Slanje emaila...");
		
		String content = "Pozdrav " + patientFullName + ",<br>"
	            + "Izdati su Vam sledeci lekovi putem eRecepta:<br>"
	            + "<h3>" + medicineCodes + "</h3>"
	            + "Hvala Vam,<br>"
	            + "ISA, TIM 28.";
		
		MimeMessage message = javaMailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message);
	    helper.setFrom(env.getProperty("spring.mail.username"));
	    helper.setTo(address);
	    helper.setSubject("eRecept");
	    helper.setText(content, true);
	    
		javaMailSender.send(message);

		System.out.println("Email poslat!");	
		
	}
	
	@Async
	public void sendMailAsync(User user, String siteURL) throws MessagingException {
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: "
				+ Thread.currentThread().getId());
		System.out.println("Slanje emaila...");

		String content = "Pozdrav " + user.getName() + ",<br>"
				+ "Molimo Vas da posetite link ispod kako biste potvrdili Vasu registraciju:<br>"
				+ "<h3><a href=\"[[URL]]\" target=\"_self\">POTVRDI</a></h3>" + "Hvala Vam,<br>" + "ISA, TIM 28.";
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
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: "
				+ Thread.currentThread().getId());
		System.out.println("Slanje emaila...");
		String content = "Pozdrav " + name + ",<br>" + "Prepisani su Vam lekovi:<br>" + "<h3>" + medicineCodes + "</h3>"
				+ "Hvala Vam,<br>" + "ISA, TIM 28.";

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
	public void sendReservationReceivedEmailAsync(String name, String address, long reservationId)
			throws MessagingException {
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: "
				+ Thread.currentThread().getId());
		System.out.println("Slanje emaila...");
		String content = "Pozdrav " + name + ",<br>" + "Rezervisani lek je preuzet." + "<br>" + "Broj rezervacije: "
				+ reservationId + "<br>" + "Hvala Vam,<br>" + "ISA, TIM 28.";

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(env.getProperty("spring.mail.username"));
		helper.setTo(address);
		helper.setSubject("Preuzeta rezervacija");
		helper.setText(content, true);

		javaMailSender.send(message);

		System.out.println("Email poslat!");
	}

	@Async
	public void sendReservationMadeEmailAsync(String name, String address, String medicineName, long reservationId)
			throws MessagingException {
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: "
				+ Thread.currentThread().getId());
		System.out.println("Slanje emaila...");
		String content = "Pozdrav " + name + ",<br>" + "Uspešno ste rezervisali " + medicineName + "." + "<br>"
				+ "Broj rezervacije: " + reservationId + "<br>" + "Hvala Vam,<br>" + "ISA, TIM 28.";

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(env.getProperty("spring.mail.username"));
		helper.setTo(address);
		helper.setSubject("Uspešna rezervacija");
		helper.setText(content, true);

		javaMailSender.send(message);

		System.out.println("Email poslat!");
	}

	@Async
	public void sendCounselingScheduled(String name, String address, String pharmacist, String date)
			throws MessagingException {
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: "
				+ Thread.currentThread().getId());
		System.out.println("Slanje emaila...");
		String content = "Pozdrav " + name + ",<br>" + "Uspešno ste zakazali savetovanje kod farmaceuta: " + pharmacist + "." + "<br>"+
				"Savetovanje će se održati " + date + "<br>"
				+"Hvala Vam,<br>" + "ISA, TIM 28.";

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(env.getProperty("spring.mail.username"));
		helper.setTo(address);
		helper.setSubject("Uspešno zakazano savetovanje");
		helper.setText(content, true);

		javaMailSender.send(message);

		System.out.println("Email poslat!");
	}
	
	@Async
	public void sendAppointmentScheduled(String name, String address, String dermatologist, String date)
			throws MessagingException {
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: "
				+ Thread.currentThread().getId());
		System.out.println("Slanje emaila...");
		String content = "Pozdrav " + name + ",<br>" + "Uspešno ste zakazali pregled kod dermatologa: " + dermatologist + "." + "<br>"+
				"Savetovanje će se održati " + date + "<br>"
				+"Hvala Vam,<br>" + "ISA, TIM 28.";

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(env.getProperty("spring.mail.username"));
		helper.setTo(address);
		helper.setSubject("Uspešno zakazan pregled");
		helper.setText(content, true);

		javaMailSender.send(message);

		System.out.println("Email poslat!");
	}

	public void sendEmailToSupplier(String email, String name, String text, long offerId) throws MessagingException {
		String content = "Poštovani/Poštovana " + name + ",<br>" + "Vaša ponuda je " + text + "<br>" + "Broj ponude: "
				+ offerId + "<br>" + "Radujemo se budućem poslovanju,<br>" + "ISA, TIM 28.";

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(env.getProperty("spring.mail.username"));

		helper.setTo(email);
		helper.setSubject("Odgovor na ponudu za narudžbinu");
		helper.setText(content, true);

		javaMailSender.send(message);
	}

	@Async
	public void sendAppointmnetConfirmationAsync(String name, String address, LocalDateTime startDateTime)
			throws MessagingException {
		System.out.println("Async metoda se izvrsava u drugom Threadu u odnosu na prihvaceni zahtev. Thread id: "
				+ Thread.currentThread().getId());
		System.out.println("Slanje emaila za zakazivanje...");
		String content = "Pozdrav " + name + ",<br>" + "Novi pregled je zakazan." + "<br>" + "Vreme pregleda: "
				+ startDateTime.format(DateTimeFormatter.ofPattern("HH:mm, dd.MM.yyyy.")) + "<br>" + "Hvala Vam,<br>"
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
	public void sendEmailDealPromotion(String email, String pharmacyName, String type, String text,
			LocalDate startDateTIme, LocalDate endDateTime) throws MessagingException {
		String content = text + "<br>" + "Datum početka ponude: "
				+ startDateTIme.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")) + "<br>" + "Datum isteka ponude: "
				+ endDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(env.getProperty("spring.mail.username"));

		helper.setTo(email);
		helper.setSubject(type + " iz apoteke " + pharmacyName);
		helper.setText(content, true);

		javaMailSender.send(message);
	}

	@Async
	public void notifyEmployeeLeaveRequestIsDeclined(String email, String fullName, String type, LocalDate startDate,
			LocalDate endDate, String reasonDeclined) throws MessagingException {
		String content = "Poštovani/Poštovana " + fullName + ",<br>" + "Vaš zahtev za " + type + " od "
				+ startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")) + " do "
				+ endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")) + " je odbijen." + "<br>"
				+ "Razlog zbog kog je zahtev obijen: " + reasonDeclined;

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(env.getProperty("spring.mail.username"));

		helper.setTo(email);
		helper.setSubject("Odgovor na zahtev za " + type);
		helper.setText(content, true);

		javaMailSender.send(message);
	}

	@Async
	public void notifyEmployeeLeaveRequestIsAccepted(String email, String fullName, String type, LocalDate startDate,
			LocalDate endDate) throws MessagingException {
		String content = "Poštovani/Poštovana " + fullName + ",<br>" + "Vaš zahtev za " + type + " od "
				+ startDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")) + " do "
				+ endDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")) + " je prihvaćen.";

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(env.getProperty("spring.mail.username"));

		helper.setTo(email);
		helper.setSubject("Odgovor na zahtev za " + type);
		helper.setText(content, true);

		javaMailSender.send(message);
	}
}
