package bo.gov.aduana.bandeja.filters;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.jmx.LoggerDynamicMBean;

public class AuthFilter implements Filter{

	public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
 
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		httpResponse.addHeader("Pragma", "no-cache");
		httpResponse.addHeader("Cache-Control", "no-cache");
		// Stronger according to blog comment below that references HTTP spec
		httpResponse.addHeader("Cache-Control", "no-store");
		httpResponse.addHeader("Cache-Control", "must-revalidate");
		// some date in the past
		httpResponse.addHeader("Expires", "Fri, 11 Jul 1983 13:50:00 GMT");

		
		// Si es una vista se deja pasar
		if (httpRequest.getServletPath().contains("src/view")) {
			chain.doFilter(request, response);
		} else
		// Si entra a cualquier página y no está loguedo se manda al login, a
		// menos que sea la del login, la página de error o una plantilla
		if (!httpRequest.getServletPath().matches("(index)\\.html$")
			&& (httpRequest.getSession(false) == null || httpRequest
			    .getSession().getAttribute("token") == null)) {
			// requiere autenticarse
//			httpResponse.sendRedirect("#/login");
		} else
		// si ya se autenticó, se le envía a la página de principal
		if (httpRequest.getSession(false) != null
				&& httpRequest.getSession().getAttribute("token") != null) {				
				chain.doFilter(request, response);
		}else
			chain.doFilter(request, response);
    }
    public void init(FilterConfig config) throws ServletException {
         
        //Get init parameter
        String testParam = config.getInitParameter("test-param");
         
        //Print the init parameter
        System.out.println("Test Param: " + testParam);
    }
    public void destroy() {
        //add code to release any resource
    }

}

