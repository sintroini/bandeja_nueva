package bo.gov.aduana.bandeja.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.kie.api.task.model.Status;

public class QueryService {

	private HashMap<String, List<String>> parameter;
	
//	public HashMap<String, List<String>> createMap(Long workItemId, Long taskId, String businessAdministrator, String potentialOwner, Status status, String taskOwner, Long instance){
//		
//		parameter = new HashMap<>();
//		
//		if(workItemId!=null)
//			parameter.put("workItemId", workItemId.toString());
//		else
//			parameter.put("workItemId", null);
//		
//		if(taskId!=null)
//			parameter.put("taskId", taskId.toString());
//		else
//			parameter.put("taskId", null);
//		
//		if(businessAdministrator!=null)
//			parameter.put("businessAdministrator", businessAdministrator);
//		else
//			parameter.put("businessAdministrator", null);
//		
//		if(potentialOwner!=null)
//			parameter.put("potentialOwner", potentialOwner);
//		else
//			parameter.put("potentialOwner", null);
//		
//		if(status!=null)
//			parameter.put("status", status.toString());
//		else
//			parameter.put("status", null);
//		
//		if(taskOwner!=null)
//			parameter.put("taskOwner", taskOwner);
//		else
//			parameter.put("taskOwner", null);
//		
//		if(instance!=null)
//			parameter.put("processInstanceId", instance.toString());
//		else
//			parameter.put("processInstanceId", null);
//		
//		return parameter;
//	}
	
	public boolean isEmpty(HashMap<String, List<String>> parameter) {
		
		boolean empty=true;
		
		for (List<String> value : parameter.values()) {
            if(value!=null){
            	if(!value.isEmpty())
            		empty=false;
            	
            }
            
		}		
		return empty;
	}

	public List<String> getProcessInstanceId(HashMap<String, List<String>> parameters) {
		List<String> instanceIds = parameters.get("processInstanceId");
		return instanceIds;
	}

	public HashMap<String, List<String>> createMap(List<Long> workItemId,
			List<Long> taskId, List<String> businessAdministrator,
			List<String> potentialOwner, List<Status> status,
			List<String> taskOwner, List<Long> processInstanceId) {
		
		parameter = new HashMap<>();
		List<String> newList;
		
		//*********************************************************
		
		if(!workItemId.isEmpty()){
			newList=new ArrayList<String>();
			for (Long newString : workItemId) { 
				  newList.add(newString.toString()); 
			}
			parameter.put("workItemId", newList);
		}	
		else
			parameter.put("workItemId", null);
		
		//*********************************************************
		
		if(!taskId.isEmpty()){
			newList=new ArrayList<String>();
			for (Long newString : taskId) { 
				  newList.add(newString.toString()); 
			}
			parameter.put("taskId", newList);
		}	
		else
			parameter.put("taskId", null);
		
		//*********************************************************
		
		if(businessAdministrator!=null)
			parameter.put("businessAdministrator", businessAdministrator);
		else
			parameter.put("businessAdministrator", null);
		
		//*********************************************************
		
		if(potentialOwner!=null)
			parameter.put("potentialOwner", potentialOwner);
		else
			parameter.put("potentialOwner", null);
		
		//*********************************************************
		
		if(!status.isEmpty()){
			newList=new ArrayList<String>();
			for (Status newString : status) { 
				  newList.add(newString.toString()); 
			}
			parameter.put("status", newList);
		}
		else
			parameter.put("status", null);
		
		//*********************************************************
		
		if(taskOwner!=null)
			parameter.put("taskOwner", taskOwner);
		else
			parameter.put("taskOwner", null);
		
		//*********************************************************
		
		if(!processInstanceId.isEmpty()){
			newList=new ArrayList<String>();
			for (Long newString : processInstanceId) { 
				  newList.add(newString.toString()); 
			}
			parameter.put("processInstanceId", newList);
		}
		else
			parameter.put("processInstanceId", null);
		
		//*********************************************************
		
		return parameter;
	}
 	
	
}
