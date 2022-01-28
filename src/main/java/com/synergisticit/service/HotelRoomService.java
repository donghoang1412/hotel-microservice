package com.synergisticit.service;

import java.util.List;

import com.synergisticit.domain.HotelRoom;
import com.synergisticit.domain.RoomType;

public interface HotelRoomService {
	public HotelRoom saveHotelRoom(HotelRoom hotelRoom);
	public HotelRoom getHotelRoomByType(RoomType roomType);
	public List<HotelRoom> getHotelRoomByHotelId(int hotelId);
}
