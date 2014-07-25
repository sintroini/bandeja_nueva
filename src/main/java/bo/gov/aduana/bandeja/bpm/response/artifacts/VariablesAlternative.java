package bo.gov.aduana.bandeja.bpm.response.artifacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bo.gov.aduana.bandeja.bpm.kiewrapper.VariableWrapper;

/**
 * @author Jorge 
 *
 */
public class VariablesAlternative extends HashMap<String,String> {

	private static final long serialVersionUID = -7624322343963102449L;

	
	public List<VariableWrapper> toList(){
		List<VariableWrapper> variables = new ArrayList<>();
		for (String key : keySet()) {
			variables.add(new VariableWrapper(key,get(key)));
		}
		return variables;
	}
	
	/* (non-Javadoc)
	 * @see java.util.AbstractMap#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		int i = 0;
		for (String key : keySet()) {
			sb.append("\""+key+"\":");
			sb.append("\""+get(key)+"\"");
			i++;
			if(!(i==size()))//end
				sb.append(",");
		}
		sb.append("}");
		return sb.toString();
	}
	
	

}
