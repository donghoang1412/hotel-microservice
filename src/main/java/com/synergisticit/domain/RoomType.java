package com.synergisticit.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roomtype")
public class RoomType {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int typeId;
	private String name;
	private int maxGuest;
	
	public int getMaxGuest() {
		return maxGuest;
	}
	public void setMaxGuest(int maxGuest) {
		this.maxGuest = maxGuest;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
