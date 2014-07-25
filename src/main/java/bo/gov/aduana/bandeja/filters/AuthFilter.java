package bo.gov.aduana.bandeja.filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@SuppressWarnings("unused")
public class AuthFilter implements Filter{
	

	private List<String> pagOnlyAdmin = Arrays.asList();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(true);

		
		httpResponse.addHeader("Pragma", "no-cache");
		httpResponse.addHeader("Cache-Control", "no-cache");
		// Stronger according to blog comment below that references HTTP spec
		httpResponse.addHeader("Cache-Control", "no-store");
		httpResponse.addHeader("Cache-Control", "must-revalidate");
		// some date in the past
		httpResponse.addHeader("Expires", "Fri, 11 Jul 1983 13:50:00 GMT");
		
		
		// No se considera la autenticación (sessión token) para los casos
		// login y logout
		
		
		String pathInfo = httpRequest.getPathInfo();
		
		if(!"/login".equals(pathInfo) && !"/logout".equals(pathInfo)){
			String token = (String) httpRequest.getHeader("host");
			String urlRequest = httpRequest.getRequestURI();
			
			if(httpRequest.getQueryString()!=null){
				urlRequest = urlRequest + "?" + httpRequest.getQueryString();
			}
			
			session.setAttribute("urlRequest", urlRequest);
			if (token == null || session == null
					|| session.getAttribute("token") == null
					|| session.getAttribute("user") == null) {
				
				httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				
				boolean ajaxRequest = "XMLHttpRequest".equals(httpRequest.getHeader("X-Requested-With"));
				
				if(!ajaxRequest)
					httpResponse.sendRedirect( httpRequest.getContextPath() + "/#/login");
				else {
					httpResponse.setHeader("error", "401");
				}
				return;
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		
	}

}

