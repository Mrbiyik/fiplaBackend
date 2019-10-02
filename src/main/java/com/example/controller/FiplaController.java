package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Place;
import com.example.service.PlaceService;


@ComponentScan("com.example.config")
@RestController
public class FiplaController {
	
	@Autowired
	PlaceService service;
	
	@CrossOrigin
    @RequestMapping("/getPlaces")
    public List<Place> getPlaces(@RequestParam("long") double longitude, @RequestParam("lat") double latitude, @RequestParam("rad") long radius){
		
		return service.getPlaces(longitude, latitude, radius);
    }

}
