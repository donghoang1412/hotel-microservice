package com.synergisticit.service;

import com.synergisticit.domain.Review;

public interface ReviewService {
	public Review save(Review review);
	public Review getReviewByUserNameAndHotelId(String userName, int hotelId);
}
