package bo.gov.aduana.bandeja.rest;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import bo.gov.aduana.bandeja.bpm.kiewrapper.ProcessInstanceWrapper;
import bo.gov.aduana.bandeja.bpm.kiewrapper.VariableWrapper;
import bo.gov.aduana.bandeja.entities.User;
import bo.gov.aduana.bandeja.service.BpmService;
import bo.gov.aduana.bandeja.service.SessionService;

@Path("/process")
@RequestScoped
public class ProcessRestService {

	@Inject BpmService bpmSrv;
		
	@Inject Logger log;
	
	@Inject
	private User user;
	
	@Inject
	private SessionService sessionSrv;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProcessInstanceWrapper> getProcessIntances(@QueryParam("processDefinition") String processDefinition, @QueryParam("instance") Long processInstance,@Context HttpServletRequest request) {
		List<ProcessInstanceWrapper> lstProcess = new ArrayList<ProcessInstanceWrapper>();
		
		user = sessionSrv.getUser(request);
		
		try {
			if(processInstance!=null){
				ProcessInstanceWrapper instance = null;
				instance = bpmSrv.getActiveProcessInstance(processInstance, user);
				lstProcess.add(instance);
			}else
				lstProcess = bpmSrv.getActiveProcessInstances(processDefinition, user);
		
		} catch (Exception e) {
			log.debug("Error al recuperar las instancias de procesos");
		}
		
		return lstProcess;
	}
	
	@GET
    @Path("/instance/{instanceId:[0-9]+}/variables")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> viewInstanceVariables(@PathParam("instanceId") Long processInstance, @Context HttpServletRequest request) throws Exception {
		
		user = sessionSrv.getUser(request);

    	List <VariableWrapper> lstVar = bpmSrv.getProcessInstanceVariables(processInstance, user);
    	List<String> response=new ArrayList<String>();
    	
    	for (VariableWrapper var : lstVar) {
			response.add("<b>"+var.getName()+ "</b> : " + var.getValue()+"<br>");
		}
    	return response;
    }
	@GET
    @Path("/variables/{instanceId:[0-9]+}")
    @Produces(MediaType.APPLICATION_JSON)
    public List <VariableWrapper> getInstanceVariables(@PathParam("instanceId") Long processInstance, @Context HttpServletRequest request) throws Exception {
		
		user = sessionSrv.getUser(request);

    	List <VariableWrapper> lstVar = bpmSrv.getProcessInstanceVariables(processInstance, user);
    	
    	return lstVar;
    }
	
	@GET
    @Path("/instance/{instanceId:[0-9]+}/completed/variables")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> viewInstanceVariablesCompleted(@PathParam("instanceId") Long processInstance, @Context HttpServletRequest request) throws Exception {

		user = sessionSrv.getUser(request);

    	List <VariableWrapper> lstVar = bpmSrv.getProcessCompletedVariables(processInstance,user);
    	List<String> response=new ArrayList<String>();
    	
    	for (VariableWrapper var : lstVar) {
			response.add("<b>"+var.getName()+ "</b> : " + var.getValue()+"<br>");
		}
    	return response;
    }
}
