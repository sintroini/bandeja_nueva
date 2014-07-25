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
import bo.gov.aduana.bandeja.response.ProcessResponse;
import bo.gov.aduana.bandeja.service.BpmService;

@Path("/process")
@RequestScoped
public class ProcessRestService {

	@Inject BpmService bpmSrv;
		
	@Inject Logger log;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ProcessInstanceWrapper> getProcessIntances(@QueryParam("processDefinition") String processDefinition, @QueryParam("instance") Long processInstance) {
		List<ProcessInstanceWrapper> lstProcess = new ArrayList<ProcessInstanceWrapper>();
		
		
		try {
			if(processInstance!=null){
				ProcessInstanceWrapper instance = null;
				instance = bpmSrv.getActiveProcessInstance(processInstance, null);
				lstProcess.add(instance);
			}else
				lstProcess = bpmSrv.getActiveProcessInstances(processDefinition, null);
		
		} catch (Exception e) {
			log.debug("Error al recuperar las instancias de procesos");
		}
		
		return lstProcess;
	}
	
	@GET
    @Path("/instance/{instanceId:[0-9]+}/variables")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> viewInstanceVariables(@PathParam("instanceId") Long processInstance, @Context HttpServletRequest request) throws Exception {

    	List <VariableWrapper> lstVar = bpmSrv.getProcessInstanceVariables(processInstance, null);
    	List<String> response=new ArrayList<String>();
    	
    	for (VariableWrapper var : lstVar) {
			response.add("<b>"+var.getName()+ "</b> : " + var.getValue()+"<br>");
		}
    	return response;
    }
	
	@GET
    @Path("/instance/{instanceId:[0-9]+}/completed/variables")
    @Produces(MediaType.APPLICATION_JSON)
    public List<String> viewInstanceVariablesCompleted(@PathParam("instanceId") Long processInstance, @Context HttpServletRequest request) throws Exception {

    	List <VariableWrapper> lstVar = bpmSrv.getProcessCompletedVariables(processInstance);
    	List<String> response=new ArrayList<String>();
    	
    	for (VariableWrapper var : lstVar) {
			response.add("<b>"+var.getName()+ "</b> : " + var.getValue()+"<br>");
		}
    	return response;
    }
}
