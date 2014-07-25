/**
 * 
 */
package bo.gov.aduana.bandeja.bpm.response;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import bo.gov.aduana.bandeja.bpm.kiewrapper.TaskWrapper;

/**
 * @author Jorge Morando
 *
 */

@XmlRootElement(name = "task-summary")
@XmlAccessorType (XmlAccessType.FIELD)
public class TaskSummary {
	
	@XmlElement(name = "id")
	private Long id;
	
	@XmlElement(name = "name")
	private String name;
	
	@XmlElement(name = "subject")
	private String subject;
	
	@XmlElement(name = "description")
	private String description;
	
	@XmlElement(name = "status")
	private String status;
	
	@XmlElement(name = "priority")
	private Long priority;
	
	@XmlElement(name = "skipable")
	private String skipable;
	
	@XmlElement(name = "actual-owner")
	private String actualOwner;
	
	@XmlElement(name = "created-on")
	private Date createdOn;
	
	@XmlElement(name = "activation-time")
	private Date activationTime;
	
	@XmlElement(name = "process-instance-id")
	private Long processInstanceId;
	
	@XmlElement(name = "process-id")
	private String processId;
	
	@XmlElement(name = "process-session-id")
	private Long processSessionId;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getPriority() {
		return priority;
	}

	public void setPriority(Long priority) {
		this.priority = priority;
	}

	public String getSkipable() {
		return skipable;
	}

	public void setSkipable(String skipable) {
		this.skipable = skipable;
	}

	public String getActualOwner() {
		return actualOwner;
	}

	public void setActualOwner(String actualOwner) {
		this.actualOwner = actualOwner;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getActivationTime() {
		return activationTime;
	}

	public void setActivationTime(Date activationTime) {
		this.activationTime = activationTime;
	}

	public Long getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public Long getProcessSessionId() {
		return processSessionId;
	}

	public void setProcessSessionId(Long processSessionId) {
		this.processSessionId = processSessionId;
	}
	
}
