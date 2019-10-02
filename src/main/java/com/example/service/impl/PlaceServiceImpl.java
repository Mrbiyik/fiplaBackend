package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.PlaceDao;
import com.example.model.Place;
import com.example.service.PlaceService;

@Service
public class PlaceServiceImpl implements PlaceService{
	
	@Autowired
	PlaceDao placeDao;
	
	
	@Override
	public List<Place> getPlaces(double longitude, double latitude, long radius) {

		// ANA MANTIK!
		String isRequestExist = saveRequest(longitude, latitude, radius);
		
		if(isRequestExist.equals("exist")) {
			
			System.out.println("SAME REQUEST!....");
			
			long requestid = findRequest(longitude, latitude, radius);
			System.out.println("Request founded...");
			
			List<Place> places = getPlacesFromDatabase(requestid);
			System.out.println("Places got from Database...");
			return places;
			
			
		}
		else if(isRequestExist.equals("notexist")) {
			
			System.out.println("DIFFERENT REQUEST!....");
			
			List<Place> places = getPlacesFromAPI(longitude, latitude, radius);
			
			System.out.println("Places got from API...");
			
			long requestid = findRequest(longitude, latitude, radius);
			
			System.out.println("Request founded...");
			
			System.out.println(places.size());
			
			if(places.size() != 0) 
			{
				
				for(int i=0; i<places.size();i++) 
				{
					
					String isPlaceExist = savePlace(places.get(i));
					
					System.out.println("Place was saved...");
					
					long placeid = findPlace(places.get(i));
					
					System.out.println("Place was found...");
					
					if(isPlaceExist.equals("exist")) 
					{
						
						saveRelation(requestid, placeid);
						System.out.println("Relation was saved...");
						
					}
					else if(isPlaceExist.equals("notexist")) 
					{
						
						saveRelation(requestid, placeid);
						System.out.println("Relation was saved...");
						
					}
					
				  }
			}else {
				
				placeDao.deleteRequest(requestid);
				places.add(new Place("API Problem!", 0.0, 0.0));
				
			}
				
			return places;	
				
		}
		return null;
		
	}
	

	@Override
	public List<Place> getPlacesFromAPI(double longitude, double latitude, long radius) {
		return placeDao.getPlacesFromAPI(longitude, latitude, radius);
	}


	@Override
	public String savePlace(Place place) {
		// TODO Auto-generated method stub
		return placeDao.savePlace(place);
	}

	@Override
	public String saveRequest(double longitude, double latitude, long radius) {
		// TODO Auto-generated method stub
		return placeDao.saveRequest(longitude, latitude, radius);
	}


	@Override
	public List<Place> getPlacesFromDatabase(long requestid) {
		// TODO Auto-generated method stub
		return placeDao.getPlacesFromDatabase(requestid);
	}


	@Override
	public Long findRequest(double longitude, double latitude, long radius) {
		// TODO Auto-generated method stub
		return placeDao.findRequest(longitude, latitude, radius);
	}


	@Override
	public Long findPlace(Place place) {
		// TODO Auto-generated method stub
		return placeDao.findPlace(place);
	}


	@Override
	public boolean saveRelation(long requestid, long placeid) {
		// TODO Auto-generated method stub
		return placeDao.saveRelation(requestid, placeid);
	}


	@Override
	public boolean deleteRequest(long requestid) {
		// TODO Auto-generated method stub
		return placeDao.deleteRequest(requestid);
	}



}
