package bo.gov.aduana.bandeja.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.log4j.Logger;


@Singleton
@Named("bandejaProperties")
public class BandejaProperties extends Properties {
	
	private static final long serialVersionUID = 8397332937906785565L;

	public static final String DEFAULT_FILE_NAME = "bandeja_procesos.properties";
	
	private static final String SERVER_CONF_URL = System.getProperty("jboss.server.config.dir");
	
	private static Logger log = Logger.getLogger(BandejaProperties.class);
	
	private InputStream elInput=null;

	public BandejaProperties() {
		log.info("Cargando configuraci\u00F3n de Bandeja de Procesos.");
		
		fetchSource();
		load();
	}
	
	private void fetchSource(){
		try {
			File externalFile = getExternalConfig();
			
			if (externalFile==null) {
				log.warn("No se encontr\u00F3 archivo de configuraci\u00F3n "+DEFAULT_FILE_NAME+ " en "+SERVER_CONF_URL+". Se usar\u00E1 el que se incluye en el paquete");
				log.debug("Usando archivo de configuraci\u00F3n interno incluido en el paquete");
				elInput  = this.getClass().getClassLoader().getResourceAsStream(DEFAULT_FILE_NAME);
			} else {
				log.debug("Usando archivo de configuraci\u00F3n externo "+externalFile.getAbsolutePath());
				elInput = new FileInputStream(externalFile);
			}
			
		} catch (Exception e) {
			log.error("No puedo cargar el arhivo properties "+ DEFAULT_FILE_NAME);
			log.debug("No puedo cargar el arhivo properties "+ DEFAULT_FILE_NAME,e);
		}
	}
	
	private void load(){
		if (this.isEmpty()) {
			try {
				this.load(this.elInput);
			} catch (IOException e) {
				throw new RuntimeException("No se pudo cargar el archivo de configuraci\u00f3n de Bandeja de Procesos.",e);
			}
		}
	}

	private File getExternalConfig() {
		if (SERVER_CONF_URL == null)
			return null;
		
		log.debug("Tratando de levantar archivo externo "+SERVER_CONF_URL+"/"+DEFAULT_FILE_NAME);
		
		File ret = new File(SERVER_CONF_URL, DEFAULT_FILE_NAME);

		if (!ret.exists())
			return null;

		return ret;

	}
	
	public BandejaProperties reload(){
		fetchSource();
		load();
		return this;
	}
	
	/**
	 * Optional
	 * @return String Bandeja Deployment Context
	 */
	public String getBandejaDeploymentContext(){
		return this.getProperty("bandeja.deployment.context","/bandeja");
	}
	
	/**
	 * Required<br/> 
	 * Includes PROTOCOL://HOST:PORT<br/>
	 * Avoid trailling slash
	 * @return String Deployment HOST
	 */
	public String getProcessDeploymentHost(){
		return this.getProperty("process.deployment.host");
	}
	
	/**
	 * Optional<br/>
	 * Avoid trailling slash
	 * @return String Process Deployment Context
	 */
	public String getProcessDeploymentContext(){
		return this.getProperty("process.deployment.context", "/business-central");
	}
	
	/**
	 * Required
	 * @return String Process Deployment Id
	 */
	public String getProcessDeploymentId(){
		return this.getProperty("process.deployment.id");
	}
	
	/**
	 * Required<br/>
	 * Separated Commas String with no spaces between commas and ids
	 * @return String[] Process Definition IDs
	 */
	public String[] getProcessDefinitionIds(){
		String rawDefinitions = this.getProperty("process.definition.ids");
		String[] definitions = null;
		if(rawDefinitions!=null){
			definitions = rawDefinitions.split(",");
		}else{
			definitions = new String[]{};
		}
		return definitions;
	}
	
	/**
	 * Optional
	 * @return String URL Form Render
	 */
	public String getUrlFormRender(){
		return this.getProperty("url.formrender","http://192.168.100.7:8080/FormRender/rest/external/save");
	}
	
	/**
	 * Optional
	 * @return String URL Directory
	 */
	public String getUrlDirectory(){
		return this.getProperty("url.directory","/home/karina/");
	}
	
}
