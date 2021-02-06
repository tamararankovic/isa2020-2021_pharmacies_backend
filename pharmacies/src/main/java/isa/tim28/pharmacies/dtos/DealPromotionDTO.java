package isa.tim28.pharmacies.dtos;

import java.time.LocalDate;

import isa.tim28.pharmacies.model.BenefitType;

public class DealPromotionDTO {

	private String text;
	private BenefitType type;
	private LocalDate startDateTime;
	private LocalDate endDateTime;

	public DealPromotionDTO() {
		super();
	}
	
	public DealPromotionDTO(String text, BenefitType type, LocalDate startDateTime, LocalDate endDateTime) {
		super();
		this.text = text;
		this.type = type;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
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

	public LocalDate getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDate startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDate getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDate endDateTime) {
		this.endDateTime = endDateTime;
	}
}
