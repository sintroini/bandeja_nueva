/**
 * 
 */
package bo.gov.aduana.bandeja.forms.resolver.implementation;

import bo.gov.aduana.bandeja.bpm.kiewrapper.ProcessInstanceWrapper;
import bo.gov.aduana.bandeja.bpm.kiewrapper.TaskWrapper;
import bo.gov.aduana.bandeja.forms.resolver.BaseResolver;

/**
 * Implementaci&oacute;n de Resolver encargada de devolver la URL de formularios
 * creados por el render
 * 
 * @author Jorge Morando
 * 
 */
public final class FormRenderResolver extends BaseResolver{

	@Override
	public String getUrl(TaskWrapper task) {
		String host = "http://cuyum.homeip.net:8080";
		String contextPath="/FormRender";
		String path = "/formulario/display.xhtml";
		StringBuffer queryString = new StringBuffer("?repeat=1");
		String formId = "id="+task.getProcessId()+"."+normalizeTaskName(task.getName());
		queryString.append("&"+formId);
		String recordId = "recordId="+task.getId();
		queryString.append("&"+recordId);
		//.replace("{taskId}", String.valueOf(task.getId()))
		return host + contextPath + path + queryString.toString(); 
	}

	@Override
	public String getUrl(ProcessInstanceWrapper instance) {
		// TODO Auto-generated method stub
		return null;
	}

}
