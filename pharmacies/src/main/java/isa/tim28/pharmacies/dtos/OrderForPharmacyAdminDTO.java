package isa.tim28.pharmacies.dtos;

import java.time.LocalDateTime;
import java.util.Set;

public class OrderForPharmacyAdminDTO {

	private long id;
	private Set<OrderedMedicineDTO> medicines;
	private LocalDateTime deadline;
	private boolean editable;
	private String state;
	private boolean canChooseWinner;
	private Set<OfferDTO> allOffers;
	private OfferDTO winningOffer;
	
	public OrderForPharmacyAdminDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public OrderForPharmacyAdminDTO(long id, Set<OrderedMedicineDTO> medicines, LocalDateTime deadline,
			boolean editable, String state, boolean canChooseWinner, Set<OfferDTO> allOffers, OfferDTO winningOffer) {
		super();
		this.id = id;
		this.medicines = medicines;
		this.deadline = deadline;
		this.editable = editable;
		this.state = state;
		this.canChooseWinner = canChooseWinner;
		this.allOffers = allOffers;
		this.winningOffer = winningOffer;
	}



	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Set<OrderedMedicineDTO> getMedicines() {
		return medicines;
	}
	public void setMedicines(Set<OrderedMedicineDTO> medicines) {
		this.medicines = medicines;
	}
	public LocalDateTime getDeadline() {
		return deadline;
	}
	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public boolean isCanChooseWinner() {
		return canChooseWinner;
	}

	public void setCanChooseWinner(boolean canChooseWinner) {
		this.canChooseWinner = canChooseWinner;
	}

	public Set<OfferDTO> getAllOffers() {
		return allOffers;
	}

	public void setAllOffers(Set<OfferDTO> allOffers) {
		this.allOffers = allOffers;
	}

	public OfferDTO getWinningOffer() {
		return winningOffer;
	}

	public void setWinningOffer(OfferDTO winningOffer) {
		this.winningOffer = winningOffer;
	}
}
