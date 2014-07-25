package bo.gov.aduana.bandeja.template.engine;


/**
 * 
 * @author Karina Fernandez
 * 
 */
public class Notification {

	public enum Level {
		DEFAULT, INFO, WARNING, ERROR, SUCCESS
	}
	
	public Notification(String message, Level level) {
		this.message = message;
		this.level = level;
	}

	private Level level;

	private String message;

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
