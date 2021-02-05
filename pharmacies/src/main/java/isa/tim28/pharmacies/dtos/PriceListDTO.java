package isa.tim28.pharmacies.dtos;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class PriceListDTO {

	private Set<ItemPriceDTO> medicinePrices = new HashSet<ItemPriceDTO>();
	private ItemPriceDTO pharmacistAppointmentPrice;
	private ItemPriceDTO dermatologistAppointmentPrice;
	private LocalDate startDate;
	
	public PriceListDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public PriceListDTO(Set<ItemPriceDTO> medicinePrices, ItemPriceDTO pharmacistAppointmentPrice,
			ItemPriceDTO dermatologistAppointmentPrice, LocalDate startDate) {
		super();
		this.medicinePrices = medicinePrices;
		this.pharmacistAppointmentPrice = pharmacistAppointmentPrice;
		this.dermatologistAppointmentPrice = dermatologistAppointmentPrice;
		this.startDate = startDate;
	}
	
	public Set<ItemPriceDTO> getMedicinePrices() {
		return medicinePrices;
	}
	public void setMedicinePrices(Set<ItemPriceDTO> medicinePrices) {
		this.medicinePrices = medicinePrices;
	}
	public ItemPriceDTO getPharmacistAppointmentPrice() {
		return pharmacistAppointmentPrice;
	}
	public void setPharmacistAppointmentPrice(ItemPriceDTO pharmacistAppointmentPrice) {
		this.pharmacistAppointmentPrice = pharmacistAppointmentPrice;
	}
	public ItemPriceDTO getDermatologistAppointmentPrice() {
		return dermatologistAppointmentPrice;
	}
	public void setDermatologistAppointmentPrice(ItemPriceDTO dermatologistAppointmentPrice) {
		this.dermatologistAppointmentPrice = dermatologistAppointmentPrice;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
}
