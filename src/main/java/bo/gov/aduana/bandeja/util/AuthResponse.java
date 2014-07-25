package bo.gov.aduana.bandeja.util;

import bo.gov.aduana.bandeja.entities.User;

public class AuthResponse {
	private String token;
	
	private User user;
	
	public AuthResponse(User user) {
		this.user = user;
		this.token = user.getUsername()+"@"+StringUtility.generateRamdonString();
	}

	public AuthResponse() {
		// TODO Auto-generated constructor stub
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
