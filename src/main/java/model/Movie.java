package model;

public class Movie {
	private int id;
	private String title; // text
	private Company company;
	private String duration; // range
	private String color; // color
	private String file; // file
	
	public Movie() {
		this(0);
	}
	
	public Movie(int id) {
		this.id = id;
		setTitle("");
		setDuration("");
		setColor("");
		setFile("");
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
}

