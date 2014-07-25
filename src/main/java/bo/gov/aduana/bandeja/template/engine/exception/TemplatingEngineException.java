/**
 * 
 */
package bo.gov.aduana.bandeja.template.engine.exception;

/**
 * @author Jorge Morando
 *
 */
public class TemplatingEngineException extends RuntimeException {

	private static final long serialVersionUID = 4127160667818754624L;

	public TemplatingEngineException(){
		super();
	}
	
	public TemplatingEngineException(String msg){
		super(msg);
	}
	
	public TemplatingEngineException(Throwable stack){
		super(stack);
	}
	
	public TemplatingEngineException(String msg,Throwable stack){
		super(msg,stack);
	}
	
}
