package com.hoofmen.mapchat.security;

import java.util.HashMap;
import java.util.Map;


public class AuthenticationService {
	
	private static Map<String,String> tokens = new HashMap<String,String>();
	static {
		tokens.put("thisisaverysecrettoken", "thisisaverysecrettoken");
	}
	
	public boolean authenticate(String authToken) {		
		return tokens.containsKey(authToken);
	}

}
