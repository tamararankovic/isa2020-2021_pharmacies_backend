package isa.tim28.pharmacies.dtos;

public class OrderedMedicineDTO {

	MedicineForPharmacyAdminDTO medicine;
	int quantity;
	
	public OrderedMedicineDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderedMedicineDTO(MedicineForPharmacyAdminDTO medicine, int quantity) {
		super();
		this.medicine = medicine;
		this.quantity = quantity;
	}

	public MedicineForPharmacyAdminDTO getMedicine() {
		return medicine;
	}

	public void setMedicine(MedicineForPharmacyAdminDTO medicine) {
		this.medicine = medicine;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
