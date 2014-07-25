/**
 * 
 */
package bo.gov.aduana.bandeja.bpm.response;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * @author Karina Fernandez
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultHistory extends BaseBpmResponse {
	
	private long date;
	private long processInstanceId;
	private String variableId;
	private String value;
	
	public long getDate() {
		return date;
	}
	public void setDate(long date) {
		this.date = date;
	}
	public long getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getVariableId() {
		return variableId;
	}
	public void setVariableId(String variableId) {
		this.variableId = variableId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
