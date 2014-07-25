package bo.gov.aduana.bandeja.template.block.task;

import java.util.Arrays;
import java.util.List;

import bo.gov.aduana.bandeja.bpm.kiewrapper.TaskWrapper;
import bo.gov.aduana.bandeja.template.engine.BlockTemplate;

/**
 * Backend class that manages the "TaskTable" view block.
 * @author Karina Fernandez
 *
 */
public class TaskTable extends BlockTemplate {
	
	private List<TaskWrapper> tasks;
	
	private boolean own;
	
	public TaskTable(boolean own, TaskWrapper...tasks){
		super(View.TASK_TABLE);
		this.tasks = Arrays.asList(tasks);
		this.own=own;
	}
	
	public TaskTable(boolean own, List<TaskWrapper> tasks){
		super(View.TASK_TABLE);
		this.tasks = tasks;
		this.own=own;
	}
	
	@Override
	public void processTemplateVars() {
		addVar("tasks", tasks);
		addVar("ownTasks", own);
	}
	
	public List<TaskWrapper> getTasks(){
		return tasks;
	}
	
	/**
	 * @return the own
	 */
	public boolean isOwn() {
		return own;
	}
	
	/**
	 * @return size of table
	 */
	public int size() {
		return tasks.size();
	}
}
