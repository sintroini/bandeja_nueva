package bo.gov.aduana.bandeja.template.block.process;

import java.util.Arrays;
import java.util.List;

import bo.gov.aduana.bandeja.bpm.kiewrapper.ProcessInstanceWrapper;
import bo.gov.aduana.bandeja.template.engine.BlockTemplate;

/**
 * Backend class that manages the "InstanceTable" view block.
 * @author Jorge Morando
 *
 */
public class InstanceTable extends BlockTemplate {
	
	private List<ProcessInstanceWrapper> instances;
	
	public InstanceTable(ProcessInstanceWrapper...instances){
		super(View.PROCESS_INSTANCES_TABLE);
		this.instances = Arrays.asList(instances);
	}
	
	public InstanceTable(List<ProcessInstanceWrapper> instances){
		super(View.PROCESS_INSTANCES_TABLE);
		this.instances = instances;
		
	}
	
	@Override
	public void processTemplateVars() {
		addVar("instances", instances);
	}
	
	public List<ProcessInstanceWrapper> getInstances(){
		return instances;
	}
	
	public int getTotalPages(){
		int pag = instances.size();
		pag = pag/10;
		
		return pag;
	}
}
