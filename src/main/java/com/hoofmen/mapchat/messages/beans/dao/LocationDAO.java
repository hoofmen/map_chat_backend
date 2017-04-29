package com.hoofmen.mapchat.messages.beans.dao;

public class LocationDAO {

	private String type;

	private double[] coordinates;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	}

}
