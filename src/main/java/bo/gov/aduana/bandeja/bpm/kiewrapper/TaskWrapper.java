/**
 * 
 */
package bo.gov.aduana.bandeja.bpm.kiewrapper;

import java.io.Serializable;
import java.util.Date;

import org.kie.api.task.model.TaskSummary;

import bo.gov.aduana.bandeja.forms.resolver.ResolverFactory;

/**
 * @author jorge
 *
 */
public class TaskWrapper implements Serializable {

	private static final long serialVersionUID = -6580978960258791337L;

	private Long id;
	
	private String name;
	
	private String description;
	
	private Date createdDate;
	
	private String status;
	
	private String processId;
	
	private Long processInstance;
	
	private String ownerId;
	
	private String taskFormUrl;
	
	private boolean hasForm = false;
	
	public TaskWrapper(TaskSummary task){
		this.id = task.getId();
		this.name = task.getName();
		this.createdDate = task.getCreatedOn();
		this.status = task.getStatus().toString();
		this.processId = task.getProcessId();
		this.description = task.getDescription();
		this.processInstance = task.getProcessInstanceId();
		this.ownerId =  task.getActualOwner() == null?null:task.getActualOwner().getId();
		this.taskFormUrl = ResolverFactory.getInstance(task.getProcessId(),task.getName()).getUrl(this);
	}

	public TaskWrapper(
			bo.gov.aduana.bandeja.bpm.response.TaskSummary task) {
		this.id = task.getId();
		this.name = task.getName();
		this.createdDate = task.getCreatedOn();
		this.status = task.getStatus().toString();
		this.processId = task.getProcessId();
		this.description = task.getDescription();
		this.processInstance = task.getProcessInstanceId();
		this.ownerId =  task.getActualOwner() == null?null:task.getActualOwner();
		this.taskFormUrl = ResolverFactory.getInstance(task.getProcessId(),task.getName()).getUrl(this);
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param name the name to set
	 */
	public Date getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * @param name the name to set
	 */
	public void setCreatedDate(Date createdOn) {
		this.createdDate = createdOn;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the processId
	 */
	public String getProcessId() {
		return processId;
	}

	/**
	 * @param processId the processId to set
	 */
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	/**
	 * @return the processInstance
	 */
	public Long getProcessInstance() {
		return processInstance;
	}

	/**
	 * @param processInstance the processInstance to set
	 */
	public void setProcessInstance(Long processInstance) {
		this.processInstance = processInstance;
	}

	/**
	 * @return the taskFormUrl
	 */
	public String getTaskFormUrl() {
		return taskFormUrl;
	}

	/**
	 * @param taskFormUrl the taskFormUrl to set
	 */
	public void setTaskFormUrl(String taskFormUrl) {
		if(taskFormUrl!=null && !taskFormUrl.isEmpty())
			this.hasForm = true;
		this.taskFormUrl = taskFormUrl;
	}

	/**
	 * @return the hasForm
	 */
	public boolean hasForm() {
		return hasForm;
	}

	/**
	 * @return the ownerId
	 */
	public String getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

}
