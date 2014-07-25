package bo.gov.aduana.bandeja.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import bo.gov.aduana.bandeja.entities.User;
import bo.gov.aduana.bandeja.util.AuthResponse;

public class SessionService {

	private User user;
	private String token;
    private AuthResponse authResponse;
	
	public User getUser(HttpServletRequest request){
		
		HttpSession session = request.getSession(true);
    	authResponse = new AuthResponse((User)session.getAttribute("user"));
    	user = authResponse.getUser();
		return user;
	}
	
	public HttpSession createSession(HttpServletRequest request, User user){
		HttpSession session;
		
		AuthResponse loginInfo = new AuthResponse(user);
		session = request.getSession(true);
		session.setAttribute("user", loginInfo.getUser());
		session.setAttribute("token", loginInfo.getToken());
		
		return session;
	}
}
