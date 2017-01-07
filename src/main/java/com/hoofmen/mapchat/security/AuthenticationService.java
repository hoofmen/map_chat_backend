package com.hoofmen.mapchat.security;

import com.hoofmen.mapchat.shared.AppConstants;

import java.util.HashMap;
import java.util.Map;


public class AuthenticationService {
	
	private static Map<String,String> tokens = new HashMap<String,String>();
	static {
		tokens.put(AppConstants.TOKEN, AppConstants.TOKEN);
	}
	
	public boolean authenticate(String authToken) {		
		return tokens.containsKey(authToken);
	}

}
