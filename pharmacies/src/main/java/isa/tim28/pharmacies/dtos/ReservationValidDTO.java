package isa.tim28.pharmacies.dtos;

import java.time.LocalDateTime;

public class ReservationValidDTO {
	
	private boolean valid;

	public ReservationValidDTO() {
		super();
	}

	public ReservationValidDTO(boolean valid) {
		super();
		this.valid = valid;
	}
	
	/*konstruktor gde proveravamo da li ima manje od 24h od kraja rezervacije*/
	public ReservationValidDTO(LocalDateTime dateTime) {
		super();
		if(LocalDateTime.now().isBefore(dateTime.minusDays(1))) this.valid = true;
		else this.valid = false;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}
