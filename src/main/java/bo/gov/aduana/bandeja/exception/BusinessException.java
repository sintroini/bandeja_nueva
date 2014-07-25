/**
 * 
 */
package bo.gov.aduana.bandeja.exception;

/**
 * @author Jorge Morando
 *
 */
public class BusinessException extends Exception {

	private static final long serialVersionUID = 6062614493375640733L;
	
	public BusinessException () {
		super();
	}
	
	public BusinessException (String msg){
		super(msg);
	}
	
	public BusinessException (Throwable stack){
		super(stack);
	}
	
	public BusinessException (String msg, Throwable stack){
		super(msg, stack);
	}
	

}
