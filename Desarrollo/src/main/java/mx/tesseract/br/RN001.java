package mx.tesseract.br;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import mx.tesseract.dto.PantallaDTO;
import mx.tesseract.editor.entidad.Pantalla;

@Service("rN001")
@Scope(value = BeanDefinition.SCOPE_SINGLETON)
public class RN001 {
	
	public Boolean isValidRN001(PantallaDTO entidad) {
		Boolean valido = true;
		Pantalla pantalla;
		if (entidad.getId() == null) {
//			pantalla = ;
		} else {
//			pantalla = ;
		}
//		if (pantalla != null) {
//			valido = false;
//		}
		return valido;
	}

}
