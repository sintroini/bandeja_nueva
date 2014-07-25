/**
 * 
 */
package bo.gov.aduana.bandeja.template.engine;

import java.util.List;
import java.util.Map;

/**
 * @author Jorge Morando
 *
 */
public interface Template {
		
	/**
	 * M&eacute;todo que devuelve las variables de una template.
	 * @return
	 */
	Map<String, Object> getVars();
	
	/**
	 * M&eacute;todo que implementa la l&oacute;gica de inclusi&oacute;n de varias variables de template incluidas en un .ftl  
	 * @param vars
	 */
	void addVars(Map<String,Object> vars);
	
	/**
	 * M&eacute;todo que implementa la l&oacute;gica de inclusi&oacute;n de una variable de template incluida en un .ftl
	 * @param key
	 * @param value
	 */
	void addVar(String key, Object value);
	
//	/**
//	 * M&eacute;todo que implementa la l&oacute;gica de procesamiento de variables para una template definida, sea bloque o pantalla
//	 * @param item
//	 */
//	void processVars(Template item);
	
	/**
	 * @param items
	 */
	void addNestedItems(List<Template> items);

	/**
	 * M&eacute;todo que devuelve templates anidadas en una template concreta
	 * @return
	 */
	List<Template> getNestedItems();

	
	/**
	 * M&eacute;todo que implementa la l&oacute;gica de inclusi&oacute;n de templates anidadas en varias templates.
	 * @param items
	 */
	void addNestedItems(Template...items);
	
	/**
	 * M&eacute;todo que implementa la l&oacute;gica de inclusi&oacute;n de templates anidadas en una template espec&iacute;fica.
	 * @param item
	 */
	void addNestedItem(Template item);
	
	/**
	 * M&eacute;todo que implementa la l\u00f3gica de construcci\u00f3n del objeto concreto.
	 */
	public void build();
	
}
