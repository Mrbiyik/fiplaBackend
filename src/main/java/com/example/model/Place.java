package com.example.model;

public class Place {
	
	private long id;
	private String name;
	private double longitude;
	private double latitude;
	public Place(long id, String name, double longitude, double latitude) {
		super();
		this.id = id;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public Place(String name, double lng, double lat) {
		this.name = name;
		this.longitude = lng;
		this.latitude = lat;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	
	


}
