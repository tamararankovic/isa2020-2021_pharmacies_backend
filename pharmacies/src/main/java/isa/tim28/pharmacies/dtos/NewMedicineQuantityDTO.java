package isa.tim28.pharmacies.dtos;

public class NewMedicineQuantityDTO {

	private long medicineId;
	private long orderedQuantity;
	
	public NewMedicineQuantityDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NewMedicineQuantityDTO(long medicineId, long orderedQuantity) {
		super();
		this.medicineId = medicineId;
		this.orderedQuantity = orderedQuantity;
	}
	
	public long getMedicineId() {
		return medicineId;
	}
	public void setMedicineId(long medicineId) {
		this.medicineId = medicineId;
	}
	public long getOrderedQuantity() {
		return orderedQuantity;
	}
	public void setOrderedQuantity(long orderedQuantity) {
		this.orderedQuantity = orderedQuantity;
	}
}
