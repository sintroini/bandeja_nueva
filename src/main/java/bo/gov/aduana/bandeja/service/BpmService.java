package bo.gov.aduana.bandeja.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import bo.gov.aduana.bandeja.bpm.BpmHandler;
import bo.gov.aduana.bandeja.bpm.annotation.DefaultBPMHandler;
import bo.gov.aduana.bandeja.bpm.kiewrapper.ProcessInstanceWrapper;
import bo.gov.aduana.bandeja.bpm.kiewrapper.TaskWrapper;
import bo.gov.aduana.bandeja.bpm.kiewrapper.VariableWrapper;
import bo.gov.aduana.bandeja.entities.User;
import bo.gov.aduana.bandeja.exception.BusinessException;

@Named
@RequestScoped
public class BpmService {

	@Inject
    private Logger log;
	
	@Inject
	@DefaultBPMHandler
	private BpmHandler bpm;
	
	@Inject
	private QueryService qsrv;
	
	public List<ProcessInstanceWrapper> getActiveProcessInstances(String processDefinition, User user) throws BusinessException{
		log.debug("Devolviendo listado de instancias de procesos activas");
		List<ProcessInstanceWrapper> instances = bpm.getActiveInstances(processDefinition, user);
		if(instances==null)
			instances = new ArrayList<ProcessInstanceWrapper>();
		return instances;
	}
	
	public ProcessInstanceWrapper getActiveProcessInstance(Long processInstanceId, User user) throws BusinessException {
		log.debug("Devolviendo listado de instancias de procesos activas");
		ProcessInstanceWrapper instance = bpm.getProcessInstance(processInstanceId, true, user);
		return instance;
	}
	
	public List<VariableWrapper> getProcessInstanceVariables(Long processInstanceId, User user) throws BusinessException {
		log.debug("Devolviendo listado variables de instancias de procesos");
		ProcessInstanceWrapper instance = bpm.getProcessInstance(processInstanceId, true, user);
		return instance.getVariables();
	}
	
	public List<TaskWrapper> getInstanceTasks(Boolean ownTasks, Integer page, Integer pageSize,HashMap<String, List<String>> parameters, Boolean union, User user) {
		List<TaskWrapper> tasks = new ArrayList<>();
		
		
		try {
			if(ownTasks!=null && ownTasks.booleanValue())
				tasks = bpm.getInstanceTasks(parameters, ownTasks, page, pageSize, union, user);
			else if(!qsrv.isEmpty(parameters))
				tasks = bpm.getInstanceTasks(page, pageSize,parameters,union, user);
			else
				tasks = bpm.getInstanceTasks(parameters, null, page, pageSize, union, user);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tasks;
	}
	
	public Boolean claimTask(Long taskId, User user){
		try {
			bpm.claim(taskId, user);
			return true;
		} catch (Exception e) {
			log.debug("Se captur\u00f3 un error tratando de liberar la tarea "+taskId,e);
			log.error("Se captur\u00f3 un error tratando de liberar la tarea "+taskId);
			return false;
		}
	}
	
	public Boolean releaseTask(Long taskId, User user){
		try {
			bpm.release(taskId, user);
			return true;
		} catch (Exception e) {
			log.debug("Se captur\u00f3 un error tratando de reclamar la tarea "+taskId,e);
			log.error("Se captur\u00f3 un error tratando de reclamar la tarea "+taskId);
			return false;
		}
	}
	
	public Boolean completeTask(Long taskId, HashMap<String, Object> taskParams, User user){
		try {
			bpm.complete(taskId, taskParams, user);
			return true;
		} catch (Exception e) {
			log.debug("Se captur\u00f3 un error tratando de completar la tarea "+taskId,e);
			log.error("Se captur\u00f3 un error tratando de completar la tarea "+taskId);
			return false;
		}
	}
	
	public List<VariableWrapper> getProcessCompletedVariables(Long processInstanceId, User user) throws BusinessException {
		log.debug("Devolviendo listado variables de instancias de procesos completados");
		List<VariableWrapper> instance = bpm.getProcessInstanceVariable(processInstanceId, user);
		return instance;
	}

}