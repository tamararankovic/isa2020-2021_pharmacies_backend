package isa.tim28.pharmacies.dtos;

public class OrderedMedicineForSupplierDTO {
	MedicineForSupplierDTO medicine;
	int quantity;
	
	
	public OrderedMedicineForSupplierDTO() {
		super();
	}

	public OrderedMedicineForSupplierDTO(MedicineForSupplierDTO medicine, int quantity) {
		super();
		this.medicine = medicine;
		this.quantity = quantity;
	}

	public MedicineForSupplierDTO getMedicine() {
		return medicine;
	}

	public void setMedicine(MedicineForSupplierDTO medicine) {
		this.medicine = medicine;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
