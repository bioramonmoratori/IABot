package com.IAbot.IAbot.service;

import org.springframework.stereotype.Service;

@Service
public class TokenService {
	
	public String obterTokenGmail() {
		
		String tokenGmail = "ACCESS_TOKEN";
		
		return tokenGmail;
	}
	
	public String obterTokenGpt() {
		
		String tokenGpt = "Token";
		
		return tokenGpt;
	}

	public String obterUrlEmail() {
		
		String email = "SEU_EMAIL";
		
		return email;
	}
	
}
