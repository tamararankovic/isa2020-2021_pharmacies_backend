package isa.tim28.pharmacies.dtos;

public class LoyaltyDTO {
	private int pointsAfterAppointment;
	private int pointsAfterAdvising;
	private int pointsForRegular;
	private double discountForRegular;
	private int pointsForSilver;
	private double discountForSilver;
	private int pointsForGold;
	private double discountForGold;
	
	public LoyaltyDTO() {
		super();
	}

	public LoyaltyDTO(int pointsAfterAppointment, int pointsAfterAdvising, int pointsForRegular,
			double discountForRegular, int pointsForSilver, double discountForSilver, int pointsForGold,
			double discountForGold) {
		super();
		this.pointsAfterAppointment = pointsAfterAppointment;
		this.pointsAfterAdvising = pointsAfterAdvising;
		this.pointsForRegular = pointsForRegular;
		this.discountForRegular = discountForRegular;
		this.pointsForSilver = pointsForSilver;
		this.discountForSilver = discountForSilver;
		this.pointsForGold = pointsForGold;
		this.discountForGold = discountForGold;
	}

	public int getPointsAfterAppointment() {
		return pointsAfterAppointment;
	}

	public void setPointsAfterAppointment(int pointsAfterAppointment) {
		this.pointsAfterAppointment = pointsAfterAppointment;
	}

	public int getPointsAfterAdvising() {
		return pointsAfterAdvising;
	}

	public void setPointsAfterAdvising(int pointsAfterAdvising) {
		this.pointsAfterAdvising = pointsAfterAdvising;
	}

	public int getPointsForRegular() {
		return pointsForRegular;
	}

	public void setPointsForRegular(int pointsForRegular) {
		this.pointsForRegular = pointsForRegular;
	}

	public double getDiscountForRegular() {
		return discountForRegular;
	}

	public void setDiscountForRegular(double discountForRegular) {
		this.discountForRegular = discountForRegular;
	}

	public int getPointsForSilver() {
		return pointsForSilver;
	}

	public void setPointsForSilver(int pointsForSilver) {
		this.pointsForSilver = pointsForSilver;
	}

	public double getDiscountForSilver() {
		return discountForSilver;
	}

	public void setDiscountForSilver(double discountForSilver) {
		this.discountForSilver = discountForSilver;
	}

	public int getPointsForGold() {
		return pointsForGold;
	}

	public void setPointsForGold(int pointsForGold) {
		this.pointsForGold = pointsForGold;
	}

	public double getDiscountForGold() {
		return discountForGold;
	}

	public void setDiscountForGold(double discountForGold) {
		this.discountForGold = discountForGold;
	}
	
	

}
