package com.synergisticit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.HotelRoom;
import com.synergisticit.domain.RoomType;
import com.synergisticit.respository.HotelRoomRepository;

@Service
public class HotelRoomServiceImpl implements HotelRoomService {

	@Autowired
	HotelRoomRepository hotelRoomRepository;
	
	@Override
	public HotelRoom saveHotelRoom(HotelRoom hotelRoom) {
		return hotelRoomRepository.save(hotelRoom);
	}

	@Override
	public HotelRoom getHotelRoomByType(RoomType roomType) {
		return hotelRoomRepository.getHotelRoomByType(roomType);
	}

	@Override
	public List<HotelRoom> getHotelRoomByHotelId(int hotelId) {
		return hotelRoomRepository.getHotelRoomByHotelId(hotelId);
	}
	
}
