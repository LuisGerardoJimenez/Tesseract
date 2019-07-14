package mx.tesseract.br;

import java.util.Date;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("rN035")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN035 {
	
	public Boolean isValidRN035(Date fechaInicio, Date fechaTermino) {
		Boolean valido = true;
		if (!fechaTermino.after(fechaInicio)) {
			valido = false;
		}
		return valido;
	}
	
}
