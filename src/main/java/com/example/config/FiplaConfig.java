package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.dao.PlaceDao;
import com.example.dao.impl.PlaceDaoImpl;
import com.example.service.PlaceService;
import com.example.service.impl.PlaceServiceImpl;


@Configuration
public class FiplaConfig {

	
	@Bean
	public PlaceService getPlaceService() {
		
		return new PlaceServiceImpl();
		
	}
	@Bean
	public PlaceDao convertUserDao() {
		return new PlaceDaoImpl();
	}
	
}
