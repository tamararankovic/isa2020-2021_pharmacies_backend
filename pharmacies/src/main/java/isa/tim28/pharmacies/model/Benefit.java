package isa.tim28.pharmacies.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Benefit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "text", nullable = false)
	private String text;
	
	@Column(name = "type", nullable = false)
	private BenefitType type;
	
	@Column(name = "startDateTime", nullable = false)
	private LocalDateTime startDateTime;
	
	@Column(name = "endDateTime", nullable = false)
	private LocalDateTime endDateTime;

	public Benefit() {
		super();
	}
	
	public Benefit(long id, String text, BenefitType type, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		super();
		this.id = id;
		this.text = text;
		this.type = type;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public BenefitType getType() {
		return type;
	}

	public void setType(BenefitType type) {
		this.type = type;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}
}
