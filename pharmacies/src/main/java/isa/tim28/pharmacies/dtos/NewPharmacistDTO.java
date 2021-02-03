package isa.tim28.pharmacies.dtos;

import java.time.LocalDateTime;

public class NewPharmacistDTO {
	
	private String name;
	private String surname;
	private String email;
	private String password;
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private boolean sunday;
	private LocalDateTime mondayStart;
	private LocalDateTime mondayEnd;
	private LocalDateTime tuesdayStart;
	private LocalDateTime tuesdayEnd;
	private LocalDateTime wednesdayStart;
	private LocalDateTime wednesdayEnd;
	private LocalDateTime thursdayStart;
	private LocalDateTime thursdayEnd;
	private LocalDateTime fridayStart;
	private LocalDateTime fridayEnd;
	private LocalDateTime saturdayStart;
	private LocalDateTime saturdayEnd;
	private LocalDateTime sundayStart;
	private LocalDateTime sundayEnd;
	
	public NewPharmacistDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public NewPharmacistDTO(String name, String surname, String email, String password, boolean monday, boolean tuesday,
			boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday,
			LocalDateTime mondayStart, LocalDateTime mondayEnd, LocalDateTime tuesdayStart, LocalDateTime tuesdayEnd,
			LocalDateTime wednesdayStart, LocalDateTime wednesdayEnd, LocalDateTime thursdayStart,
			LocalDateTime thursdayEnd, LocalDateTime fridayStart, LocalDateTime fridayEnd, LocalDateTime saturdayStart,
			LocalDateTime saturdayEnd, LocalDateTime sundayStart, LocalDateTime sundayEnd) {
		super();
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.monday = monday;
		this.tuesday = tuesday;
		this.wednesday = wednesday;
		this.thursday = thursday;
		this.friday = friday;
		this.saturday = saturday;
		this.sunday = sunday;
		this.mondayStart = mondayStart;
		this.mondayEnd = mondayEnd;
		this.tuesdayStart = tuesdayStart;
		this.tuesdayEnd = tuesdayEnd;
		this.wednesdayStart = wednesdayStart;
		this.wednesdayEnd = wednesdayEnd;
		this.thursdayStart = thursdayStart;
		this.thursdayEnd = thursdayEnd;
		this.fridayStart = fridayStart;
		this.fridayEnd = fridayEnd;
		this.saturdayStart = saturdayStart;
		this.saturdayEnd = saturdayEnd;
		this.sundayStart = sundayStart;
		this.sundayEnd = sundayEnd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isMonday() {
		return monday;
	}

	public void setMonday(boolean monday) {
		this.monday = monday;
	}

	public boolean isTuesday() {
		return tuesday;
	}

	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}

	public boolean isWednesday() {
		return wednesday;
	}

	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}

	public boolean isThursday() {
		return thursday;
	}

	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}

	public boolean isFriday() {
		return friday;
	}

	public void setFriday(boolean friday) {
		this.friday = friday;
	}

	public boolean isSaturday() {
		return saturday;
	}

	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}

	public boolean isSunday() {
		return sunday;
	}

	public void setSunday(boolean sunday) {
		this.sunday = sunday;
	}

	public LocalDateTime getMondayStart() {
		return mondayStart;
	}

	public void setMondayStart(LocalDateTime mondayStart) {
		this.mondayStart = mondayStart;
	}

	public LocalDateTime getMondayEnd() {
		return mondayEnd;
	}

	public void setMondayEnd(LocalDateTime mondayEnd) {
		this.mondayEnd = mondayEnd;
	}

	public LocalDateTime getTuesdayStart() {
		return tuesdayStart;
	}

	public void setTuesdayStart(LocalDateTime tuesdayStart) {
		this.tuesdayStart = tuesdayStart;
	}

	public LocalDateTime getTuesdayEnd() {
		return tuesdayEnd;
	}

	public void setTuesdayEnd(LocalDateTime tuesdayEnd) {
		this.tuesdayEnd = tuesdayEnd;
	}

	public LocalDateTime getWednesdayStart() {
		return wednesdayStart;
	}

	public void setWednesdayStart(LocalDateTime wednesdayStart) {
		this.wednesdayStart = wednesdayStart;
	}

	public LocalDateTime getWednesdayEnd() {
		return wednesdayEnd;
	}

	public void setWednesdayEnd(LocalDateTime wednesdayEnd) {
		this.wednesdayEnd = wednesdayEnd;
	}

	public LocalDateTime getThursdayStart() {
		return thursdayStart;
	}

	public void setThursdayStart(LocalDateTime thursdayStart) {
		this.thursdayStart = thursdayStart;
	}

	public LocalDateTime getThursdayEnd() {
		return thursdayEnd;
	}

	public void setThursdayEnd(LocalDateTime thursdayEnd) {
		this.thursdayEnd = thursdayEnd;
	}

	public LocalDateTime getFridayStart() {
		return fridayStart;
	}

	public void setFridayStart(LocalDateTime fridayStart) {
		this.fridayStart = fridayStart;
	}

	public LocalDateTime getFridayEnd() {
		return fridayEnd;
	}

	public void setFridayEnd(LocalDateTime fridayEnd) {
		this.fridayEnd = fridayEnd;
	}

	public LocalDateTime getSaturdayStart() {
		return saturdayStart;
	}

	public void setSaturdayStart(LocalDateTime saturdayStart) {
		this.saturdayStart = saturdayStart;
	}

	public LocalDateTime getSaturdayEnd() {
		return saturdayEnd;
	}

	public void setSaturdayEnd(LocalDateTime saturdayEnd) {
		this.saturdayEnd = saturdayEnd;
	}

	public LocalDateTime getSundayStart() {
		return sundayStart;
	}

	public void setSundayStart(LocalDateTime sundayStart) {
		this.sundayStart = sundayStart;
	}

	public LocalDateTime getSundayEnd() {
		return sundayEnd;
	}

	public void setSundayEnd(LocalDateTime sundayEnd) {
		this.sundayEnd = sundayEnd;
	}

}
