package bo.gov.aduana.bandeja.bpm.kiewrapper;

/**
 * @author Jorge Morando
 *
 */
public class VariableWrapper {

	
	private String name;
	
	private String value;
	
	public VariableWrapper(String name, String value){
		this.name = name;
		this.value = value;
	}
	public VariableWrapper(){
		this.name = "";
		this.value = "";
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
