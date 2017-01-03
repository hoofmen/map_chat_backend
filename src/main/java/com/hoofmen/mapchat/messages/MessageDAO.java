package com.hoofmen.mapchat.messages;

import com.hoofmen.mapchat.model.Location;
import com.hoofmen.mapchat.model.MapMessage;
import com.hoofmen.mapchat.model.ViewArea;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by osman on 1/2/17.
 */
@Service
public class MessageDAO {
    //TODO: Embedded DB (Derby, H2, etc.)


    /**
     * Returns a list of MapMessages give a ViewArea (Currently a mock)
     * @param viewArea
     * @return
     */
    public List<MapMessage> getMapMessagesGivenViewArea(ViewArea viewArea){
        List<MapMessage> messageList = new ArrayList<>();
        MapMessage mm;
        for (int i=0; i<6; i++){
            mm = new MapMessage();
            mm.setLocation(getRandomLocationByLatLonRad(viewArea.getLocation().getLat(),
                                                        viewArea.getLocation().getLon(),
                                                        viewArea.getRadius()));
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
