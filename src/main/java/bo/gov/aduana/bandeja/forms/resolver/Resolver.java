/**
 * 
 */
package bo.gov.aduana.bandeja.forms.resolver;

import bo.gov.aduana.bandeja.bpm.kiewrapper.ProcessInstanceWrapper;
import bo.gov.aduana.bandeja.bpm.kiewrapper.TaskWrapper;

/**
 * Interfaz de comunicaci&oacute;n para las diferentes implementaciones que
 * describan l&oacute;gicas diferentes de resoluci&oacute;n de URLs para los
 * formularios de las tareas.
 * 
 * @author Jorge Morando
 * 
 */
public interface Resolver {

	public String getUrl(TaskWrapper task);
	
	public String getUrl(ProcessInstanceWrapper instance);

}
