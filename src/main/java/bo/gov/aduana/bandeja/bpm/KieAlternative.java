/**
 * 
 */
package bo.gov.aduana.bandeja.bpm;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientRequestFactory;
import org.jboss.resteasy.client.ClientResponse;
import org.jbpm.services.task.query.TaskSummaryImpl;
import org.kie.api.task.model.TaskSummary;
import org.kie.services.client.api.RestRequestHelper;

import bo.gov.aduana.bandeja.bpm.response.BaseBpmResponse;
import bo.gov.aduana.bandeja.bpm.response.ProcessInstanceResponse;
import bo.gov.aduana.bandeja.bpm.response.TaskSummaryList;

/**
 * @author Jorge Morando
 * 
 */
public class KieAlternative {

	private static final Logger log = Logger.getLogger(KieAlternative.class);
	
	private String userId;
	
	private String password;
	
	private URL baseRestUrl;
	
	public KieAlternative(URL baseRestUrl,String userId, String password){
		this.userId = userId;
		this.password = password;
		this.baseRestUrl = baseRestUrl;
	}
	
	
	public ClientResponse<?> execute(Command command) throws Exception{
		return execute(command,null,null,null);
	}
	
	public ClientResponse<?> execute(Command command, Map<String,String> pathParams, Boolean union) throws Exception{
		return execute(command,pathParams,null,null);
	}
	
	public ClientResponse<?> execute(Command command, Map<String,String> pathParams, HashMap<String,List<String>> queryParams, Boolean union) throws Exception{
		ClientRequestFactory requestFactory;
        
		
		String commandUrl="";
		
		if(pathParams!=null){
			commandUrl = baseRestUrl.getPath() + command.getUrl(pathParams);
		}
		else if(queryParams!=null){
			commandUrl = baseRestUrl.getPath() + command.getUrlWhitQuery(queryParams);
			if(union!=null && union){
				commandUrl=commandUrl+ "&union=" + Boolean.toString(union);
			}
		}
		
        String urlString = new URL(baseRestUrl, commandUrl).toExternalForm();
//        
        requestFactory = RestRequestHelper.createRequestFactory(baseRestUrl, userId, password,30);
     
		ClientRequest request = requestFactory.createRequest(urlString);
		request.accept(command.getContentType());
		
		if(queryParams==null)
			return request.get(command.getResponseClass());
		else
			return request.get(command.getResponseClass());
		
	}

}
