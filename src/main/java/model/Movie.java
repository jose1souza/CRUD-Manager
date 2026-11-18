package model;

public class Movie {
	private int id;
	private String title; // text
	private Company company; // select (relacionamento FK)
	private int duration; // range (int em minutos)
	private String color; // color (#hexadecimal)
	private String file; // file (nome/caminho como String)
	
	public Movie() {
		this(0);
	}
	
	public Movie(int id) {
		this.id = id;
		setTitle("");
		setDuration(120);
		setColor("#ff0000");
		setFile("file.mp4");
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
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
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
