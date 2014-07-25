package bo.gov.aduana.bandeja.template.engine;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.apache.log4j.Logger;

import bo.gov.aduana.bandeja.template.engine.exception.TemplatingEngineException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

/**
 * @author Jorge Morando
 *
 */
@ApplicationScoped
public class TemplateEngine {
	
	public static final String TEMPLATES_PATH_PREFIX = "templating";
	
	@Inject
	private Logger log;
	
	
	/**
	 * Esta clase es un singleton que deber&iacute;a cargarse solo una vez en el ciclo de vida de la aplicaci&oacute;n
	 */
	private Configuration cfg = new Configuration();
	
	@PostConstruct
	private void init(){
		// Where do we load the templates from:
	    cfg.setClassForTemplateLoading(TemplateEngine.class, "/"+TEMPLATES_PATH_PREFIX);
	    
	    // Some other recommended settings:
	    cfg.setIncompatibleImprovements(new Version(2, 3, 20));
	    cfg.setDefaultEncoding("UTF-8");
	    cfg.setLocale(Locale.US);
	    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	    
	}
	
	public String buildTemplate(BaseTemplate template){
		template.build();
		return build(template.getViewPath(),template.getVars());
	}
	
	private String build(String template, Map<String,Object> vars){
	    
		try{
			
		    //se levanta la template
		    Template loadedTemplate = cfg.getTemplate(template);
		    
		    //se procesa la template
		    Writer stringWriter = new StringWriter();
		    //se recopilan las variables solo si hay
		    if(vars !=null && vars.size()>0){
		    	loadedTemplate.process(vars, stringWriter);
		    }else{
		    	loadedTemplate.process(null, stringWriter);
		    }
		    
		    return stringWriter.toString();
		    
		} catch (IOException e) {
			String msg = "Se ha capturado un error tratando de construir template '"+template+"'";
			log.debug(msg,e);
			log.error(msg);
			throw new TemplatingEngineException(msg, e);
		} catch (TemplateException e) {
			String msg = "Se ha capturado un error tratando de construir template '"+template+"'";
			log.debug(msg,e);
			log.error(msg);
			throw new TemplatingEngineException(msg, e);
		}
	}
	
}
