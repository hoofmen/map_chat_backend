package com.hoofmen.mapchat;

import java.util.List;
import java.util.Random;

import com.hoofmen.mapchat.shared.UrlParamNotFoundException;
import com.hoofmen.mapchat.messages.MessageService;
import com.hoofmen.mapchat.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoofmen.mapchat.model.ViewArea;
import com.hoofmen.mapchat.model.Location;
import com.hoofmen.mapchat.model.MapMessage;

import javax.swing.text.View;

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

        //just to test the ErrorHandler
		if (max_messages == 0){
            throw new UrlParamNotFoundException(AppConstants.ERROR_URL_PARAM_NOT_FOUND);
        }
		ViewArea viewArea = buildViewArea(lat, lon, rad);

		return messageService.getMapMessagesGivenViewArea(viewArea);
	}

	private ViewArea buildViewArea(double lat, double lon, double rad){
		ViewArea viewArea = new ViewArea();
		Location location = new Location();
		location.setLat(lat);
		location.setLon(lon);
		viewArea.setLocation(location);
		viewArea.setRadius(rad);
		return viewArea;
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
