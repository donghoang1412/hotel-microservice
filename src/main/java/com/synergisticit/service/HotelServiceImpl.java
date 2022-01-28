package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Hotel;
import com.synergisticit.respository.HotelRespository;

@Service
public class HotelServiceImpl implements HotelService {

	@Autowired
	HotelRespository hotelRespository;
	
	@Override
	public Hotel saveHotel(Hotel hotel) {
		return hotelRespository.save(hotel);
	}

	@Override
	public Hotel getHotelByHotelName(String hotelName) {
		return hotelRespository.findByHotelName(hotelName);
	}

	@Override
	public List<Hotel> getHotelsByHotelName(String hotelName) {
		List<Hotel> hotels = hotelRespository.getHotelsByHotelName(hotelName);
		return hotels;
	}

	@Override
	public List<Hotel> getHotel() {
		List<Hotel> hotels = hotelRespository.findAll();
		return hotels;
	}

	@Override
	public Hotel getHotelByHotelId(int hotelId) {
		return hotelRespository.getHotelByHotelId(hotelId);
	}

}
