package com.synergisticit.models;

import java.util.Set;

public class Booking {
	private int bookingId;
	private int hotelId; //communicates with hotel management to fetch hotel details
	private int hotelRoomId;	
	private int noRooms;
	private Set<Guest> guests;
	private String checkInDate;
	private String checkOutDate;

	private String bookedOnDate;
	private String status; //cancelled, completed (can be simply compared), upcoming
	private float price;
	private float discount;	
	private String customerMobile; //identify the customer who booked
	private String roomType;
	private String hotelName;
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public int getHotelId() {
		return hotelId;
	}
	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
	public int getHotelRoomId() {
		return hotelRoomId;
	}
	public void setHotelRoomId(int hotelRoomId) {
		this.hotelRoomId = hotelRoomId;
	}
	public int getNoRooms() {
		return noRooms;
	}
	public void setNoRooms(int noRooms) {
		this.noRooms = noRooms;
	}
	public Set<Guest> getGuests() {
		return guests;
	}
	public void setGuests(Set<Guest> guests) {
		this.guests = guests;
	}
	public String getCheckInDate() {
		return checkInDate;
	}
	public void setCheckInDate(String checkInDate) {
		this.checkInDate = checkInDate;
	}
	public String getCheckOutDate() {
		return checkOutDate;
	}
	public void setCheckOutDate(String checkOutDate) {
		this.checkOutDate = checkOutDate;
	}
	public String getBookedOnDate() {
		return bookedOnDate;
	}
	public void setBookedOnDate(String bookedOnDate) {
		this.bookedOnDate = bookedOnDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public float getDiscount() {
		return discount;
	}
	public void setDiscount(float discount) {
		this.discount = discount;
	}
	public String getCustomerMobile() {
		return customerMobile;
	}
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	
}
