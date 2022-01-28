package com.synergisticit.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import com.synergisticit.domain.RoomType;

@Repository
public interface RoomTypeeRepository extends JpaRepository<RoomType, Integer>{
	public RoomType getRoomTypeByName(@Param("roomTypeName") String roomTypeName);
	@Query(value="select * from roomtype where typeId = (select type_typeId from hotel_rooms where hotelRoomId =:hotelRoomId)",nativeQuery=true)
	public RoomType getRoomTypeByHotelRoomId(@RequestParam(value="hotelRoomId") int hotelRoomId);
}
