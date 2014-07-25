package bo.gov.aduana.bandeja.response;

import bo.gov.aduana.bandeja.bpm.kiewrapper.ProcessInstanceWrapper;

public class ProcessResponse {
	
	private Long id;
	private String description;
	private String status;
	
	public ProcessResponse(ProcessInstanceWrapper piw) {
		super();
		this.id = piw.getId();
		this.description = piw.getDescription();
		this.status = piw.getStateString();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String estado) {
		this.status = estado;
	}
	
}
