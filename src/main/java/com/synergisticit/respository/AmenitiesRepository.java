package com.synergisticit.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.synergisticit.domain.Amenities;

@Repository
public interface AmenitiesRepository extends JpaRepository<Amenities, Integer>{
	@Query (value="select * from amenities where name=:amenitiesName", nativeQuery = true)
	public Amenities findByName(@Param(value = "amenitiesName") String amenitiesName);
}
