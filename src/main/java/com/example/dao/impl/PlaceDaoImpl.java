package com.example.dao.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.dao.PlaceDao;
import com.example.model.Place;
import com.example.model.Request;


@Repository
public class PlaceDaoImpl implements PlaceDao{
	
	final private String API_KEY = "";
	
	@Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public List<Place> getPlacesFromAPI(double longitude, double latitude, long radius) {
		
		String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+latitude+","+longitude+"&radius="+radius+"&key="+API_KEY;
		
		System.out.println(url);
		
		String tam = "http://localhost/test/tam.txt";
		String az = "http://localhost/test/az.txt";
		String fazla = "http://localhost/test/fazla.txt";

		List<Place> placeList = new ArrayList<Place>();
		
		URL obj;
		try {
			obj = new URL(url);
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();

			// optional default is GET
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		
			
			JSONObject resp = new JSONObject(response.toString());
			
			JSONArray results = resp.getJSONArray("results");
			
			for(int i=0;i<results.length();i++) {
				
				
				
				JSONObject jsonobject = results.getJSONObject(i);
				
				String name = jsonobject.getString("name");
				
				JSONObject geometry = jsonobject.getJSONObject("geometry");
				
				JSONObject location = geometry.getJSONObject("location");
				
				double lat = location.getDouble("lat");
				
				double lng = location.getDouble("lng");
				
				
				placeList.add(new Place(name,lng,lat));
				
				
			}
			
		} catch (MalformedURLException e) {
			return null;
		} catch (ProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		
		
		return placeList;
	}

	@Override
	public List<Place> getPlacesFromDatabase(long requestid) {
		
		String sql = "SELECT places.id,places.name, places.longitude, places.latitude FROM relation INNER JOIN places ON relation.placeid = places.id WHERE relation.requestid = ?";
		
		List<Place> places = jdbcTemplate.query(sql, new Object[] { requestid }, new PlaceRowMapper());
		
		return places;
	}

	@Override
	public String savePlace(Place place) {
		
		String sql = "INSERT INTO places (name,longitude,latitude) VALUES(?,?,?)";
		
		try {
			jdbcTemplate.update(sql, new Object[] { place.getName(),place.getLongitude(), place.getLatitude()});
	    }
	    catch (DataIntegrityViolationException e) {
	       return "exist";
	    }
			
		return "notexist";
	}

	@Override
	public String saveRequest(double longitude, double latitude, long radius) {
		
		String sql = "INSERT INTO requests (longitude,lat,rad) VALUES(?,?,?)";
		
		try {
			jdbcTemplate.update(sql, new Object[] { longitude,latitude,radius});
	    }
	    catch (DataIntegrityViolationException e) {
	       return "exist";
	    }
			
		return "notexist";
	}


	@Override
	public Long findRequest(double longitude, double latitude, long radius) {
		
		String sql = "SELECT * FROM requests WHERE longitude=? AND lat = ? AND rad = ?";
		
		Request request = jdbcTemplate.queryForObject(sql, new Object[] { longitude,latitude, radius }, new RequestRowMapper());
		
		return request.getId();
	}


	@Override
	public Long findPlace(Place place) {
		
		String sql = "SELECT * FROM places WHERE name=? AND longitude = ? AND latitude=?";
		
		Place pla = jdbcTemplate.queryForObject(sql, new Object[] { place.getName(),place.getLongitude(),place.getLatitude() }, new PlaceRowMapper());
		
		return pla.getId();
	}
	
	
	public class PlaceRowMapper implements RowMapper<Place> {
	    public Place mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Place place = new Place(rs.getLong("id"),rs.getString("name"),rs.getDouble("longitude"),rs.getDouble("latitude"));
	        return place;
	    }
	}
	
	public class RequestRowMapper implements RowMapper<Request> {
	    public Request mapRow(ResultSet rs, int rowNum) throws SQLException {
	    	Request request = new Request(rs.getLong("id"),rs.getDouble("longitude"),rs.getDouble("lat"),rs.getLong("rad"));
	        return request;
	    }
	}

	@Override
	public boolean saveRelation(long requestid, long placeid) {
		
		String sql = "INSERT INTO relation (requestid,placeid) VALUES(?,?)";
		
		int rows = jdbcTemplate.update(sql, requestid,placeid);
		
		if(rows>0)
			return true;
		else
			return false;

	}

	@Override
	public boolean deleteRequest(long requestid) {
		
		String deleteRequest = "DELETE FROM requests WHERE id = ?";
		
		
		try {
			
			jdbcTemplate.update(deleteRequest, requestid);
			
		
			return true;
		
		}catch (DataIntegrityViolationException  e) {
			return false;
		}
	}

}
