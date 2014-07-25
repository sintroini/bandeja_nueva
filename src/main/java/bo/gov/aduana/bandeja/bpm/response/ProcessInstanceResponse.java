/**
 * 
 */
package bo.gov.aduana.bandeja.bpm.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import bo.gov.aduana.bandeja.bpm.response.artifacts.ProcessInstanceAlternative;
import bo.gov.aduana.bandeja.bpm.response.artifacts.VariablesAlternative;

/**
 * @author Jorge Morando
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessInstanceResponse extends BaseBpmResponse {
	
	private VariablesAlternative variables;
	
	private ProcessInstanceAlternative processInstance;

	/**
	 * @return the variables
	 */
	public VariablesAlternative getVariables() {
		return variables;
	}

	/**
	 * @param variables the variables to set
	 */
	public void setVariables(VariablesAlternative variables) {
		this.variables = variables;
	}

	/**
	 * @return the processInstance
	 */
	public ProcessInstanceAlternative getProcessInstance() {
		return processInstance;
	}

	/**
	 * @param processInstance the processInstance to set
	 */
	public void setProcessInstance(ProcessInstanceAlternative processInstance) {
		this.processInstance = processInstance;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"status\":");
		sb.append("\""+getStatus()+"\"");
		sb.append(",\"url\":");
		sb.append("\""+getUrl()+"\"");
		sb.append(",\"variables\":");
		sb.append(variables);
		sb.append(",\"processInstance\":");
		sb.append(processInstance);
		sb.append("}");
		return sb.toString();
	}
	
}
