/**
 * 
 */
package bo.gov.aduana.bandeja.template.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * @author Jorge Morando
 *
 */
public abstract class BaseTemplate implements Template {
	
	private static Logger log = Logger.getLogger(BaseTemplate.class);
	
	/**
	 * Tipo de Template<br/>
	 * BLOCK: es un bloque HTML que no tiene funcionalidad o estilos espec&iacute;ficos, solo las clases de estilos de Bootstrap<br/>
	 * SCREEN: es una pantalla HTML que tiene funcionalidad javascript, implementaciones de estilos y extensiones de estilos de bootstrap espec&iacute;ficos de Bandeja
	 * @author Jorge Morando
	 *
	 */
	public enum Type {
		BLOCK,SCREEN;
	}
	
	private Map<String,Object> vars;
	
	private List<Template> nestedItems;

	/**
	 * @return
	 */
	public abstract String getViewPath();
	
	/**
	 * @return
	 */
	public abstract Type getType();
	
	/* (non-Javadoc)
	 * @see bo.gov.aduana.bandeja.template.engine.Template#getVars()
	 */
	public Map<String, Object> getVars() {
		if(vars==null) vars = new HashMap<>();
		return vars;
	}
	
	/* (non-Javadoc)
	 * @see bo.gov.aduana.bandeja.template.engine.Template#addVars(java.util.Map)
	 */
	public void addVars(Map<String,Object> vars){
		if(vars!=null && !vars.isEmpty()){
			for (String key : vars.keySet()) {
				addVar(key,vars.get(key));
			}
		}
	}	
	
	/* (non-Javadoc)
	 * @see bo.gov.aduana.bandeja.template.engine.Template#addVar(java.lang.String, java.lang.Object)
	 */
	public void addVar(String key, Object value){
		Object duplicate = getVars().get(key);
		if(duplicate!=null)
			log.warn("La variable '"+key+"="+value+"' en la vista '"+getViewPath()+"' entra en conflicto con una variable del mismo nombre ya seteada ("+duplicate+").");
		
		getVars().put(key, value);
	}	
	
	/* (non-Javadoc)
	 * @see bo.gov.aduana.bandeja.template.engine.Template#getNestedItems()
	 */
	public List<Template> getNestedItems() {
		if(nestedItems==null) nestedItems = new ArrayList<>();
		return nestedItems;
	}

	/* (non-Javadoc)
	 * @see bo.gov.aduana.bandeja.template.engine.Template#addNestedItems(java.util.List)
	 */
	public void addNestedItems(List<Template> items){
		for (Template screen : items) {
			addNestedItem(screen);
		}
	}
	
	/* (non-Javadoc)
	 * @see bo.gov.aduana.bandeja.template.engine.Template#addNestedItems(bo.gov.aduana.bandeja.template.engine.Template[])
	 */
	public void addNestedItems(Template...items){
		for (Template screen : items) {
			addNestedItem(screen);
		}
	}
	
	/* (non-Javadoc)
	 * @see bo.gov.aduana.bandeja.template.engine.Template#addNestedItem(bo.gov.aduana.bandeja.template.engine.Template)
	 */
	public void addNestedItem(Template item){
//		item.build();
//		processVars(item);
		getNestedItems().add(item);
	}
	
	
	/* (non-Javadoc)
	 * @see bo.gov.aduana.bandeja.template.engine.Template#processVars(bo.gov.aduana.bandeja.template.engine.Template)
	 */
	private void processVars(Template item){
		Set<String> keys = item.getVars().keySet();
		log.debug("Procesando variables: "+keys);
		for (String key : keys) {
			Object value = item.getVars().get(key);
			addVar(key, value);
		}
	}
	
	/* (non-Javadoc)
	 * @see bo.gov.aduana.bandeja.template.engine.Template#build()
	 */
	public void build() {
		processTemplateVars();
		List<Template> nestedItems = getNestedItems();
		for (Template template : nestedItems) {
			template.build();
			processVars(template);
		}
	}
	
	/**
	 * M&eacute;todo que deber&aacute;n implementar todos los objetos concretos de la jerarqu&iacute;a de templates para recopilar todas las variables de las vistas y bloques.
	 * <br/> 
	 * En dicha implementaci&oacute;n deber&aacute;n incluirse todos los objetos anidados. 
	 */
	public abstract void processTemplateVars();
}
