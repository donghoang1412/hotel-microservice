package com.synergisticit.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.synergisticit.domain.Hotel;

@Repository
public interface HotelRespository extends JpaRepository<Hotel, Integer> {
	public Hotel findByHotelName(String hotelName);

	@Query(value="select * from hotels where hotelName like %:hotelName%",nativeQuery=true)
	public List<Hotel> getHotelsByHotelName(@Param("hotelName") String hotelName);
	
	public Hotel getHotelByHotelName(@Param("hotelName") String hotelName);
	
	public Hotel getHotelByHotelId(@Param("hotelId") int hotelId);
	
}