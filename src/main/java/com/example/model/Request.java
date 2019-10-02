package com.example.model;

public class Request {
	
	private Long id;
	private double longitude;
	private double latitude;
	private Long radius;
	public Request(Long id, double longitude, double latitude, Long radius) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.radius = radius;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public Long getRadius() {
		return radius;
	}
	public void setRadius(Long radius) {
		this.radius = radius;
	}
	
	

}
