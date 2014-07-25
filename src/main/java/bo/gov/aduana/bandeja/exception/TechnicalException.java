/**
 * 
 */
package bo.gov.aduana.bandeja.exception;

/**
 * @author Jorge Morando
 *
 */
public class TechnicalException extends RuntimeException {

	private static final long serialVersionUID = 6062614493375640733L;
	
	public TechnicalException () {
		super();
	}
	
	public TechnicalException (String msg){
		super(msg);
	}
	
	public TechnicalException (Throwable stack){
		super(stack);
	}
	
	public TechnicalException (String msg, Throwable stack){
		super(msg, stack);
	}
	

}
