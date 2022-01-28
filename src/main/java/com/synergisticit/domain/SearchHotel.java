package com.synergisticit.domain;

public class SearchHotel {
	private String hotelName;
	private String checkInDate;
	private String checkOutDate;
	private int noRooms;
	private int noGuests;
	
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
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
	public int getNoRooms() {
		return noRooms;
	}
	public void setNoRooms(int noRooms) {
		this.noRooms = noRooms;
	}
	public int getNoGuests() {
		return noGuests;
	}
	public void setNoGuests(int noGuests) {
		this.noGuests = noGuests;
	}
	
	
}