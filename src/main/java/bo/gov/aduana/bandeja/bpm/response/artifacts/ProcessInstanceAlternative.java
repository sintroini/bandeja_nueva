/**
 * 
 */
package bo.gov.aduana.bandeja.bpm.response.artifacts;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Jorge Morando
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessInstanceAlternative {
	
	private Long id;

	private String processId;
    
    private int state;

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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"id\":");
		sb.append(id);
		sb.append(",\"processId\":");
		sb.append("\""+processId+"\"");
		sb.append(",\"state\":");
		sb.append(state);
		sb.append("}");
		return sb.toString();
	}
	
}
