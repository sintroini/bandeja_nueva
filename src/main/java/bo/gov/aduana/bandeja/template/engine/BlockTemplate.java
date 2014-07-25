package bo.gov.aduana.bandeja.template.engine;

/**
 * 
 * @author Jorge Morando
 *
 */
public abstract class BlockTemplate extends BaseTemplate {
	
	protected enum View {
		HEADER("header.ftl"),
		MENU("menu.ftl"),
		SEARCH("search.ftl"),
		FOOTER("footer.ftl"),
		HOME_CONTENT("home/content.ftl"),
		PROCESS_INSTANCES_TABLE("process/instances_table.ftl"),
		PROCESS_INSTANCES_VARIABLES("process/instance_variables.ftl"),
		TASK_TABLE("tasks/tasks_table.ftl"), 
		TASK_MODAL("tasks/task_modal.ftl");
		
		private static final String BLOCKS_PATH_PREFIX = "blocks";
		
		private String template;
		
		private Type type;
		
		private View(String template){
			this.template = template;
			this.type= Type.BLOCK;
		}

		@Override
		public String toString() {
			return BLOCKS_PATH_PREFIX +"/"+template;
		}

		/**
		 * @return the type
		 */
		public Type getType() {
			return type;
		}
		
	}
	
	private View view;

	protected BlockTemplate(View view){
		super();
		this.view = view;
	}
	
	@Override
	public String getViewPath() {
		return getView().toString();
	}
	
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
	
}
