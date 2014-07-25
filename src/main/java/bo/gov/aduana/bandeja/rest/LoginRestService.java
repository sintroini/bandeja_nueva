package bo.gov.aduana.bandeja.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import bo.gov.aduana.bandeja.entities.User;
import bo.gov.aduana.bandeja.service.BpmService;
import bo.gov.aduana.bandeja.service.SessionService;
import bo.gov.aduana.bandeja.util.AuthResponse;

@Path("/session")
@RequestScoped
public class LoginRestService {

	@Inject BpmService bpmSrv;
	
	@Inject User user;
		
	@Inject Logger log;
	
	 @Inject private SessionService sessionSrv;
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
    public AuthResponse login(@FormParam("username") String username,
			@FormParam("password") String password,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response){

		AuthResponse authRsp = new AuthResponse();
		log.info("Usuario " +username + " logueado");
		
		user.setUsername(username);
		user.setPassword(password);
		HttpSession session = sessionSrv.createSession(request, user);
				
		try {
			
			if(session.getAttribute("urlRequest")!=null){
				request.getHeaderNames();
				response.sendRedirect("http://"+request.getHeader("host")+session.getAttribute("urlRequest"));
			}
			else 
				response.sendRedirect("http://"+request.getHeader("host")
						+ request.getContextPath());
			
			authRsp.setToken((String)session.getAttribute("token"));
			authRsp.setUser(user);
		
		} catch (Exception e) {
			log.debug("Error al loguear al usuario " + user.getUsername());
		}
		
		return authRsp;
    }
	
	@GET
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
    public String logout(@Context HttpServletRequest request){
		
		try {
			
			HttpSession session = request.getSession(false);
			if (session != null) {
				String token = (String) session.getAttribute("token");
				session.removeAttribute("token");
				session.removeAttribute("user");
				session.invalidate();
				log.info("Invalidado token: " + token);
			} 

			return null;
		
		} catch (Exception e) {
			log.debug("Error al loguear al usuario " + user.getUsername());
		}
		
		return null;
    }

}
