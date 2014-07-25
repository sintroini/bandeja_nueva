package bo.gov.aduana.bandeja.template.engine;

import java.util.ArrayList;
import java.util.List;



/**
 * 
 * @author Jorge Morando
 *
 */
public abstract class ScreenTemplate extends BaseTemplate {
	
	protected enum View {
		ERROR("error.ftl"),
		HOME("home.ftl"), 
		PROCESS_INSTANCES("process_instances.ftl"),
		TASKS("tasks.ftl"),
		SESSION("session.ftl");
		
		private static final String SCREENS_PATH_PREFIX = "screens";
		
		private String template;
		
		private Type type;
		
		private View(String template){
			this.template = template;
			this.type = Type.SCREEN;
		}

		@Override
		public String toString() {
			return SCREENS_PATH_PREFIX +"/"+template;
		}

		/**
		 * @return the type
		 */
		public Type getType() {
			return type;
		}
	}
	
	private final String NOTIFICATIONS_KEY = "screen_notifications_bag";
		
	private View view;
	
	protected ScreenTemplate(View view){
		this.view = view;
		addVar(NOTIFICATIONS_KEY, new ArrayList<Notification>());
	}
	
	/* (non-Javadoc)
	 * @see bo.gov.aduana.bandeja.template.engine.BaseTemplate#getViewPath()
	 */
	@Override
	public String getViewPath() {
		return getView().toString();
	}
	
	/* (non-Javadoc)
	 * @see bo.gov.aduana.bandeja.template.engine.BaseTemplate#getType()
	 */
	@Override
	public Type getType(){
		return getView().getType();
	}
	
	/**
	 * @return the template
	 */
	public View getView() {
		return view;
	}
	
	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Notification> getNotifications() {
		List<Notification> notifications = (List<Notification>) getVars().get(NOTIFICATIONS_KEY);
		return notifications;
	}
	
	public void setNotifications(List<Notification> notifications){
		getVars().put(NOTIFICATIONS_KEY, notifications);
	}
	
	public void addNotification(Notification not){
		List<Notification> notifications = getNotifications();
		notifications.add(not);
//		setNotifications(notifications);
	}
	
	/**
	 * @param notifications
	 */
	public void addNotifications(List<Notification> notifications){
		List<Notification> origNotifications = getNotifications();
		origNotifications.addAll(notifications);
		setNotifications(origNotifications);
	}

}
