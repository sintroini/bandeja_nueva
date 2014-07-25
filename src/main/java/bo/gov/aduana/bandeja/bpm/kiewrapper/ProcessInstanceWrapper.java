package bo.gov.aduana.bandeja.bpm.kiewrapper;

import java.io.Serializable;
import java.util.List;

import org.jbpm.process.audit.ProcessInstanceLog;
import org.kie.api.runtime.process.ProcessInstance;

import bo.gov.aduana.bandeja.bpm.response.artifacts.ProcessInstanceAlternative;

public class ProcessInstanceWrapper implements Serializable {

	private static final long serialVersionUID = 1309113719339688793L;
	
	private Long id;
	
	private String description;
	
	private int state;
	
	private List<VariableWrapper> variables;
	
	public ProcessInstanceWrapper(ProcessInstance instance){
		this.id = instance.getId();
		this.description = instance.getProcessName();
		this.state = instance.getState();
	}
	
	public ProcessInstanceWrapper(ProcessInstanceLog instance){
		this.id = instance.getId();
		this.description = instance.getProcessName();
		this.state = instance.getStatus();
	}
	
	public ProcessInstanceWrapper(ProcessInstanceAlternative instance){
		this.id = instance.getId();
		this.description = instance.getProcessId();
		this.state = instance.getState();
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
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}
	
	/**
	 * @return the variables
	 */
	public List<VariableWrapper> getVariables() {
		return variables;
	}

	/**
	 * @param variables the variables to set
	 */
	public void setVariables(List<VariableWrapper> variables) {
		this.variables = variables;
	}
	
	public String getStateString(){
		String state = null; 
		switch (getState()) {
		case 1:
			state = "Active";
			break;
		case 2:
			state = "Completed";
			break;
		case 3:
			state = "Aborted";
			break;
		case 4:
			state = "Suspended";
			break;
		default:
			state = "Undefined";
		}
		return state;
	}
}
