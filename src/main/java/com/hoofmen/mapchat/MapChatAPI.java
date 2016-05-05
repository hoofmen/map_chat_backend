package com.hoofmen.mapchat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MapChatAPI {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody Map<String,String> home(){
		Map<String, String> response = new HashMap<String,String>();
		response.put("hello","world!");
		return response;
	}
}
