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
@Named("resolverProperties")
public class ResolverProperties extends Properties {
	
	private static final long serialVersionUID = 8397332937906785565L;

	public static final String DEFAULT_FILE_NAME = "bandeja_resolver.properties";
	
	private static final String SERVER_CONF_URL = System.getProperty("jboss.server.config.dir");
	
	private static Logger log = Logger.getLogger(ResolverProperties.class);
	
	private InputStream elInput=null;

	public ResolverProperties() {
		log.info("Cargando configuraci\u00F3n de ResolverFactory de Bandeja de Procesos.");
		
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
			log.error("No puedo cargar el archivo properties "+ DEFAULT_FILE_NAME);
			log.debug("No puedo cargar el archivo properties "+ DEFAULT_FILE_NAME,e);
		}
	}
	
	private void load(){
		if (this.isEmpty()) {
			try {
				this.load(this.elInput);
			} catch (IOException e) {
				throw new RuntimeException("No se pudo cargar el archivo de configuraci\u00f3n de ResolverFactory de Bandeja de Procesos.",e);
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
	
	public ResolverProperties reload(){
		fetchSource();
		load();
		return this;
	}
	
}
