package com.synergisticit.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synergisticit.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
	public Review getReviewByUserNameAndHotelId (String userName, int hotelId);
}
