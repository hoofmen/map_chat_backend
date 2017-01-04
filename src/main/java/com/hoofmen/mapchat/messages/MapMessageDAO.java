package com.hoofmen.mapchat.messages;

import com.hoofmen.mapchat.messages.beans.Location;
import com.hoofmen.mapchat.messages.beans.MapMessage;
import com.hoofmen.mapchat.messages.beans.MapMessageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by osman on 1/2/17.
 */
@Service
public class MapMessageDAO implements MessageDAO{
    //TODO: Embedded DB (Derby, H2, etc.)


    /**
     * Returns a list of MapMessages give a ViewArea (Currently a mock)
     * @param mapMessageRequest
     * @return
     */
    @Override
    public List<MapMessage> getMapMessagesGivenViewArea(MapMessageRequest mapMessageRequest){
        List<MapMessage> messageList = new ArrayList<>();
        MapMessage mm;
        for (int i=0; i<mapMessageRequest.getMaxMessages(); i++){
            mm = new MapMessage();
            mm.setLocation(getRandomLocationByLatLonRad(mapMessageRequest.getLocation().getLat(),
                                                        mapMessageRequest.getLocation().getLon(),
                                                        mapMessageRequest.getRadius()));
            mm.setDuration(i * 4);
            mm.setMessage("This is message [" + i + "] from the server!");
            messageList.add(mm);
        }

        return messageList;
    }

    /**
     * Just a helper to get mocked locations given:
     * @param lat
     * @param lon
     * @param rad
     * @return
     */
    private Location getRandomLocationByLatLonRad(double lat, double lon, double rad){
        Random random = new Random();

        // Convert radius from meters to degrees
        double radiusInDegrees = rad / 111000f;

        double u = random.nextDouble();
        double v = random.nextDouble();
        double w = radiusInDegrees * Math.sqrt(u);
        double t = 2 * Math.PI * v;
        double x = w * Math.cos(t);
        double y = w * Math.sin(t);

        // Adjust the x-coordinate for the shrinking of the east-west distances
        double new_x = x / Math.cos(lat);

        double foundLongitude = new_x + lon;
        double foundLatitude = y + lat;

        Location newLocation = new Location();

        newLocation.setLat(foundLatitude);
        newLocation.setLon(foundLongitude);

        return newLocation;
    }
}
