package com.synergisticit.restclient;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.synergisticit.models.Booking;

@Component
public class RestClient {

	private static final String BookingMicroserviceUrl = "http://localhost:8181/";
	
	public List<Object> getBookings(int hotelId) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType
				.APPLICATION_JSON);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(BookingMicroserviceUrl + "/getBookingsHotel?hotelId="+hotelId,
				Object[].class);
		
		Object[] obj = responseEntity.getBody();
		
		return Arrays.asList(obj);
	}
	
}