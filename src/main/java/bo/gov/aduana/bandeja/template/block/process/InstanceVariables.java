package bo.gov.aduana.bandeja.template.block.process;

import java.util.List;

import bo.gov.aduana.bandeja.bpm.kiewrapper.VariableWrapper;
import bo.gov.aduana.bandeja.template.engine.BlockTemplate;


/**
 * Backend class that manages the "InstanceModal" view block.
 * @author sintroini
 *
 */
public class InstanceVariables extends BlockTemplate {
	
	private String modalTitle;
	
	private List<VariableWrapper> processVars;
	
	public InstanceVariables(String modalTitle, List<VariableWrapper> processVars){
		super(View.PROCESS_INSTANCES_VARIABLES);
		this.modalTitle = modalTitle;
		this.processVars = processVars;
	}
	
	public InstanceVariables(String modalTitle){
		super(View.PROCESS_INSTANCES_VARIABLES);
		this.modalTitle = modalTitle;
		this.processVars = null;
	}
	
	@Override
	public void processTemplateVars() {
		addVar("modalTitle", modalTitle);
		addVar("processVars", processVars);
	}
}
