package mx.tesseract.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

public class StrutsDateConverter extends StrutsTypeConverter {
	
	private static final Logger TESSERACT_LOGGER = LogManager.getLogger();

	@Override
	public Object convertFromString(Map arg0, String[] arg1, Class arg2) {
		String dia = arg1[0].substring(0, 2);
		String mes = arg1[0].substring(3,5);
		String anio = arg1[0].substring(6);
				
		Date fecha = null;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			fecha = formatter.parse(dia + "/" + mes + "/" + anio);
		} catch (ParseException e) {
			TESSERACT_LOGGER.error(this.getClass().getName() + ": " + "convertFromString", e);
		}

		return fecha;
	}

	@Override
	public String convertToString(Map arg0, Object arg1) {
		return arg1.toString();
	}

}
