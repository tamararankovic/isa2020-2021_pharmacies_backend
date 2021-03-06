package isa.tim28.pharmacies.dtos;

public class MedicineForPharmacyAdminDTO {

	private long id;
	private String code;
	private String name;
	private String type;
	private String manufacturer;
	private boolean pharmacyOffers;
	
	public MedicineForPharmacyAdminDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MedicineForPharmacyAdminDTO(long id, String code, String name, String type, String manufacturer, boolean pharmacyOffers) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.type = type;
		this.manufacturer = manufacturer;
		this.pharmacyOffers = pharmacyOffers;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public boolean isPharmacyOffers() {
		return pharmacyOffers;
	}

	public void setPharmacyOffers(boolean pharmacyOffers) {
		this.pharmacyOffers = pharmacyOffers;
	}
}
