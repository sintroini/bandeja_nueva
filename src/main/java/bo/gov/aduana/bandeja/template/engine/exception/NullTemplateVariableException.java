/**
 * 
 */
package bo.gov.aduana.bandeja.template.engine.exception;

/**
 * @author Jorge Morando
 *
 */
public class NullTemplateVariableException extends TemplatingEngineException {

	private static final long serialVersionUID = 3662661622775670994L;

	public NullTemplateVariableException(){
		super();
	}
	
	public NullTemplateVariableException(String msg){
		super(msg);
	}
	
	public NullTemplateVariableException(Throwable stack){
		super(stack);
	}
	
	public NullTemplateVariableException(String msg,Throwable stack){
		super(msg,stack);
	}
	
}
