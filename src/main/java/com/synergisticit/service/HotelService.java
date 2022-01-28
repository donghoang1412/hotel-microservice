package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.Hotel;

public interface HotelService {
	public Hotel saveHotel(Hotel hotel);
	public List<Hotel> getHotel();
	public Hotel getHotelByHotelName(String hotelName);
	public Hotel getHotelByHotelId(int hotelId);
	public List<Hotel> getHotelsByHotelName(String hotelName);
}
