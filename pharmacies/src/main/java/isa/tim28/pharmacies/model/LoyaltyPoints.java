package isa.tim28.pharmacies.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class LoyaltyPoints {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "pointsAfterAppointment", nullable = false)
	private int pointsAfterAppointment;
	
	@Column(name = "pointsAfterAdvising", nullable = false)
	private int pointsAfterAdvising;
	
	@Column(name = "pointsForRegular", nullable = false)
	private int pointsForRegular;
	
	@Column(name = "discountForRegular", nullable = false)
	private double discountForRegular;
	
	@Column(name = "pointsForSilver", nullable = false)
	private int pointsForSilver;
	
	@Column(name = "discountForSilver", nullable = false)
	private double discountForSilver;
	
	@Column(name = "pointsForGold", nullable = false)
	private int pointsForGold;
	
	@Column(name = "discountForGold", nullable = false)
	private double discountForGold;

	public LoyaltyPoints() {
		super();
	}

	public LoyaltyPoints( int pointsAfterAppointment, int pointsAfterAdvising, int pointsForRegular,
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


	
	public LoyaltyPoints(long id, int pointsAfterAppointment, int pointsAfterAdvising, int pointsForRegular,
			double discountForRegular, int pointsForSilver, double discountForSilver, int pointsForGold,
			double discountForGold) {
		super();
		this.id = id;
		this.pointsAfterAppointment = pointsAfterAppointment;
		this.pointsAfterAdvising = pointsAfterAdvising;
		this.pointsForRegular = pointsForRegular;
		this.discountForRegular = discountForRegular;
		this.pointsForSilver = pointsForSilver;
		this.discountForSilver = discountForSilver;
		this.pointsForGold = pointsForGold;
		this.discountForGold = discountForGold;
	}


	public double getDiscountForRegular() {
		return discountForRegular;
	}


	public void setDiscountForRegular(double discountForRegular) {
		this.discountForRegular = discountForRegular;
	}


	public double getDiscountForSilver() {
		return discountForSilver;
	}


	public void setDiscountForSilver(double discountForSilver) {
		this.discountForSilver = discountForSilver;
	}


	public double getDiscountForGold() {
		return discountForGold;
	}


	public void setDiscountForGold(double discountForGold) {
		this.discountForGold = discountForGold;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public int getPointsForSilver() {
		return pointsForSilver;
	}

	public void setPointsForSilver(int pointsForSilver) {
		this.pointsForSilver = pointsForSilver;
	}

	public int getPointsForGold() {
		return pointsForGold;
	}

	public void setPointsForGold(int pointsForGold) {
		this.pointsForGold = pointsForGold;
	}
	
	
}
