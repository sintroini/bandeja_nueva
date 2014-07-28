package bo.gov.aduana.bandeja.rest;

import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.kie.api.task.model.Status;

import bo.gov.aduana.bandeja.bpm.kiewrapper.TaskWrapper;
import bo.gov.aduana.bandeja.entities.User;
import bo.gov.aduana.bandeja.service.BpmService;
import bo.gov.aduana.bandeja.service.QueryService;
import bo.gov.aduana.bandeja.service.SessionService;

@Path("/tasks")
@RequestScoped
public class TasksRestService {

	@Inject BpmService bpmSrv;
		
	@Inject Logger log;
	
	@Inject
	private User user;
	
	@Inject
	private SessionService sessionSrv;
	
	@Inject
	private QueryService qsrv;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TaskWrapper> getAllTasks(@QueryParam("processInstanceId") List<Long> processInstanceId,
			@QueryParam("own") Boolean ownTasks,
    		@QueryParam("page") Integer page, 
    		@QueryParam("pageSize") Integer pageSize,
    		@QueryParam("workItemId") List<Long> workItemId,
    		@QueryParam("taskId") List<Long> taskId,
    		@QueryParam("businessAdministrator") List<String> businessAdministrator,
    		@QueryParam("potentialOwner") List<String> potentialOwner,
    		@QueryParam("status") List<Status> status,
    		@QueryParam("taskOwner") List<String> taskOwner,
    		@QueryParam("union") Boolean union,@Context HttpServletRequest request) {
		
		List<TaskWrapper> lstTasks = null;
		HashMap<String, List<String>> parameters = qsrv.createMap(workItemId, taskId, businessAdministrator, potentialOwner, status, taskOwner, processInstanceId);
		user = sessionSrv.getUser(request);

		try {
			lstTasks = bpmSrv.getInstanceTasks(ownTasks, page, pageSize, parameters, union, user);
		} catch (Exception e) {
			log.debug("Error al recuperar las instancias de procesos");
		}
		
		return lstTasks;
	}
	
	@POST
    @Path("/claim/{id:[0-9]+}")
    @Produces("application/json;charset=UTF-8")
    public String claim(@PathParam("id") Long taskId,@Context HttpServletRequest request){
    	log.debug("claiming task "+taskId);
		user = sessionSrv.getUser(request);

    	boolean result = bpmSrv.claimTask(taskId,user);
        return "{\"success\":\""+result+"\"}";
    }
    
    @POST
    @Path("/release/{id:[0-9]+}")
    @Produces("application/json;charset=UTF-8")
    public String release(@PathParam("id") Long taskId,@Context HttpServletRequest request){
    	log.debug("releasing task "+taskId);
		user = sessionSrv.getUser(request);

    	boolean result = bpmSrv.releaseTask(taskId, user);
        return "{\"success\":\""+result+"\"}";
    }
}
