package com.synergisticit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synergisticit.domain.Review;
import com.synergisticit.respository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private ReviewRepository reviewRepository;
	@Override
	public Review save(Review review) {
		return reviewRepository.save(review);
	}
	@Override
	public Review getReviewByUserNameAndHotelId(String userName, int hotelId) {
		return reviewRepository.getReviewByUserNameAndHotelId(userName, hotelId);
	}

}
