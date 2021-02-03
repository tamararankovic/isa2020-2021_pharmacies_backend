package isa.tim28.pharmacies.dtos;

public class IsAllergicDTO {
	
	boolean allergic;

	public IsAllergicDTO() {
		super();
	}

	public IsAllergicDTO(boolean allergic) {
		super();
		this.allergic = allergic;
	}

	public boolean isAllergic() {
		return allergic;
	}

	public void setAllergic(boolean allergic) {
		this.allergic = allergic;
	}

}
