package com.hoofmen.mapchat;

import com.hoofmen.mapchat.messages.MessageService;
import com.hoofmen.mapchat.messages.beans.MapMessage;
import com.hoofmen.mapchat.shared.AppConstants;
import com.hoofmen.mapchat.shared.AppMessage;
import com.hoofmen.mapchat.shared.AppMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MapChat {

	@Autowired
	public MessageService messageService;

	@Autowired
	public AppMessageFactory appMessageFactory;

	/**
	 * Get a list of messages depending on:
	 * @param lat Latitude
	 * @param lng Longitude
	 * @param rad Radius
	 * @param max_messages Max number of messages to receive
	 * @return
	 */
	@RequestMapping(value = "/messages", method = RequestMethod.GET)
	public @ResponseBody List<MapMessage> getAreaMessages(
			//TODO: Should get a location object instead
			@RequestParam(value = "lat", required = true) double lat,
			@RequestParam(value = "lng", required = true) double lng,
			@RequestParam(value = "rad", required = true) double rad,
			@RequestParam(value = "max_messages", required = true) int max_messages) throws Exception {

		return messageService.getMapMessages(lat, lng, rad, max_messages);
	}

	@RequestMapping(value = "/messages/all", method = RequestMethod.GET)
	public @ResponseBody List<MapMessage> getAllMessages() throws Exception {
		List<MapMessage> messageList = messageService.getAllMapMessages();
		return messageList;
	}



	/**
	 * Post a new message
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "/messages", method = RequestMethod.POST)
	public @ResponseBody AppMessage postMessage(HttpServletRequest request, @RequestBody MapMessage message) throws Exception {
		messageService.saveMapMessage(message);
		return appMessageFactory.getSuccessMessage(request, AppConstants.OK_MESSAGE_SAVED);
	}
}
