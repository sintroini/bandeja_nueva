/**
 * 
 */
package bo.gov.aduana.bandeja.bpm.response;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import bo.gov.aduana.bandeja.bpm.response.artifacts.ProcessInstanceAlternative;
import bo.gov.aduana.bandeja.bpm.response.artifacts.VariablesAlternative;

/**
 * @author Karina Fernandez
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessCompletedResponse{
	
	private List<ResultHistory> result;
	

	/**
	 * @return the result
	 */
	public List<ResultHistory> getResult() {
		return result;
	}
	/**
	 * @param result the result to set
	 */
	public void setResult(List<ResultHistory> result) {
		this.result = result;
	}
}
