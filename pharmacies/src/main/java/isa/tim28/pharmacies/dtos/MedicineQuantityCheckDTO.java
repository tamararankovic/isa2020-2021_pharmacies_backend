package isa.tim28.pharmacies.dtos;

public class MedicineQuantityCheckDTO {
	
	private boolean available;

	public MedicineQuantityCheckDTO() {
		super();
	}

	public MedicineQuantityCheckDTO(boolean available) {
		super();
		this.available = available;
	}
	
	public MedicineQuantityCheckDTO(long medicineQuantity) {
		super();
		if(medicineQuantity > 0) this.available = true;
		else this.available = false;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

}
