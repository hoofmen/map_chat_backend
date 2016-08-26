package com.hoofmen.mapchat;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoofmen.mapchat.dto.Location;
import com.hoofmen.mapchat.dto.MapMessage;

@Controller
public class MapChatAPI {
	
//	private static List<MapMessage> messageList = new ArrayList<MapMessage>();
//	static {
//		MapMessage m1 = new MapMessage();
//		m1.setDuration(10);
//		Location l1 = new Location();		
//		l1.setLat(37.76582);
//		l1.setLon(-121.90761);		
//		m1.setLocation(l1);
//		m1.setMessage("Hello neigbours!");
//		
//		MapMessage m2 = new MapMessage();
//		m2.setDuration(10);
//		Location l2 = new Location();
//		l2.setLat(37.76682);
//		l2.setLon(-121.90775);		
//		m2.setLocation(l2);
//		m2.setMessage("Shut the hell UP!!!");
//		
//		messageList.add(m1);
//		messageList.add(m2);
//	}

	@RequestMapping(value = "/get-messages", method = RequestMethod.GET)
	public @ResponseBody List<MapMessage> getAreaMessages(
			//TODO: Should get a location object instead
			@RequestParam(value = "lat", required = true) double lat,
			@RequestParam(value = "lon", required = true) double lon,
			@RequestParam(value = "rad", required = true) double rad) {

		List<MapMessage> messageList = new ArrayList<MapMessage>();
		MapMessage mm = null;
		for (int i=0; i<10; i++){
			mm = new MapMessage();
			mm.setLocation(getRandomLocationByLatLonRad(lat, lon, rad));
			mm.setDuration(i * 4);
			mm.setMessage("This is message [" + i + "] from the server!");
			messageList.add(mm);
		}

		return messageList;
	}
	
	@RequestMapping(value = "/put-message", method = RequestMethod.POST)
	public @ResponseBody MapMessage message(@RequestBody MapMessage message){
		message.setMessage("response: " + message.getMessage());
		return message;
	}
	
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
