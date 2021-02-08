package isa.tim28.pharmacies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class PharmaciesApplication {
//test
	public static void main(String[] args) {
		SpringApplication.run(PharmaciesApplication.class, args);
	}

}
