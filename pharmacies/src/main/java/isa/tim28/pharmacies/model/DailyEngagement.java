package isa.tim28.pharmacies.model;

import java.time.DayOfWeek;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class DailyEngagement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "dayOfWeek", nullable = false)
	private DayOfWeek dayOfWeek;
	
	@Column(name = "startTime", nullable = false)
	private LocalTime startTime;
	
	@Column(name = "endTime", nullable = false)
	private LocalTime endTime;

	public DailyEngagement() {
		super();
	}
	
	public DailyEngagement(long id, DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
		super();
		this.id = id;
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public DailyEngagement(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime) {
		super();
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}

	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	
	public boolean isOverlappingWith(LocalTime startTime, LocalTime endTime) {
		return (startTime.isAfter(this.startTime) && startTime.isBefore(this.endTime))
		|| (endTime.isAfter(this.startTime) && endTime.isBefore(this.endTime));
	}
}
