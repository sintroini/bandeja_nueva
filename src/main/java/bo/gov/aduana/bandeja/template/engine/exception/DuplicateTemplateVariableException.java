/**
 * 
 */
package bo.gov.aduana.bandeja.template.engine.exception;

/**
 * @author Jorge Morando
 *
 */
public class DuplicateTemplateVariableException extends TemplatingEngineException {

	private static final long serialVersionUID = 3662661622775670994L;

	public DuplicateTemplateVariableException(){
		super();
	}
	
	public DuplicateTemplateVariableException(String msg){
		super(msg);
	}
	
	public DuplicateTemplateVariableException(Throwable stack){
		super(stack);
	}
	
	public DuplicateTemplateVariableException(String msg,Throwable stack){
		super(msg,stack);
	}
	
}
