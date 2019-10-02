package com.example.service;

import java.util.List;

import com.example.model.Place;

public interface PlaceService {

	
	public List<Place> getPlacesFromAPI(double longitude, double latitude, long radius);
	public List<Place> getPlacesFromDatabase(long requestid);
	public String savePlace(Place place);
	public String saveRequest(double longitude, double latitude, long radius);
	public Long findRequest(double longitude, double latitude, long radius);
	public Long findPlace(Place place);
	public List<Place> getPlaces(double longitude, double latitude, long radius);
	public boolean saveRelation(long requestid,long placeid);
	public boolean deleteRequest(long requestid);


}
