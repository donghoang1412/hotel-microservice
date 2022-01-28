package com.synergisticit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.RoomType;
import com.synergisticit.respository.RoomTypeeRepository;

@Service
public class RoomTypeServiceImpl implements RoomTypeService {

	@Autowired
	RoomTypeeRepository roomTypeRepository;
	@Override
	public RoomType saveRoomType(RoomType roomType) {
		return roomTypeRepository.save(roomType);
	}

	@Override
	public RoomType getRoomType(String roomTypeName) {
		return roomTypeRepository.getRoomTypeByName(roomTypeName);
	}

	@Override
	public RoomType getRoomTypeByHotelRoomId(int hotelRoomId) {
		return roomTypeRepository.getRoomTypeByHotelRoomId(hotelRoomId);
	}

}
