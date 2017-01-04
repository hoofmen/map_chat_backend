package com.hoofmen.mapchat;

import com.hoofmen.mapchat.messages.MessageService;
import com.hoofmen.mapchat.messages.beans.Location;
import com.hoofmen.mapchat.messages.beans.MapMessageRequest;
import com.hoofmen.mapchat.messages.beans.MapMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MapChat {
	
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

	@Autowired
	public MessageService messageService;

	/**
	 * Get a list of messages depending on:
	 * @param lat Latitude
	 * @param lon Longitude
	 * @param rad Radius
	 * @param max_messages Max number of messages to receive
	 * @return
	 */
	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	public @ResponseBody List<MapMessage> getAreaMessages(
			//TODO: Should get a location object instead
			@RequestParam(value = "lat", required = true) double lat,
			@RequestParam(value = "lon", required = true) double lon,
			@RequestParam(value = "rad", required = true) double rad,
			@RequestParam(value = "max_messages", required = true) int max_messages) throws Exception {

		MapMessageRequest mapMessageRequest = buildMapMessageRequest(lat, lon, rad, max_messages);

		return messageService.getMapMessages(mapMessageRequest);
	}

	private MapMessageRequest buildMapMessageRequest(double lat, double lon, double rad, int max_messages){
		MapMessageRequest mapMessageRequest = new MapMessageRequest();
		Location location = new Location();
		location.setLat(lat);
		location.setLon(lon);
		mapMessageRequest.setLocation(location);
		mapMessageRequest.setRadius(rad);
		mapMessageRequest.setMaxMessages(max_messages);
		return mapMessageRequest;
	}

	/**
	 * Post a new message
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/messages", method = RequestMethod.POST)
	public @ResponseBody MapMessage message(@RequestBody MapMessage message){
		message.setMessage("response: " + message.getMessage());
		return message;
	}
}
