/**
 * 
 */
package bo.gov.aduana.bandeja.forms.resolver.implementation;

import bo.gov.aduana.bandeja.bpm.kiewrapper.ProcessInstanceWrapper;
import bo.gov.aduana.bandeja.bpm.kiewrapper.TaskWrapper;
import bo.gov.aduana.bandeja.forms.resolver.BaseResolver;

/**
 * Implementaci&oacute;n de resolver base.<br/>
 * Por defecto, si no hay ninguna configuraci&oacute;n que declare lo contrario,
 * se instanciar&aacute; el siguiente Resolver para devoler la URL de las
 * 
 * @author Jorge Morando
 * 
 */
public final class DefaultResolver extends BaseResolver {

	@Override
	public String getUrl(TaskWrapper task) {
		return "http://www.google.com";
	}

	@Override
	public String getUrl(ProcessInstanceWrapper instance) {
		return "http://www.google.com";
	}

}
