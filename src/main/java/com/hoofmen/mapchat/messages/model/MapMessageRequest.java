package com.hoofmen.mapchat.messages.model;

/**
 * Created by osman on 1/3/17.
 * ViewArea class represents the surface represented by its: latitude, longitude and radius.
 */
public class MapMessageRequest {
    private Location location;
    private double radius;
    private int maxMessages;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public int getMaxMessages() {
        return maxMessages;
    }

    public void setMaxMessages(int maxMessages) {
        this.maxMessages = maxMessages;
    }
}
