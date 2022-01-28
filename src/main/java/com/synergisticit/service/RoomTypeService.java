package com.synergisticit.service;

import com.synergisticit.domain.RoomType;

public interface RoomTypeService {
	public RoomType saveRoomType(RoomType roomType);
	public RoomType getRoomType(String roomTypeName);
	public RoomType getRoomTypeByHotelRoomId(int hotelRoomId);
}
