package com.synergisticit.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.synergisticit.domain.HotelRoom;
import com.synergisticit.domain.RoomType;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Integer>  {
	public HotelRoom getHotelRoomByType(RoomType roomType);
	
	@Query(value="select * from hotel_rooms where hotelRoomId in (select hotelRooms_hotelRoomId from hotels_hotel_rooms where Hotel_hotelId =:hotelId)" ,nativeQuery = true)
	public List<HotelRoom> getHotelRoomByHotelId(@RequestParam(value="hotelId") int hotelId);
}
