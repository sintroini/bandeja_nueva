package bo.gov.aduana.bandeja.util;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

public class StringUtility {
	private static final Logger log = Logger.getLogger(StringUtility.class);

	private static final char[] CONSTS_HEX = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * Función que crea el MD5 a partir de un string
	 * 
	 * @param stringAEncriptar
	 * @return
	 */
	public static String toMD5(String stringAEncriptar) {
		try {
			MessageDigest msgd = MessageDigest.getInstance("MD5");
			byte[] bytes = msgd.digest(stringAEncriptar.getBytes());
			StringBuilder strbCadenaMD5 = new StringBuilder(2 * bytes.length);
			for (int i = 0; i < bytes.length; i++) {
				int bajo = (int) (bytes[i] & 0x0f);
				int alto = (int) ((bytes[i] & 0xf0) >> 4);
				strbCadenaMD5.append(CONSTS_HEX[alto]);
				strbCadenaMD5.append(CONSTS_HEX[bajo]);
			}
			return strbCadenaMD5.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static String generateRamdonString() {
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}
	
	public static ObjectNode parseJsonString(String src) {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode event = null;
		try {
			event = mapper.readValue(src, ObjectNode.class);
		} catch (JsonParseException e) {
			log.error("Parse exception: ", e.getCause());
		} catch (JsonMappingException e) {
			log.error("Json mapping exception: ", e.getCause());
		} catch (IOException e) {
			log.error("IO exception: ", e.getCause());
		}
		return event;
	}
	
	 public static String dateFormat(Date date){
		 DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		 return dateFormat.format(date);
     }
}
