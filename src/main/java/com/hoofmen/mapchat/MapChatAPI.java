package com.hoofmen.mapchat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hoofmen.mapchat.dto.Location;
import com.hoofmen.mapchat.dto.MapMessage;

@Controller
public class MapChatAPI {
	
	private static List<MapMessage> messageList = new ArrayList<MapMessage>();
	static {
		MapMessage m1 = new MapMessage();
		m1.setDuration(10);
		Location l1 = new Location();		
		l1.setLat(37.76482);
		l1.setLon(-121.90785);
		m1.setLocation(l1);
		m1.setMessage("Hello neigbours!");
		
		MapMessage m2 = new MapMessage();
		m2.setDuration(10);
		Location l2 = new Location();
		l2.setLat(37.76682);
		l2.setLon(-121.90775);
		m2.setLocation(l2);
		m2.setMessage("Shut the hell UP!!!");
		
		messageList.add(m1);
		messageList.add(m2);
	}

	@RequestMapping(value = "/get-messages", method = RequestMethod.POST)
	public @ResponseBody List<MapMessage> getAreaMessages(){
		return messageList;
	}
	
	@RequestMapping(value = "/put-message", method = RequestMethod.POST)
	public @ResponseBody MapMessage message(@RequestBody MapMessage message){
		message.setMessage("response: " + message.getMessage());
		return message;
	}
	
}
