/**
 * 
 */
package bo.gov.aduana.bandeja.forms.resolver;

import org.apache.log4j.Logger;

import bo.gov.aduana.bandeja.bpm.kiewrapper.TaskWrapper;
import bo.gov.aduana.bandeja.forms.resolver.implementation.DefaultResolver;
import bo.gov.aduana.bandeja.util.ResolverProperties;

/**
 * Clase factor&iacute:a pensada para instanciar {@link Resolver}s en base a
 * tareas de una instancia de proceso activa.<br/>
 * Un {@link Resolver} ser&aacute; el encargardo de resolver la URL de las
 * pantallas de formularios en base una l&oacute;gica implementada
 * 
 * @author Jorge Morando
 * 
 */
public final class ResolverFactory {

	private static final Logger log = Logger.getLogger(ResolverFactory.class);
	
	private static ResolverProperties conf = new ResolverProperties();
	
	public static Resolver getInstance(String definitionId,String taskName){
		String implementationType = getResolverImplementation(definitionId,taskName);
		
		Class<?> cls = null;
		if(implementationType==null){
			log.debug("No se encontraron configuraciones de resoluci\u00F3n de URL para "+definitionId);
			log.info("Se procede a devolver la instancia Default de Resolver.");
			return new DefaultResolver();
		}
		try {
			log.debug("Tratando de encontrar la implementaci\u00f3n "+implementationType);
			cls = Class.forName(implementationType);
			log.debug("Verificando que el tipo "+implementationType+" sea de hecho una implementaci\u00f3 de Resolver");
			if(!Resolver.class.isAssignableFrom(cls)){
				throw new ClassNotFoundException(implementationType+" no es una implementaci\u00F3n de Resolver");
			}
		} catch (ClassNotFoundException e) {
			log.warn("No se ha encontrado una implementaci\u00F3n de Resolver v\u00E1lida.");
			log.debug("No se ha encontrado una implementaci\u00F3n de Resolver v\u00E1lida.",e);
			log.info("Se procede a devolver la instancia Default de Resolver.");
			return new DefaultResolver();
		}
		
		Resolver instance = null;
		try {
			instance = (Resolver) cls.newInstance();
		} catch (InstantiationException e) {
			log.warn("No se ha podido instanciar la implementaci\u00F3n de Resolver: "+implementationType);
			log.debug("No se ha podido instanciar la implementaci\u00F3n de Resolver: "+implementationType,e);
			log.info("Se procede a devolver la instancia Default de Resolver.");
			return new DefaultResolver();
		} catch (IllegalAccessException e) {
			log.warn("No se ha podido instanciar la implementaci\u00F3n de Resolver: "+implementationType);
			log.debug("No se ha podido instanciar la implementaci\u00F3n de Resolver: "+implementationType,e);
			log.info("Se procede a devolver la instancia Default de Resolver.");
			return new DefaultResolver();
		}
		
		return instance;
	}
	
	public static Resolver getInstance(TaskWrapper task) {
		return getInstance(task.getProcessId(), task.getName());	
	}
	
	private static String getResolverImplementation(String definitionId, String taskName){
		log.debug("Buscando configuraci\u00f3n para tarea \""+taskName+"\" del proceso "+definitionId);
		
		String allTasksKey= definitionId+"-*";
		String thisTaskKey = definitionId+"-"+BaseResolver.normalizeTaskName(taskName);
		
		String allTasks = conf.getProperty(allTasksKey);
		String thisTask = conf.getProperty(thisTaskKey);
		
		log.debug("Plugin para "+allTasksKey+" es "+allTasks);
		log.debug("Plugin para "+thisTaskKey+" es "+thisTask);
		
		String defPackage = "bo.gov.aduana.bandeja.forms.resolver.implementation";
		
		if(thisTask!=null){
			return defPackage+"."+thisTask;
		}else if(allTasks!=null){
			return defPackage+"."+allTasks;
		}else{
			return null;
		}
	}
	
}
