package com.hoofmen.mapchat.messages.beans;

public class Location {
	private double lon;
	private double lat;
	
	/**
	 * @return the lonitude
	 */
	public double getLon() {
		return lon;
	}
	/**
	 * @param lon the longitude to set
	 */
	public void setLon(double lon) {
		this.lon = lon;
	}
	/**
	 * @return the latitude
	 */
	public double getLat() {
		return lat;
	}
	/**
	 * @param lat the latitude to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}
}
