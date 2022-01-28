package com.synergisticit.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.synergisticit.domain.Amenities;
import com.synergisticit.domain.Hotel;
import com.synergisticit.domain.HotelRoom;
import com.synergisticit.domain.Review;
import com.synergisticit.domain.RoomType;
import com.synergisticit.domain.SearchHotel;
import com.synergisticit.models.Booking;
import com.synergisticit.respository.HotelRoomRepository;
import com.synergisticit.restclient.RestClient;
import com.synergisticit.service.AmenitiesService;
import com.synergisticit.service.HotelRoomService;
import com.synergisticit.service.HotelService;
import com.synergisticit.service.ReviewService;
import com.synergisticit.service.RoomTypeService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class HotelController {

	@Autowired
	HotelService hotelService;

	@Autowired
	AmenitiesService amenitiesService;

	@Autowired
	HotelRoomService hotelRoomService;

	@Autowired
	RoomTypeService roomTypeService;

	@Autowired
	ReviewService reviewService;

	@Autowired
	RestClient restClient;

	@RequestMapping(value = "/findHotelByHotelName/{hotelName}", method = RequestMethod.GET)
	public ResponseEntity<Hotel> findHotelByHotelName(@PathVariable String hotelName) {
		Hotel hotel = hotelService.getHotelByHotelName(hotelName);
		return new ResponseEntity<Hotel>(hotel, HttpStatus.OK);
	}

	@RequestMapping(value = "/saveHotel", method = RequestMethod.POST)
	public ResponseEntity<Hotel> saveAuto(@RequestBody Hotel hotel) {
		System.out.println("Hotel name: " + hotel.getHotelName());
		Hotel updatedHotel = hotelService.saveHotel(hotel);
		return new ResponseEntity<Hotel>(updatedHotel, HttpStatus.OK);
	}

	@PostMapping(value = "/saveAmenitiesHotel")
	public ResponseEntity<Hotel> saveHotel(@RequestBody JsonNode node) {
		String hotelName = node.get("hotelName").asText();
		String amenName = node.get("amenities").asText();
		Amenities amenities = amenitiesService.getAmenitiesByName(amenName);
		Hotel searchedHotel = hotelService.getHotelByHotelName(hotelName);

		if (amenities == null) {
			Amenities saveAmenities = new Amenities();
			saveAmenities.setName(amenName);
			Amenities persistAmenities = amenitiesService.saveAmenities(saveAmenities);
			Set<Amenities> setAmenities = searchedHotel.getAmenities();
			setAmenities.add(persistAmenities);
		} else {
			Set<Amenities> setAmenities = searchedHotel.getAmenities();
			setAmenities.add(amenities);
		}
		Hotel updatedHotel = hotelService.saveHotel(searchedHotel);
		return new ResponseEntity<>(updatedHotel, HttpStatus.OK);

	}

	@GetMapping(value = "/returnHello")
	public String returnHello() {
		String s = "hello";
		return s;
	}

	@GetMapping(value = "/getHotels")
	public List<Hotel> getHotels(@RequestParam(value = "hotelName") String hotelName) {
		List<Hotel> list = hotelService.getHotelsByHotelName(hotelName);
		return list;
	}

	@PostMapping(value = "/searchHotels")
	public List<Hotel> searchHotels(@RequestBody String body) {
		long startTime = System.currentTimeMillis();
		Gson gson = new Gson();
		SearchHotel searchHotel = gson.fromJson(body, SearchHotel.class);
		List<Hotel> hotels = new ArrayList<>();
		System.out.println(searchHotel.getHotelName());
		if (searchHotel.getHotelName().equals("allHotels"))
			hotels = hotelService.getHotel();
		else
			hotels = hotelService.getHotelsByHotelName(searchHotel.getHotelName());
		List<Hotel> hotelAvail = new ArrayList<>();

		for (Hotel eachHotel : hotels) {
			long checkinDate = 0;
			long checkoutDate = 0;
			if (searchHotel.getCheckInDate() != null)
				checkinDate = Long.parseLong(searchHotel.getCheckInDate());
			if (searchHotel.getCheckOutDate() != null)
				checkoutDate = Long.parseLong(searchHotel.getCheckOutDate());
			int searchNoGuest = searchHotel.getNoGuests();
			int searchNoRoom = searchHotel.getNoRooms();
			System.out.println(eachHotel.getHotelId());
			// This will get a list of bookingList of each hotel from the hotelList
			List<Object> bookingList = restClient.getBookings(eachHotel.getHotelId());
			// This will get every rooms from that hotel, so that we can do some logic to
			// calculate if
			// there are still rooms left in this specific hotel
			List<HotelRoom> hotelRoomList = hotelRoomService.getHotelRoomByHotelId(eachHotel.getHotelId());
			int totalRoomAvail = 0;
			for (HotelRoom room : hotelRoomList) {
				int roomId = room.getHotelRoomId();
				System.out.println("roomId " + roomId);
				int noRoom = room.getNoRooms();
				System.out.println("noRoom " + noRoom);
				RoomType roomtype = roomTypeService.getRoomTypeByHotelRoomId(roomId);
				System.out.println(roomtype.getMaxGuest());
				if (searchNoGuest <= roomtype.getMaxGuest()) {
					totalRoomAvail += noRoom;
				}
			}
			System.out.println("totalRoomAvail " + totalRoomAvail);

			for (Object obj : bookingList) {
				Gson bookingGson = new Gson();
				String eachBooking = obj.toString();
				eachBooking = eachBooking.replace(" ", "");
				Booking booking = bookingGson.fromJson(eachBooking, Booking.class);
				System.out.println(booking.getCheckInDate());
				System.out.println(booking.getCheckOutDate());
				if (checkinDate != 0 || checkoutDate != 0)
					if ((checkinDate > Long.parseLong(booking.getCheckInDate())
							&& checkinDate < Long.parseLong(booking.getCheckOutDate()))
							|| (checkoutDate > Long.parseLong(booking.getCheckInDate())
									&& checkoutDate < Long.parseLong(booking.getCheckOutDate()))) {
						totalRoomAvail -= booking.getNoRooms();
					}
			}
			System.out.println("totalRoomAvail " + totalRoomAvail);
			if (totalRoomAvail >= searchNoRoom) {
				System.out.println("This hotel is still available for booking ");
				hotelAvail.add(eachHotel);
			}
			long endTime = System.currentTimeMillis();
			System.out.println("Total time took " + (endTime - startTime));
		}
		return hotelAvail;
	}
	/*
	 * // This is using Future (create new Threads)
	 * 
	 * @PostMapping(value = "/searchHotels") public List<Hotel>
	 * searchHotels(@RequestBody String body) throws InterruptedException,
	 * ExecutionException { final long startTime = System.currentTimeMillis(); Gson
	 * gson = new Gson(); SearchHotel searchHotel = gson.fromJson(body,
	 * SearchHotel.class); List<Hotel> hotels = new ArrayList<>();
	 * System.out.println(searchHotel.getHotelName()); if
	 * (searchHotel.getHotelName().equals("allHotels")) hotels =
	 * hotelService.getHotel(); else hotels =
	 * hotelService.getHotelsByHotelName(searchHotel.getHotelName()); List<Hotel>
	 * hotelAvail = new ArrayList<>();
	 * 
	 * for (Hotel eachHotel : hotels) { long checkinDate = 0; long checkoutDate = 0;
	 * if (searchHotel.getCheckInDate() != null) checkinDate =
	 * Long.parseLong(searchHotel.getCheckInDate()); if
	 * (searchHotel.getCheckOutDate() != null) checkoutDate =
	 * Long.parseLong(searchHotel.getCheckOutDate()); int searchNoGuest =
	 * searchHotel.getNoGuests(); int searchNoRoom = searchHotel.getNoRooms();
	 * System.out.println(eachHotel.getHotelId()); // This will get a list of
	 * bookingList of each hotel from the hotelList List<Object> bookingList =
	 * restClient.getBookings(eachHotel.getHotelId()); // This will get every rooms
	 * from that hotel, so that we can do some logic to // calculate if // there are
	 * still rooms left in this specific hotel List<HotelRoom> hotelRoomList =
	 * hotelRoomService.getHotelRoomByHotelId(eachHotel.getHotelId());
	 * 
	 * Future<Integer> futureGetTotalRoomAvail =
	 * getTotalRoomAvailFuture(hotelRoomList, searchNoGuest);
	 * 
	 * Future<Integer> totalRoomBookFuture = getTotalRoomBookFuture(bookingList,
	 * checkinDate, checkoutDate);
	 * 
	 * int totalRoomAvail = futureGetTotalRoomAvail.get();
	 * 
	 * int totalRoomBook = totalRoomBookFuture.get();
	 * 
	 * totalRoomAvail -= totalRoomBook;
	 * 
	 * System.out.println("totalRoomAvail " + totalRoomAvail); if (totalRoomAvail >=
	 * searchNoRoom) {
	 * System.out.println("This hotel is still available for booking ");
	 * hotelAvail.add(eachHotel); } }
	 * 
	 * final long endTime = System.currentTimeMillis();
	 * 
	 * System.out.println("Total time took: " + (endTime - startTime));
	 * 
	 * return hotelAvail; }
	 * 
	 * private Future<Integer> getTotalRoomBookFuture(final List<Object>
	 * bookingList, final long checkinDate, final long checkoutDate) {
	 * Future<Integer> futureResult = null; ExecutorService executor =
	 * Executors.newSingleThreadExecutor(); Callable<Integer> callable = () -> { int
	 * totalRoomAvail = 0; for (Object obj : bookingList) { Gson bookingGson = new
	 * Gson(); String eachBooking = obj.toString(); eachBooking =
	 * eachBooking.replace(" ", ""); Booking booking =
	 * bookingGson.fromJson(eachBooking, Booking.class);
	 * System.out.print("booking: " + booking);
	 * System.out.println(booking.getCheckInDate());
	 * System.out.println(booking.getCheckOutDate()); if (checkinDate != 0 ||
	 * checkoutDate != 0) if ((checkinDate >
	 * Long.parseLong(booking.getCheckInDate()) && checkinDate <
	 * Long.parseLong(booking.getCheckOutDate())) || (checkoutDate >
	 * Long.parseLong(booking.getCheckInDate()) && checkoutDate <
	 * Long.parseLong(booking.getCheckOutDate()))) { totalRoomAvail +=
	 * booking.getNoRooms(); } }
	 * 
	 * System.out.print("checkinDate: " + checkinDate);
	 * System.out.print("checkoutDate: " + checkoutDate);
	 * System.out.println("totalRoomAvail In Future "+ totalRoomAvail); return
	 * totalRoomAvail; }; futureResult = executor.submit(callable);
	 * executor.shutdown(); return futureResult; }
	 * 
	 * private Future<Integer> getTotalRoomAvailFuture(final List<HotelRoom>
	 * hotelRoomList, final int searchNoGuest) { Future<Integer> futureResult =
	 * null; ExecutorService executor = Executors.newSingleThreadExecutor();
	 * 
	 * Callable<Integer> callable = () -> { int totalRoomBook = 0; for (HotelRoom
	 * room : hotelRoomList) { int roomId = room.getHotelRoomId();
	 * System.out.println("roomId " + roomId); int noRoom = room.getNoRooms();
	 * System.out.println("noRoom " + noRoom); RoomType roomtype =
	 * roomTypeService.getRoomTypeByHotelRoomId(roomId);
	 * System.out.println(roomtype.getMaxGuest()); if (searchNoGuest <=
	 * roomtype.getMaxGuest()) { totalRoomBook += noRoom; } }
	 * System.out.println("totalRoomAvail In Future "+ totalRoomBook);
	 * 
	 * return totalRoomBook; };
	 * 
	 * futureResult = executor.submit(callable);
	 * 
	 * executor.shutdown();
	 * 
	 * return futureResult; }
	 */

	@GetMapping(value = "/getHotelsById")
	public Hotel getHotelsById(@RequestParam(value = "hotelId") int hotelId) {
		Hotel hotel = hotelService.getHotelByHotelId(hotelId);
		return hotel;
	}

	@GetMapping(value = "/getAllHotels")
	public List<Hotel> getAllHotels() {
		List<Hotel> list = hotelService.getHotel();
		return list;
	}

	@RequestMapping(value = "/saveRoomType", method = RequestMethod.POST)
	public ResponseEntity<RoomType> saveRoomType(@RequestBody RoomType roomType) {
		System.out.println("RoomType name: " + roomType.getName());
		RoomType updatedRoomType = roomTypeService.saveRoomType(roomType);
		return new ResponseEntity<RoomType>(updatedRoomType, HttpStatus.OK);
	}

	@PostMapping(value = "/saveHotelRoom")
	public ResponseEntity<Hotel> saveHotelRoom(@RequestBody JsonNode node) {
		String hotelName = node.get("hotelName").asText();
		String roomTypeName = node.get("type").asText();
		Hotel searchedHotel = hotelService.getHotelByHotelName(hotelName);
		RoomType roomType = roomTypeService.getRoomType(roomTypeName);
		HotelRoom saveHotelRoom = new HotelRoom();
		saveHotelRoom.setDescription(node.get("description").asText());
		saveHotelRoom.setDiscount(node.get("discount").asInt());
		saveHotelRoom.setHotelName(hotelName);
		saveHotelRoom.setNoRooms(node.get("noRooms").asInt());
		saveHotelRoom.setPolicies(node.get("policies").asText());
		saveHotelRoom.setPrice(node.get("price").asInt());
		saveHotelRoom.setRoomType(roomTypeName);
		if (roomType == null) {
			RoomType saveRoomType = new RoomType();
			saveRoomType.setName(roomTypeName);
			RoomType persistRoomType = roomTypeService.saveRoomType(saveRoomType);
			saveHotelRoom.setType(persistRoomType);
		}
		saveHotelRoom.setType(roomType);
		HotelRoom persistHotelRoom = hotelRoomService.saveHotelRoom(saveHotelRoom);
		Set<HotelRoom> setHotelRoom = searchedHotel.getHotelRooms();
		setHotelRoom.add(persistHotelRoom);

		Hotel updatedHotel = hotelService.saveHotel(searchedHotel);
		return new ResponseEntity<>(updatedHotel, HttpStatus.OK);
	}

	@PostMapping(value = "/saveReview")
	public ResponseEntity<?> saveReview(@RequestBody String body) {
		Gson gson = new Gson();
		Review hotelReview = gson.fromJson(body, Review.class);
		Hotel hotel = hotelService.getHotelByHotelId(hotelReview.getHotelId());
		List<Review> review = hotel.getReview();
		Review saveReview = reviewService.getReviewByUserNameAndHotelId(hotelReview.getUserName(),
				hotelReview.getHotelId());
		if (saveReview == null) {
			review.add(hotelReview);
			hotel.setReview(review);
		}
		else {
			hotelReview.setReview_id(saveReview.getReview_id());
			System.out.println("get in else");
			for(int i = 0; i<review.size(); i++) {
				if(review.get(i).getHotelId() == hotelReview.getHotelId()&& review.get(i).getUserName().equals(hotelReview.getUserName())) {
					System.out.println("removed");
					review.remove(i);
				}
			}
			review.add(hotelReview);
			hotel.setReview(review);
		}	
		Hotel persiHotel = hotelService.saveHotel(hotel);
		return ResponseEntity.ok(persiHotel);
	}

}
