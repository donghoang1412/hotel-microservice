package com.synergisticit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Amenities;
import com.synergisticit.respository.AmenitiesRepository;

@Service
public class AmenitiesServiceImpl implements AmenitiesService{

	@Autowired
	AmenitiesRepository amenitiesRepository;
	

	@Override
	public Amenities saveAmenities(Amenities amenities) {
		return amenitiesRepository.save(amenities);
	}
	

	@Override
	public Amenities getAmenitiesByName(String amenitiesName) {
		// TODO Auto-generated method stub
		return amenitiesRepository.findByName(amenitiesName);
	}
	
}
