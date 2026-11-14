package model;

import java.util.Date;

public class Ticket {
	private int id;
	private Date start; // select do tipo data
	private Date startTime; // select do tipo horario
	private double price; //  select do tipo number
	private Movie movie;
	private boolean halfPriceTicket;	// checkbox
	
	public Ticket() {
		this(0);
	}
	
	public Ticket(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	public boolean isHalfPriceTicket() {
		return halfPriceTicket;
	}
	public void setHalfPriceTicket(boolean halfPriceTicket) {
		this.halfPriceTicket = halfPriceTicket;
	}
}
