package com.hoofmen.mapchat.messages.beans.dao;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.UUID;

@Document(collection = "mapmessages")
public class MapMessageDAO implements Serializable {
	@Id
	private UUID id;
	private LocationDAO location;
	private String message;
	private int duration;


	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocationDAO getLocation() {
		return location;
	}

	public void setLocation(LocationDAO location) {
		this.location = location;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
}
