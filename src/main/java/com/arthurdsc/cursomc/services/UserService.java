package com.arthurdsc.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.arthurdsc.cursomc.security.UserSS;

@Service
public class UserService {
	
	public static UserSS authenticated(){
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
		
}
