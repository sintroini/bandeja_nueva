package bo.gov.aduana.bandeja.bpm;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.jboss.resteasy.client.ClientResponse;
import org.jbpm.process.audit.AuditLogService;
import org.jbpm.process.audit.ProcessInstanceLog;
import org.kie.api.runtime.KieSession;
import org.kie.api.task.TaskService;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;
//import org.kie.services.client.api.RemoteRestRuntimeEngineFactory;
import org.kie.services.client.api.RemoteRestRuntimeFactory;
import org.kie.services.client.api.command.RemoteRuntimeEngine;

import bo.gov.aduana.bandeja.bpm.annotation.DefaultBPMHandler;
import bo.gov.aduana.bandeja.bpm.kiewrapper.ProcessInstanceWrapper;
import bo.gov.aduana.bandeja.bpm.kiewrapper.TaskWrapper;
import bo.gov.aduana.bandeja.bpm.kiewrapper.VariableWrapper;
import bo.gov.aduana.bandeja.bpm.response.ProcessCompletedResponse;
import bo.gov.aduana.bandeja.bpm.response.ProcessInstanceResponse;
import bo.gov.aduana.bandeja.bpm.response.ResultHistory;
import bo.gov.aduana.bandeja.bpm.response.TaskSummaryList;
import bo.gov.aduana.bandeja.entities.User;
import bo.gov.aduana.bandeja.exception.BusinessException;
import bo.gov.aduana.bandeja.exception.TechnicalException;
import bo.gov.aduana.bandeja.service.QueryService;
import bo.gov.aduana.bandeja.util.BandejaProperties;

@DefaultBPMHandler
@Named("bpmHandler")
public class BpmHandler {

	private BandejaProperties conf = new BandejaProperties();

	@Inject
	private Logger log;

	@Inject
	private QueryService qsrv;

	/* POC */

	// URL de servidor bpm6 de cuyum
	// private String deploymentUrlStr =
	// "http://162.243.12.101:8080/business-central/";
	// private String deploymentId = "org.kie.procesojorge:aduanabojorge:1.0";
	// private String definitionId = "bo.gob.aduana.registro-operadores-jorge";

	private String deploymentUrlStr;
	private String deploymentId;
	private String definitionId;

	private String userId; // usuario con roles: "tecAnalista",
										// "tecPlataforma" y "Solicitante" ;
	private String password;

	private RemoteRuntimeEngine engine;
	private KieSession session;
	private TaskService taskService;
	private AuditLogService auditService;

	private boolean connected = false;

	private KieAlternative kieAlt;

	public BpmHandler() {
		String host = conf.getProcessDeploymentHost();
		String context = conf.getProcessDeploymentContext();
		if (host == null || host.isEmpty()) {
			log.error("No se puede Levantar engine Remoto (KIE) de BPM porque el host no est\u00E1 correctamente configurado");
		} else if (context == null || context.isEmpty()) {
			log.error("No se puede Levantar engine Remoto (KIE) de BPM porque el contexto no est\u00E1 correctamente configurado");
		} else {
			deploymentUrlStr = host + context;
			deploymentId = conf.getProcessDeploymentId();
			definitionId = conf.getProcessDefinitionIds()[0];
		}
	}

	private void connectAlternative(User user) throws BusinessException {
		connect(true, user);
	}

	private void connectdefault(User user) throws BusinessException {
		connect(false, user);
	}

	private void connect(boolean alternative, User user) throws BusinessException {
		
		userId = user.getUsername();
		password = user.getPassword();

		if (deploymentUrlStr != null && deploymentId != null && userId != null
				&& password != null) {
			URL deploymentUrl;
			try {
				if (alternative) {
					deploymentUrl = new URL(deploymentUrlStr + "/rest");
					kieAlt = new KieAlternative(deploymentUrl, userId, password);
				} else {
					deploymentUrl = new URL(deploymentUrlStr);
					// Creamos la sesion REST
					RemoteRestRuntimeFactory restFactory = new RemoteRestRuntimeFactory(
							deploymentId, deploymentUrl, userId, password, 30);
					engine = restFactory.newRuntimeEngine();

					/*
					 * engine = RemoteRestRuntimeEngineFactory.newBuilder()
					 * .addDeploymentId(deploymentId) .addUrl(deploymentUrl)
					 * .addUserName(userId) .addPassword(password)
					 * .addTimeout(30) .build() .newRuntimeEngine();
					 */

					// current session
					session = engine.getKieSession();
					// remote session for process
					auditService = engine.getAuditLogService();
					// tasks
					taskService = engine.getTaskService();
				}
				connected = true;
			} catch (MalformedURLException e) {
				String msg = "Error de formaci\u00F3n de URL";
				throw new TechnicalException(msg, e);
			}
		} else {
			throw new BusinessException(
					"No se han encontrado credenciales para autenticarse contra BPM");
		}
	}

	private void disconnect() {
		connected = false;
		session = null;
		auditService = null;
		taskService = null;
		kieAlt = null;
	}

	public List<ProcessInstanceWrapper> getActiveInstances(
			String processDefinition, User user) throws BusinessException {
		if (!connected)
			connectdefault(user);
		List<ProcessInstanceLog> remoteInstances = new ArrayList<ProcessInstanceLog>();
		// FIXME:KIE (versión 6.0.2-redhat-6) tiene un bug en el que cuando no
		// hay instancias de un proceso dispara un NullPointerException
		try {
			if (processDefinition != null && !processDefinition.isEmpty()) {
				remoteInstances = auditService
						.findProcessInstances(processDefinition);
			} else {
				remoteInstances = auditService
						.findProcessInstances(definitionId);
			}

		} catch (NullPointerException e) {
			log.warn("Se ha capturado NullPointerException de un bug de KIE al pedir instancias de un proceso y no encontrar ninguna.");
			log.trace("Error de bug KIE capturado", e);
		}
		disconnect();
		return wrapRemoteInstances(remoteInstances);
	}

	/*
	 * public List<ProcessInstanceWrapper> obtener_instancias_activas(){
	 * if(session==null) connect();
	 * 
	 * List<ProcessInstance> instances = new ArrayList<ProcessInstance>();
	 * instances.addAll(session.getProcessInstances());
	 * 
	 * if(instances.isEmpty()){ log.info("No active instances"); return
	 * wrapInstances(instances); } for (ProcessInstance processInstance :
	 * instances) { log.info("================================");
	 * log.info("Process: "+processInstance.getProcessId());
	 * log.info("Instance: "+processInstance.getId());
	 * log.info("State:"+processInstance.getState());
	 * log.info("================================"); } return
	 * wrapInstances(instances); }
	 * 
	 * public ProcessInstance crear_instancia() { if(session==null) connect();
	 * //Creamos una bolsa de parametros a enviar para iniciar la instancia
	 * //estos parametros son necesarios para que el proceso corra Map<String,
	 * Object> params = new HashMap<String, Object>(); params.put("agenciaDesp",
	 * "false"); params.put("idSolicitud", "tres-3"); params.put("url",
	 * "http://localhost:8080/dummy/rest/service?idSolicitud=#{idSolicitud}");
	 * params.put("urlNotificacion",
	 * "http://localhost:8080/dummy/rest/service/date?idSolicitud=#{idSolicitud}"
	 * ); params.put("urlNotificacionProgramada",
	 * "http://localhost:8080/dummy/rest/service/date/random?idSolicitud=#{idSolicitud}"
	 * ); params.put("entidadPublica", "false");
	 * 
	 * //iniciamos una instancia del proceso ProcessInstance processInstance =
	 * session.startProcess(definitionId, params);
	 * log.info("Started process instance: " + processInstance + " " +
	 * (processInstance == null ? "" : processInstance.getId()));
	 * 
	 * return processInstance;
	 * 
	 * }
	 */

	public ProcessInstanceWrapper getProcessInstance(Long processInstance, User user)
			throws BusinessException {
		return getProcessInstance(processInstance, false, user);
	}

	public ProcessInstanceWrapper getProcessInstance(Long processInstance,
			boolean withVariables, User user) throws BusinessException {
		if (!connected)
			connectAlternative(user);

		Map<String, String> params = new HashMap<>(2);
		params.put("deploymentId", deploymentId);
		params.put("processInstanceId", "" + processInstance);
		ClientResponse<?> response;
		ProcessInstanceResponse pir = null;
		try {
			if (withVariables) {
				response = kieAlt.execute(
						Command.PROCESS_INSTANCE_WITH_VARIABLES, params, null);
			} else {
				response = kieAlt.execute(Command.PROCESS_INSTANCE, params,
						null);
			}
			pir = (ProcessInstanceResponse) response.getEntity();
			disconnect();
			return wrapProcessInstance(pir);
		} catch (Exception e) {
			throw new TechnicalException(
					"Se captur\u00F3 un error tratando de recuperar la instancia de proceso "
							+ processInstance + " con variables.", e);
		}
	}

	@SuppressWarnings("unchecked")
	public List<TaskWrapper> getInstanceTasks(Integer page, Integer pageSize,
			HashMap<String, List<String>> parameters, Boolean union, User user)
			throws BusinessException {

		if (!connected)
			connectAlternative(user);

		TaskSummaryList task = new TaskSummaryList();
		ClientResponse<?> response;

		try {
			response = kieAlt.execute(Command.TASK, null, parameters, union);

			task = (TaskSummaryList) response.getEntity();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return wrapTaskSummaryList(task.getTaskSummary());
	}

	private List<TaskWrapper> wrapTaskSummaryList(
			@SuppressWarnings("rawtypes") List taskSummaryList) {
		List<TaskWrapper> tasks = new ArrayList<TaskWrapper>();
		@SuppressWarnings("unchecked")
		List<bo.gov.aduana.bandeja.bpm.response.TaskSummary> taskSumm = taskSummaryList;

		if (taskSumm != null)
			for (bo.gov.aduana.bandeja.bpm.response.TaskSummary taskSummary : taskSumm) {
				tasks.add(wrapTaskSummary(taskSummary));
			}

		return tasks;
	}

	private TaskWrapper wrapTaskSummary(
			bo.gov.aduana.bandeja.bpm.response.TaskSummary taskSummary) {

		TaskWrapper task = new TaskWrapper(taskSummary);
		return task;
	}

	public List<TaskWrapper> getInstanceTasks(
			HashMap<String, List<String>> parameters, Boolean ownTasks,
			Integer page, Integer pageSize, Boolean union, User user)
			throws BusinessException {
		if (!connected)
			connectdefault(user);

		List<TaskSummary> tasks = new ArrayList<TaskSummary>();
		List<TaskWrapper> newTasks = new ArrayList<TaskWrapper>();

		if (ownTasks != null && ownTasks) {
			tasks = taskService.getTasksOwned(user.getUsername(), "en-UK");
			newTasks = wrapTaskSummary(tasks);
		} else {
			// buscamos las tareas asignadas al grupo del usuario referenciado
			// por "userId"
			tasks = taskService.getTasksAssignedAsPotentialOwner(user.getUsername(),
					"en-UK");
			newTasks = wrapTaskSummary(tasks);
		}

		// buscamos las tareas paginadas (no funciona la libreria) descomentar
		// si se encuentra solución
		// List<TaskSummary> tasks =
		// taskService.getTasksAssignedAsPotentialOwner(userId, groupIds,
		// "en-UK", page, pageSize);

		// filtramos todas las tareas de la instancia recien creada
		List<TaskWrapper> thisProcTasks;
		if (!qsrv.isEmpty(parameters)) {
			thisProcTasks = findTaskId(qsrv.getProcessInstanceId(parameters),
					ownTasks, newTasks);
		} else
			thisProcTasks = newTasks;

		disconnect();
		return thisProcTasks;
	}

	// private List<TaskWrapper> findTaskOwn(String userId,
	// List<TaskWrapper> newTasks) {
	//
	// List<TaskWrapper> taskOwn = new ArrayList<TaskWrapper>();
	//
	// for (TaskWrapper task : newTasks) {
	// log.info("Task " + task.getName() + " (" + task.getOwnerId()
	// + ") for process instance " + userId);
	// String uId = task.getOwnerId();
	//
	// if(uId!=null){
	// if (uId.equals(userId)) {
	// taskOwn.add(task);
	// }
	// }
	// }
	// return taskOwn;
	// }

	public void claim(Long taskId, User user) throws BusinessException {
		if (!connected)
			connectdefault(user);
		// se trata de obtener la tarea
		Task task = taskService.getTaskById(taskId);

		// establecer que la tarea esta en progreso
		// taskService.start(task.getId(), userId);

		// creo una bolsa de variables que permitiran el completado correcto de
		// la tarea
		// Map<String, Object> taskParams = new HashMap<String, Object>();

		// reclamo la tarea
		taskService.claim(task.getId(), user.getUsername());

		// completo la tarea
		// taskService.complete(task.getId(), userId, taskParams);
		disconnect();

	}

	public void release(Long taskId, User user) throws BusinessException {
		if (!connected)
			connectdefault(user);
		// se trata de obtener la tarea
		Task task = taskService.getTaskById(taskId);

		// establecer que la tarea esta en progreso
		// taskService.start(task.getId(), userId);

		// creo una bolsa de variables que permitiran el completado correcto de
		// la tarea
		// Map<String, Object> taskParams = new HashMap<String, Object>();

		// reclamo la tarea
		taskService.release(task.getId(), user.getUsername());

		// completo la tarea
		// taskService.complete(task.getId(), userId, taskParams);
		disconnect();

	}
	
	public void complete(Long taskId, HashMap<String, Object> taskParams, User user)throws Exception{
		
		if(!connected) 
    		connectdefault(user);
		//taskService.claim(taskId, userId);
		System.out.println("taskService - srart tarea: " + taskId );
		taskService.start(taskId, userId);
		System.out.println("taskService - complete tarea: " + taskId );
		taskService.complete(taskId, userId, taskParams);
		
	}

	/*
	 * 
	 * 
	 * public void verificarSolicitudPorAnalista(Long taskId){ //se trata de
	 * obtener la tarea Task task = taskService.getTaskById(taskId);
	 * log.info("Ubico la task " + task.getId());
	 * verificarSolicitudPorAnalista(task); }
	 * 
	 * public void verificarSolicitudPorAnalista(TaskSummary taskSummary){ //se
	 * trata de obtener la tarea Task task =
	 * taskService.getTaskById(taskSummary.getId()); log.info("Ubico la task " +
	 * task.getId() + ": " + taskSummary.getName()
	 * +" ("+taskSummary.getStatus()+")"); verificarSolicitudPorAnalista(task);
	 * }
	 * 
	 * private void verificarSolicitudPorAnalista(Task task){
	 * 
	 * //establecer que la tarea esta en progreso
	 * taskService.start(task.getId(), userId);
	 * 
	 * //creo una bolsa de variables que permitiran el completado correcto de la
	 * tarea Map<String, Object> taskParams = new HashMap<String, Object>();
	 * taskParams.put("observacionOut", "false");
	 * 
	 * //reclamo la tarea // taskService.claim(task.getId(), userId);
	 * 
	 * //completo la tarea taskService.complete(task.getId(), userId,
	 * taskParams);
	 * 
	 * }
	 * 
	 * public void destrabarEsperarAlSolicitante(TaskSummary taskSummary){ //se
	 * trata de obtener la tarea Task task =
	 * taskService.getTaskById(taskSummary.getId()); log.info("Ubico la task " +
	 * task.getId() + ": " + taskSummary.getName()
	 * +" ("+taskSummary.getStatus()+")");
	 * 
	 * //establecer que la tarea esta en progreso
	 * taskService.start(task.getId(), userId);
	 * 
	 * //creo una bolsa de variables que permitiran el completado correcto de la
	 * tarea Map<String, Object> taskParams = new HashMap<String, Object>();
	 * taskParams.put("sePresentoOut", "si");
	 * 
	 * //reclamo la tarea // taskService.claim(task.getId(), userId);
	 * 
	 * //completo la tarea taskService.complete(task.getId(), userId,
	 * taskParams);
	 * 
	 * }
	 * 
	 * public void verificarSolicitudPorPlataforma(TaskSummary taskSummary){
	 * //se trata de obtener la tarea Task task =
	 * taskService.getTaskById(taskSummary.getId()); log.info("Ubico la task " +
	 * task.getId() + ": " + taskSummary.getName()
	 * +" ("+taskSummary.getStatus()+")");
	 * 
	 * //establecer que la tarea esta en progreso
	 * taskService.start(task.getId(), userId);
	 * 
	 * //creo una bolsa de variables que permitiran el completado correcto de la
	 * tarea Map<String, Object> taskParams = new HashMap<String, Object>();
	 * taskParams.put("observacionOut", "false"); taskParams.put("editaFormOut",
	 * "false"); //el proceso terminará por "Completar Registro", no es
	 * necesario
	 * 
	 * //reclamo la tarea // taskService.claim(task.getId(), userId);
	 * 
	 * //completo la tarea taskService.complete(task.getId(), userId,
	 * taskParams);
	 * 
	 * }
	 * 
	 * public void completarRegistro(TaskSummary taskSummary){ //se trata de
	 * obtener la tarea Task task =
	 * taskService.getTaskById(taskSummary.getId()); log.info("Ubico la task " +
	 * task.getId() + ": " + taskSummary.getName()
	 * +" ("+taskSummary.getStatus()+")");
	 * 
	 * //establecer que la tarea esta en progreso
	 * taskService.start(task.getId(), userId);
	 * 
	 * //creo una bolsa de variables que permitiran el completado correcto de la
	 * tarea Map<String, Object> taskParams = new HashMap<String, Object>();
	 * 
	 * //reclamo la tarea // taskService.claim(task.getId(), userId);
	 * 
	 * //completo la tarea taskService.complete(task.getId(), userId,
	 * taskParams);
	 * 
	 * }
	 */
	/* HELPER METHOD */
	private List<TaskWrapper> findTaskId(List<String> processInstancesIds,
			boolean ownTasks, List<TaskWrapper> taskSumList) {
		List<TaskWrapper> taskList = new ArrayList<TaskWrapper>();
		if (processInstancesIds != null && taskSumList != null) {
			for (TaskWrapper task : taskSumList) {

				for (String instanceId : processInstancesIds) {
					log.info("Task " + task.getName() + " (" + task.getId()
							+ ") for process instance " + instanceId);
					Long id = task.getProcessInstance();
					if (id.toString().equals(instanceId)) {
						taskList.add(task);
					}
				}
			}
		} else {
			taskList = taskSumList;
		}
		return taskList;
	}

	private List<ProcessInstanceWrapper> wrapRemoteInstances(
			List<ProcessInstanceLog> instances) {
		List<ProcessInstanceWrapper> wrappedInstances = new ArrayList<>();
		if (instances != null) {
			for (ProcessInstanceLog processInstance : instances) {
				wrappedInstances
						.add(new ProcessInstanceWrapper(processInstance));
			}
		}
		return wrappedInstances;
	}

	private ProcessInstanceWrapper wrapProcessInstance(
			ProcessInstanceResponse response) {
		ProcessInstanceWrapper wrapper = new ProcessInstanceWrapper(
				response.getProcessInstance());
		wrapper.setVariables(response.getVariables().toList());
		return wrapper;
	}

	private List<TaskWrapper> wrapTaskSummary(List<TaskSummary> tasks) {
		List<TaskWrapper> wrappedTasks = new ArrayList<>();
		for (TaskSummary taskSummary : tasks) {
			wrappedTasks.add(wrapTaskSummary(taskSummary));
		}
		return wrappedTasks;
	}

	private TaskWrapper wrapTaskSummary(TaskSummary taskSummary) {
		TaskWrapper task = new TaskWrapper(taskSummary);
		return task;
	}

//	public String completarTarea(Long taskId) {
//		Task task = taskService.getTaskById(taskId);
//		log.info("HOLA!! " + task.getNames());
//		return "index";
//	}

	public List<VariableWrapper> getProcessInstanceVariable(Long processInstance, User user)
			throws BusinessException {

		if (!connected)
			connectAlternative(user);

		Map<String, String> params = new HashMap<>(2);
		params.put("deploymentId", deploymentId);
		params.put("processInstanceId", "" + processInstance);
		ProcessCompletedResponse result = new ProcessCompletedResponse();
		ClientResponse<?> response;

		try {
			response = kieAlt.execute(Command.PROCESS_INSTANCE_HISTORY, params,
					null);
			result = (ProcessCompletedResponse) response.getEntity();
			disconnect();
			return wrapProcessInstanceHistory(result);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new TechnicalException(
					"Se captur\u00F3 un error tratando de recuperar la instancia de proceso "
							+ processInstance + " con variables.", e);
		}

		// return null;
	}

	private List<VariableWrapper> wrapProcessInstanceHistory(
			ProcessCompletedResponse response) {
		// lista para ser retornada
		List<VariableWrapper> wrapper = new ArrayList<VariableWrapper>();
		// crea una lista de string para guardar id de variables (por ejemplo
		// varDos, varTres)
		List<String> name = new ArrayList<String>();
		List<ResultHistory> rh = response.getResult();
		for (ResultHistory result : rh) {
			name.add(result.getVariableId());
			log.debug(result.getVariableId());
		}
		HashSet hs = new HashSet();
		// cargar el HasSet con los valores del array name, esto hace que quite
		// los elementos repetidos
		hs.addAll(name);
		// Limpiamos el array name
		name.clear();
		// Agrega los variableId sin repetir
		name.addAll(hs);

		// recorre la lista de variables y la compara con la lista response que
		// recibi por parametro
		for (String n : name) {
			ResultHistory aux = null;
			for (ResultHistory result : rh) {
				if (n.equals(result.getVariableId()))
					aux = result;
			}
			VariableWrapper vr = new VariableWrapper(aux.getVariableId(),
					aux.getValue());
			wrapper.add(vr);
		}
		return wrapper;
	}
}