/**
 * 
 */
package bo.gov.aduana.bandeja.forms.resolver;

import bo.gov.aduana.bandeja.bpm.kiewrapper.ProcessInstanceWrapper;
import bo.gov.aduana.bandeja.bpm.kiewrapper.TaskWrapper;

/**
 * @author jorge
 *
 */
public abstract class BaseResolver implements Resolver {

	/* (non-Javadoc)
	 * @see bo.gov.aduana.bandeja.forms.resolver.Resolver#getUrl(bo.gov.aduana.bandeja.bpm.kiewrapper.TaskWrapper)
	 */
	public abstract String getUrl(TaskWrapper task);

	/* (non-Javadoc)
	 * @see bo.gov.aduana.bandeja.forms.resolver.Resolver#getUrl(bo.gov.aduana.bandeja.bpm.kiewrapper.ProcessInstanceWrapper)
	 */
	public abstract String getUrl(ProcessInstanceWrapper instance);
	
	public static String normalizeTaskName(String origTaskName){
		String normalizedTaskName = origTaskName.toLowerCase().replaceAll(" ", "");
		return normalizedTaskName;
	}

}
