package com.synergisticit.service;

import com.synergisticit.domain.Amenities;

public interface AmenitiesService {
	public Amenities saveAmenities(Amenities amenities);
	public Amenities getAmenitiesByName (String amenitiesName);
}
