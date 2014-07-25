package bo.gov.aduana.bandeja.bpm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.jbpm.services.task.query.TaskSummaryImpl;

import bo.gov.aduana.bandeja.bpm.response.BaseBpmResponse;
import bo.gov.aduana.bandeja.bpm.response.ProcessInstanceResponse;
import bo.gov.aduana.bandeja.bpm.response.ProcessCompletedResponse;
import bo.gov.aduana.bandeja.bpm.response.TaskSummaryList;

public enum Command {
	PROCESS_INSTANCE_WITH_VARIABLES("/runtime/{deploymentId}/withvars/process/instance/{processInstanceId}",ProcessInstanceResponse.class,MediaType.APPLICATION_JSON),
	PROCESS_INSTANCE("/runtime/{deploymentId}/process/instance/{processInstanceId}",ProcessInstanceResponse.class, MediaType.APPLICATION_JSON),
	TASK("/task/query?", MediaType.APPLICATION_XML),
	PROCESS_INSTANCE_HISTORY("/runtime/{deploymentId}/history/instance/{processInstanceId}/variable",ProcessCompletedResponse.class,MediaType.APPLICATION_JSON);

	private String wsUrl;
	private  Class<?> clazz;
	private String content;
	
	
	private Command(String wsUrl, Class<?> clazz, String content){
		this.wsUrl=wsUrl;
		this.clazz = clazz;
		this.content=content;
	}
	
	private Command(String wsUrl, String content){
		this.wsUrl=wsUrl;
		this.clazz=TaskSummaryList.class;
		this.content=content;
	}
	
	/**
	 * @return the clazz
	 */
	public Class<?> getResponseClass() {
		return clazz;
	}

	public String getUrl(){
		return wsUrl;
	}
	
	public String getUrl(Map<String,String> params){
		if(params!=null)
			return withPathParams(params);
		return getUrl();
	}
	
	private String withPathParams(Map<String,String> params){
		String url = new String(wsUrl);
		for (String key : params.keySet()) {
			String value = params.get(key);
			url = url.replaceAll("\\{"+key+"\\}", value);
		}
		return url;
	}
	
	public String getUrlWhitQuery(HashMap<String, List<String>> parameters) {
		String url = new String(wsUrl);
		String param;
		for (String key : parameters.keySet()) {
			if(parameters.get(key)!=null){
				for (String value : parameters.get(key)) {
					if(value!=null){
						param = key + "=" + value + "&";
						url = url + param;
					}
				}
			}
		}
		url= url.substring(0, url.length()-1);
		return url;
	}

	public String getContentType() {
		return content;
	}
}